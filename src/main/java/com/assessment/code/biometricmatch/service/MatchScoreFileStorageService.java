package com.assessment.code.biometricmatch.service;

import java.util.List;
import java.util.Map;

import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;

public interface MatchScoreFileStorageService {

	IDSLMatchScoreModel getMatchScoreFile(String fileName, String fileName2);

	Iterable<IDSLMatchScoreModel> getMatchScoreFiles(String fileName);

	List<IDSLMatchScoreModel> getAllFiles();

	Map<String, Boolean> removeMatchScoreFile(String fileName1, String fileName2);

	Map<String, Boolean> removeAllMatchScoreFiles();

	Map<String, Boolean> removeMatchScoreFiles(String fileName);

}
