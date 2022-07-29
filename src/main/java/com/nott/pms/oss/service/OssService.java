package com.nott.pms.oss.service;

import com.nott.pms.file.entity.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public interface OssService {
     void upload(String dir, String fileName, InputStream imageStream);

    void remove(String path);

    void download(HttpServletResponse rep, File file) throws IOException;
}
