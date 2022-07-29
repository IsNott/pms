package com.nott.pms.file.controller;


import cn.hutool.core.lang.Assert;
import com.nott.pms.common.Result;
import com.nott.pms.common.service.SessionService;
import com.nott.pms.consts.SysConstant;
import com.nott.pms.exception.UserNotFoundExeption;
import com.nott.pms.file.entity.File;
import com.nott.pms.file.service.IFileService;
import com.nott.pms.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author zzzwlong
 * @since 2022-04-07
 */
@RestController
@RequestMapping("/pms/file")
public class FileController {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private IFileService fileService;


    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result upload(MultipartFile file) {
        User user = sessionService.getUser();
        fileService.upload(file, user.getEmpNo());
        return Result.ok();
    }

    @PostMapping("/queryFileByPage")
    @ApiOperation("文件列表")
    public Result queryFileByPage(Long page, Long rows, String name) {
        return Result.success(fileService.fileList(page, rows, name));
    }

    @PostMapping("/remove")
    @ApiOperation("管理员删除文件")
    public Result remove(Long id) throws UserNotFoundExeption {
        Assert.notNull(id, "id不能为空");
//        sessionService.auth(SysConstant.File_PERMISSION);
        fileService.removeFile(id);
        return Result.ok();
    }

    @ApiOperation("下载文件")
    @RequestMapping(value = "/download/{id}", produces = {"application/zip;charset=UTF-8"})
    public Result download(HttpServletResponse rep, @PathVariable("id") Long id) throws IOException {
        Assert.notNull(id, "文件id不能为空");
        fileService.getFile(rep, id);
        return Result.ok();
    }

    @ApiOperation("获取文件类型")
    @RequestMapping("/type")
    public Result getType(Long id){
        File file = fileService.getById(id);
        String fileName = file.getFileName();
        HashMap<String, String> map = new HashMap<>();
        String type = fileName.substring(fileName.lastIndexOf("."));
        map.put("name",fileName);
        map.put("type",type);
        return Result.success(map);
    }

}
