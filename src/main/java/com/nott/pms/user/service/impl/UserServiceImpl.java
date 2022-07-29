package com.nott.pms.user.service.impl;

import com.aliyuncs.ram.model.v20150501.GetUserResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.mapper.EmpMapper;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.emp.vo.EmpInfoVo;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.menu.entity.Permission;
import com.nott.pms.menu.entity.RolePermission;
import com.nott.pms.menu.service.IPermissionService;
import com.nott.pms.menu.service.IRolePermissionService;
import com.nott.pms.menu.service.IUserRoleService;
import com.nott.pms.oss.OssUtil;
import com.nott.pms.oss.entity.OssManager;
import com.nott.pms.oss.service.OssService;
import com.nott.pms.user.entity.PwdParam;
import com.nott.pms.user.entity.User;
import com.nott.pms.user.mapper.UserMapper;
import com.nott.pms.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nott.pms.user.vo.AdminVo;
import com.nott.pms.user.vo.UserVo;
import okhttp3.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.nott.pms.consts.Consts.CURRENT_USER;
import static com.nott.pms.consts.Consts.UserFace;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    public static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String CAPT = "captcha";

    @Autowired
    HttpSession session;

    @Autowired
    private OssManager ossManager;

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private OssService ossService;
    @Autowired
    private IEmpService empService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRolePermissionService rolePermissionService;


    @Override
    public User findUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        User user = baseMapper.selectOne(wrapper);
        if (!Objects.nonNull(user)) {
            return null;
        }
        return user;
    }

    @Override
    public Map<String, Object> login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) session.getAttribute(CAPT);
        Assert.notNull(code, "验证码为空");
        if (Objects.isNull(code) || !code.equals(captcha)) {
            throw new BusinessException("验证码错误！");
        }
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, username)
//                .eq(User::getPassword, password)
//                .eq(User::getDelflag, SysConstant.delflag.NORMAL);
//        User user = baseMapper.selectOne(wrapper);
//        if (Objects.isNull(user)) {
//            throw new BusinessException("账号或密码错误");
//        }

        //修改为shiro登录
        HashMap<String, Object> map = new HashMap<>();
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            if (subject.isAuthenticated()) {
                LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(User::getUsername, username);
                User user = this.list(wrapper).get(0);
                logger.info("用户登录：" + user.toString());
                session.setAttribute(CURRENT_USER, user);
                logger.info("用户SessionId：" + session.getId());
                user.setTel(empMapper.selectTelByEmpno(user.getEmpNo()));
                List<EmpInfoVo> empInfoVos = empMapper.queryEmpBasicInfo(null, user.getEmpNo());
                EmpInfoVo empInfoVo = empInfoVos.get(0);
                map.put("userInfo", user);
                map.put("empInfo", empInfoVo);
            }
        } catch (AuthenticationException e) {
            log.error("登录失败");
            throw new AuthenticationException("校验失败");
        }
        return map;
    }

    @Override
    public void logout() {
//        User user = (User) session.getAttribute(CURRENT_USER);
//        Assert.notNull(user, "无登录信息");
//        session.removeAttribute(CURRENT_USER);
        //修改为shiro登出
        logger.info("user logout,sessionInvalidate:" + session.getId());
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.invalidate();
    }

    @Override
    public void UploadUserface(String data, User user) throws IOException {
        InputStream imageStream = null;
        FileOutputStream fileOutputStream = null;
        String imgStr = data.substring(23);
        byte[] bytes = new byte[0];
        try {
            bytes = Base64.getDecoder().decode(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    // 调整异常数据
                    bytes[i] += 256;
                }
            }
        } catch (Exception e) {
            log.error("图片解码失败");
        }

        try {
            imageStream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            throw new BusinessException("上传失败，写入文件失败，" + e.getMessage());
        }
        String dir = UserFace;
        String name = UUID.randomUUID().toString() + ".jpg";
        ossService.upload(dir, name, imageStream);
        if (imageStream != null) {
            imageStream.close();
        }
        if (null != user.getUrl() && !user.getUrl().contains("default.jpg")) {
            if (user.getUrl().contains("/")) {
                String url = user.getUrl();
                String delName = url.substring(url.indexOf("/", url.indexOf("/") + 1) + 1);
                ossService.remove(dir + "/" + delName);
            }
        }
        user.setUrl(ossManager.getBucketName() + "." + ossManager.getEndpoint() + "/" + dir + "/" + name);
        baseMapper.updateById(user);
    }


    @Override
    public void findUserfaceById(Long id, HttpServletRequest req, HttpServletResponse rep) throws IOException {
        User user = baseMapper.selectById(id);
        Assert.notNull(user, "无相关用户信息");
        ServletOutputStream outputStream = null;
        String userfaceurl = null;
        BufferedImage image = null;
        //未设置头像的用户使用系统默认头像
        if (Objects.isNull(user.getUrl())) {
            userfaceurl = "https://" + ossManager.getBucketName() + "." + ossManager.getEndpoint() + "/" + UserFace + "/" + "default.jpg";
            //System.out.println(userfaceurl);
        } else {
            userfaceurl = "https://" + user.getUrl();
        }
        try {
            URL url = new URL(userfaceurl);
            try {
                image = (BufferedImage) ImageIO.read(url);
                outputStream = rep.getOutputStream();
                ImageIO.write(image, "jpg", outputStream);
                logger.info("user face：" + url);
                outputStream.flush();
                req.setAttribute(UserFace, req.getRequestURI());
            } catch (IOException e) {
                //数据库中url出错使用服务器默认图片
                image = (BufferedImage) ImageIO.read(new URL("https://" + ossManager.getBucketName() + "." + ossManager.getEndpoint() + "/" + UserFace + "/" + "default.jpg"));
                outputStream = rep.getOutputStream();
                ImageIO.write(image, "jpg", outputStream);
                outputStream.flush();
                logger.error("get userFace error ：" + e.getMessage());

            }
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException：" + e.getMessage());
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("IOException：" + e.getMessage());
                }
            }
        }
    }


    @Override
    public void removeByEmpno(Long id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getEmpNo, id);
        User user = this.getOne(wrapper);
        this.removeById(user);
    }

    @Override
    public Page<UserVo> Userlist(Long size, Long page, String keyword) {
        Page<UserVo> userPage = new Page<>();
        Page<User> userPage1 = new Page<>();
        if (Objects.nonNull(size)) {
            userPage1.setSize(size);
        }
        if (Objects.nonNull(page)) {
            userPage1.setCurrent(page);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getRoleId, SysConstant.roleId.NORMAL)
                .eq(User::getDelflag, SysConstant.delflag.NORMAL)
                .like(StringUtils.hasText(keyword), User::getUsername, keyword);
        List<User> userList1 = this.list(wrapper);
        Page<User> users = this.page(userPage1, wrapper);
        List<User> userList = users.getRecords();
        ArrayList<UserVo> vos = new ArrayList<>();
        for (User user : userList) {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(user, vo);
            Emp emp = empService.getById(user.getEmpNo());
            vo.setEmpName(emp.getName());
            vo.setRoleName(userRoleService.getById(user.getRoleId()).getRoleName());
            vos.add(vo);
        }
        userPage.setRecords(vos);
        userPage.setTotal(userList1.size());
        return userPage;
    }

    @Override
    public List<AdminVo> findAdmins(Long empNo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(User::getRoleId, SysConstant.roleId.NORMAL);
        List<User> admins = this.list(wrapper);
        ArrayList<AdminVo> adminVos = new ArrayList<>();
        for (User admin : admins) {
            AdminVo vo = new AdminVo();
            BeanUtils.copyProperties(admin, vo);
            List<Permission> roleId = permissionService.getPermissionByRoleId(admin.getRoleId());
            if (!roleId.isEmpty()) {
                vo.setPermisssionName(roleId);
            }
            adminVos.add(vo);
        }
        return adminVos;
    }

    @Override
    public void changePwd(HttpServletRequest request, PwdParam pwdParam, User user) {
        String captcha = (String) request.getSession().getAttribute(CAPT);
        if (!captcha.equals(pwdParam.getCode())) {
            throw new BusinessException("验证码错误！");
        }
        if (!pwdParam.getPwd().equals(user.getPassword())) {
            throw new BusinessException("原密码错误！");
        }
        user.setPassword(pwdParam.getNpwd());
        this.updateById(user);
    }


}
