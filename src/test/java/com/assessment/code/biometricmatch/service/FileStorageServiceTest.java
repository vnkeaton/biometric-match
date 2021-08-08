package com.assessment.code.biometricmatch.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.assessment.code.biometricmatch.exception.FileNotFoundException;
import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.repository.IDSLImageRepository;

@RunWith(SpringRunner.class)
class FileStorageServiceTest {
	
	private final IDSLImageModel image1 = new IDSLImageModel("1.png", "PNG", new BigInteger("500"), "<<image1 data>>".getBytes());
	
	@TestConfiguration
	public static class FileStorageServiceTestConfig{
		@Bean
		public ImageFileStorageService fileStorageService(IDSLImageRepository imageRepository){
			return new ImageFileStorageServiceImpl(imageRepository);
		}
	}
	
	@MockBean
	private IDSLImageRepository imageRepository;
	
	@Autowired
	private ImageFileStorageService fileStorageService;

	//@Test
	//TODO - fix this
	@Ignore
	void test_getFile_success() {
		Mockito.when(imageRepository.findByFileName(Mockito.anyString())).thenReturn(image1);
		IDSLImageModel response = fileStorageService.getImageFile("1.png");
		assertEquals(image1.getFileName(), response.getFileName());		
	}
	
	//@Test
	//TODO - fix this
	@Ignore
	void test_getFile_fileNotFoundException() {
		
		Mockito.when(imageRepository.findByFileName(Mockito.anyString())).
		             thenThrow(new FileNotFoundException("File not found: 1.png"));
		IDSLImageModel response = null;
		try {
		   response = fileStorageService.getImageFile("1.png");
		}
		catch(Exception e) {
			assertTrue(e instanceof FileNotFoundException);
			final String expected = "File not found: 1.png";
			assertEquals(expected, e.getMessage());
		}	
	}

}
