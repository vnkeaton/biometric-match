package com.assessment.code.biometricmatch.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.model.IDSLImageModel;

public interface FileStorageService {

	Resource loadFileAsResource(String fileName);
	String storeFile(MultipartFile file);
	IDSLImageModel storeFileToDatabase(MultipartFile file);
}
