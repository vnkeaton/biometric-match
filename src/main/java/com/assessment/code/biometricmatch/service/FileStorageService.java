package com.assessment.code.biometricmatch.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.model.IDSLImageModel;

public interface FileStorageService {

	IDSLImageModel getFile(String fileName);
	Map<String, Boolean> removeFile(String fileName);
	IDSLImageModel storeFile(MultipartFile file);
}
