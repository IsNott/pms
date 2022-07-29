package com.nott.pms.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nott.pms.file.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nott.pms.file.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-07
 */
public interface IFileService extends IService<File> {

    void upload(MultipartFile file, Long EmpNo);

    Page<FileVo> fileList(Long page, Long rows,String name);

    void removeFile(Long id);

    void getFile(HttpServletResponse rep, Long id) throws IOException;
}
