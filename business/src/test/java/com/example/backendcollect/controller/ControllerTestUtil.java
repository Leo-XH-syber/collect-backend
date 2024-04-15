package com.example.backendcollect.controller;
import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.service.CoReportService;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.report.CoReportVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
public class ControllerTestUtil {
    public static String get(MockMvc mockMvc, String path, String pathVal, MultiValueMap<String,String> paramMap){
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get(path,pathVal)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .params(paramMap))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            return result.getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T>String post(MockMvc mockMvc, String path, String pathVal, MultiValueMap<String,String> paramMap,T vo){
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post(path,pathVal)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(vo))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            return result.getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String asJsonString(final Object obj) {
        if(obj==null) return "";
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
