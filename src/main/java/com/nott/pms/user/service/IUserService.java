package com.nott.pms.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.user.entity.PwdParam;
import com.nott.pms.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.user.vo.AdminVo;
import com.nott.pms.user.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-02-04
 */
public interface IUserService extends IService<User> {
    User findUserByUsername(String username);

    Map<String,Object> login(String username, String password, String code, HttpServletRequest request);

    void logout();

    void UploadUserface(String data,User user) throws IOException;

    void findUserfaceById(Long id, HttpServletRequest request,HttpServletResponse response) throws IOException;

    void removeByEmpno(Long id);

    Page<UserVo> Userlist(Long size, Long page, String keyword);

    List<AdminVo> findAdmins(Long empNo);

    void changePwd(HttpServletRequest request,PwdParam pwdParam,User user);
}
