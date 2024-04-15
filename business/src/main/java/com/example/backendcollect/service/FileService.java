package com.example.backendcollect.service;

import com.example.backendcollect.vo.file.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileInfoVO uploadFile(MultipartFile file);

}
