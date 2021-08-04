package com.assessment.code.biometricmatch.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.model.MatchResponse;
import com.assessment.code.biometricmatch.service.MatchingService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ImageController.class)
class ImageControllerTest {
	
	@Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    MatchingService matchingService;

	@Disabled
	void test() {
		fail("Not yet implemented");
	}
	
    @Disabled
	public void compareImages_success() throws Exception {
		//MatchResponse response = new MatchResponse(new BigDecimal("0.1"));
		
		MultipartFile file1 = null;
		MultipartFile file2 = null;
		//Mockito.when(matchingService.compareImages(file1, file2)).thenReturn(response);		
	}

}
