package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.utils.FileHelper;
import com.example.backendcollect.vo.file.FileInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.backendcollect.enums.errorcode.Impl.FileStatusCode.FAIL_TO_UPLOAD;


@Service
public class FileServiceImpl implements FileService {
    FileHelper fileHelper;

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }


    @Override
    public FileInfoVO uploadFile(MultipartFile file) {
        try {
            return fileHelper.save(file);
        } catch (IOException e) {
            throw new ServiceException(FAIL_TO_UPLOAD);
        }
    }
}
