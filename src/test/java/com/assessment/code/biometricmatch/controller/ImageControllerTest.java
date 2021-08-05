package com.assessment.code.biometricmatch.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.assessment.code.biometricmatch.model.MatchResponse;
import com.assessment.code.biometricmatch.service.FileStorageService;
import com.assessment.code.biometricmatch.service.MatchingService;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
class ImageControllerTest {
	
	@Autowired
    MockMvc mockMvc;
    
    @MockBean
    MatchingService matchingService;
    
    @MockBean
    FileStorageService fileStorageService;
    
    private final String basePath = "src/test/resources/testFiles";
    private final File testFile1 = new File(basePath + "/1.png");
    private final File testFile2 = new File(basePath + "/2.png");

    MatchResponse matchResponse = new MatchResponse(new BigDecimal("0.5"), "1.png", "id1", "2.png", "id2");
    
    String expectedJson = "{\"matchResult\":\"0.5\",\"fileName1\":\"1.png\",\"fileId1\":\"id1\",\"fileName2\":\"2.png\",\"fileId2\":\"id2\"}";

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
    
    //TODO need tests for upload exceptions
    
    @Test
    public void test_downloadFile_success() throws Exception {
       RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/downloadFile/1.png")
			.accept(MediaType.APPLICATION_OCTET_STREAM);
	   MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	   MockHttpServletResponse response = result.getResponse();

	   assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
  //TODO need tests for download exceptions

}
