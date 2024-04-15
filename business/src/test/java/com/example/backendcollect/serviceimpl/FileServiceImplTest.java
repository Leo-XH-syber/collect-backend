package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.utils.FileHelper;
import com.example.backendcollect.vo.file.FileInfoVO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.backendcollect.enums.errorcode.Impl.FileStatusCode.FAIL_TO_UPLOAD;


/**
 * FileServiceImpl UnitTester.
 *
 * @author Rikka
 * @version 1.0
 * @since <pre>2月 20, 2022</pre>
 */
@ExtendWith(MockitoExtension.class) // 告诉JUnit MockitoExtension 使用进行测试, SpringBoot 框架不启动
public class FileServiceImplTest {

    @InjectMocks
    private FileService fileService = new FileServiceImpl(); // 待测试对象

    @Mock
    private FileHelper fileHelper;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    public void testUploadFile()  {

        byte[] bytes = new byte[8];
        MultipartFile file = new MockMultipartFile("a", "test.com/a", "image/png", bytes);

        FileInfoVO fileInfoVO = new FileInfoVO("test.com/a", "image/png", 8L); //  FileHelper 接受到那个 file 返回信息
        // 这个会设定用到的方法在接受相应参数时应该返回什么东西(桩程序)
        Mockito.when(fileHelper.save(file)).thenReturn(fileInfoVO);
        // fileService 内部的被 mock 替换
        FileInfoVO ans = fileService.uploadFile(file);
        // 这个主要是验证逻辑是否正确, 实际上更关心桩是否被调用而非功能是否完整实现
        Assertions.assertEquals(ans, fileInfoVO);
        // 验证FileHelper中的方法是否被调用 1 次
        // 验证 save 方法是否以 file 为参数被调用
        Mockito.verify(fileHelper, Mockito.times(1)).save(file);
    }

    @SneakyThrows
    @Test
    public void testUploadEmptyFile()  {
        byte[] bytes = new byte[0];
        MultipartFile emptyFile = new MockMultipartFile("a", "test.com/a", "image/png", bytes);
        Mockito.when(fileHelper.save(emptyFile)).thenThrow(new IOException());// 模拟抛错
        try {
            fileService.uploadFile(emptyFile);
        }catch (Exception e){
            Assertions.assertTrue(e instanceof ServiceException);
            Assertions.assertEquals(e.getMessage(),FAIL_TO_UPLOAD.getMsg());
        }
        Mockito.verify(fileHelper, Mockito.times(1)).save(emptyFile);
    }

} 
