package com.assessment.code.biometricmatch.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.model.IDSLImageModel;

public interface ImageFileStorageService {

	IDSLImageModel getImageFile(String fileName);
	Map<String, Boolean> removeImageFile(String fileName);
	IDSLImageModel storeImageFile(MultipartFile file);
	List<IDSLImageModel> getAllFiles();
	Map<String, Boolean> removeAllImageFiles();
	boolean doesImageFileExist(String fileName);
}
