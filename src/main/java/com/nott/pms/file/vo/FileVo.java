package com.nott.pms.file.vo;

import com.nott.pms.file.entity.File;
import lombok.Data;

@Data
public class FileVo extends File {
    private String uploader;
}
