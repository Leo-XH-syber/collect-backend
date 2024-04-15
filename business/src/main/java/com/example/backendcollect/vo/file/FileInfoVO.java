package com.example.backendcollect.vo.file;

import lombok.Data;

@Data
public class FileInfoVO {

    private String url;

    private String type;

    private Long size;

    public FileInfoVO(String url, String type, Long size) {
        this.url = url;
        this.type = type;
        this.size = size;
    }
}
