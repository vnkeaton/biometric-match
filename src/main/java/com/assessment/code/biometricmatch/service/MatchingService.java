package com.assessment.code.biometricmatch.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.model.MatchResponse;

public interface MatchingService {

	MatchResponse compareImages(IDSLImageModel image1, IDSLImageModel image2);

	List<IDSLImageModel> processMatchFiles(MultipartFile[] files);

}
