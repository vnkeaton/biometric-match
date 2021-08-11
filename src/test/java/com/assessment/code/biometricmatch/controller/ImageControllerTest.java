package com.assessment.code.biometricmatch.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.exception.EmptyFileException;
import com.assessment.code.biometricmatch.model.MatchResponse;
import com.assessment.code.biometricmatch.service.ImageFileStorageService;
import com.assessment.code.biometricmatch.service.MatchingService;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
class ImageControllerTest {
	
	@Autowired
    MockMvc mockMvc;
    
    @MockBean
    MatchingService matchingService;
    
    @MockBean
    ImageFileStorageService fileStorageService;
    
    private final String basePath = "src/test/resources/testFiles";
    private final File testFile1 = new File(basePath + "/1.png");
    private final File testFile2 = new File(basePath + "/2.png");

    MatchResponse matchResponse = new MatchResponse(new BigDecimal("0.5"), "1.png", "2.png");
    
    String expectedJson = "{\"matchResult\":\"0.5\",\"fileName1\":\"1.png\",\"fileName2\":\"2.png\"}";

    @Test
    public void test_uploadFile_success() throws Exception {

        String fileName = "1.png";
        File file = new File(fileName);
        //delete if exits
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
        		MediaType.IMAGE_PNG_VALUE, "<<image1 data>>".getBytes());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
				.multipart("/biometric/uploadFile")
				.file(mockMultipartFile);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    @Test
    public void test_uploadFile_exception() throws Exception {
    	
    	String fileName = "1.png";
        File file = new File(fileName);
        //delete if exits
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",fileName,
        		MediaType.IMAGE_PNG_VALUE, "<<image1 data>>".getBytes());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
				.multipart("/biometric/uploadFile")
				.file(mockMultipartFile);
        Mockito.doThrow(new EmptyFileException("Uploaded file is empty!"))
		       .when(fileStorageService)
		       .storeImageFile(Mockito.any(MultipartFile.class));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
    
    
    
    //TODO need tests for upload exceptions
    
    @Test
    public void test_downloadFile_success() throws Exception {
       RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/downloadFile/1.png")
			.accept(MediaType.IMAGE_PNG_VALUE);
	   MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	   MockHttpServletResponse response = result.getResponse();

	   assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
  //TODO need tests for download exceptions
    
    @Test
    public void test_removeFile_success() throws Exception {
       RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/removeFile/1.png")
			.accept(MediaType.IMAGE_PNG_VALUE);
	   MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	   MockHttpServletResponse response = result.getResponse();

	   assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
