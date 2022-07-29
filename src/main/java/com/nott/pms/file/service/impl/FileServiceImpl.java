package com.nott.pms.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.emp.entity.Emp;
import com.nott.pms.emp.service.IEmpService;
import com.nott.pms.exception.BusinessException;
import com.nott.pms.file.entity.File;
import com.nott.pms.file.mapper.FileMapper;
import com.nott.pms.file.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nott.pms.file.vo.FileVo;
import com.nott.pms.oss.entity.OssManager;
import com.nott.pms.oss.service.OssService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.nott.pms.consts.Consts.FILEPATH;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-07
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
    @Autowired
    private OssManager ossManager;
    @Autowired
    private OssService ossService;
    @Autowired
    private IEmpService empService;

    @Override
    public void upload(MultipartFile file, Long empNo) {

        File local = null;
        String dir = FILEPATH;
        String name = file.getName();
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = originalFilename.substring(0, originalFilename.length() - suffix.length()) +
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + suffix;
        try {
            local = new File();
            ossService.upload(FILEPATH, fileName, file.getInputStream());
            local.setFileName(fileName);
            local.setUploadNo(empNo);
            local.setUploadTime(LocalDateTime.now());
            local.setFileUrl(dir + "/" + fileName);
            //将保存地址保存到数据库
            this.save(local);
        } catch (IOException e) {
            throw new BusinessException("文件上传出错" + e.getMessage());
        }
    }

    @Override
    public Page<FileVo> fileList(Long page, Long rows, String name) {
        Page<FileVo> fileVoPage = new Page<FileVo>();
        Page<File> filePage = new Page<>();
        if (Objects.nonNull(page) && page > 0) {
            filePage.setCurrent(page);
        }
        if (Objects.nonNull(rows) && rows > 0) {
            filePage.setSize(rows);
        }
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<File>().like(StringUtils.hasLength(name), File::getFileName, name);
        List<File> files = this.list(wrapper);
        Page<File> filePage1 = this.page(filePage, wrapper);
        List<File> records = filePage1.getRecords();
        ArrayList<FileVo> vos = new ArrayList<>();
        for (File file : records) {
            FileVo vo = new FileVo();
            Emp emp = empService.getById(file.getUploadNo());
            BeanUtils.copyProperties(file, vo);
            vo.setUploader(emp.getName());
            vos.add(vo);
        }
        fileVoPage.setTotal(files.size());
        fileVoPage.setRecords(vos);
        return fileVoPage;
    }

    @Override
    public void removeFile(Long id) {
        File file = this.getById(id);
        if (Objects.isNull(file)) {
            throw new BusinessException("找不到文件信息");
        }
        if (StringUtils.hasLength(file.getFileUrl())) {
            String path = file.getFileUrl();
            try {
                ossService.remove(path);
                this.removeById(file.getId());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new BusinessException("删除失败");
            }
        } else {
            //如果没有文件就删除本地记录
            this.removeById(file.getId());
        }
    }

    @Override
    public void getFile(HttpServletResponse rep, Long id) throws IOException {
        File file = this.getById(id);
        if(Objects.isNull(file)){
            return;
        }
        ossService.download(rep,file);
    }
}
