package com.assessment.code.biometricmatch.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.model.UploadResponse;
import com.assessment.code.biometricmatch.model.IDSLImageModel;

public interface FileStorageService {

	IDSLImageModel storeFileToDatabase(MultipartFile file);
	IDSLImageModel getFile(String fileName);
	Map<String, Boolean> removeFile(String fileName);
}
