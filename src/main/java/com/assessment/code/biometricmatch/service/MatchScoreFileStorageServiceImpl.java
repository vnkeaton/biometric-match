package com.assessment.code.biometricmatch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.code.biometricmatch.exception.FileNotFoundException;
import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;
import com.assessment.code.biometricmatch.repository.IDSLMatchScoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchScoreFileStorageServiceImpl implements MatchScoreFileStorageService {
	
	private final IDSLMatchScoreRepository matchScoreRepository;
	 
	 @Autowired
	 public MatchScoreFileStorageServiceImpl(IDSLMatchScoreRepository matchScoreRepository) {
			this.matchScoreRepository = matchScoreRepository;
	}
	 
	@Override
	public IDSLMatchScoreModel getMatchScoreFile(String fileName1, String fileName2) {
			log.info("get matchscore fileName1: " + fileName1 + " fileName2:" + fileName2);
			IDSLMatchScoreModel matchScore = matchScoreRepository.findByFileName1AndFileName2(fileName1, fileName2);
			if (matchScore == null) {
				log.info("matchscore not found");
				throw new FileNotFoundException("matchscore data not found where fileName1: " + fileName1 + " and fileName2: " + fileName2);
			}
			return matchScore;
	}
	    
	@Override
	public Iterable<IDSLMatchScoreModel> getMatchScoreFiles(String fileName) {
		log.info("get matchscores for fileName: " + fileName);
		Iterable<IDSLMatchScoreModel> matchScores = matchScoreRepository.findByFileName1OrFileName(fileName);
		if (matchScores == null) {
			log.info("matchscore not found");
			throw new FileNotFoundException("matchscore data not found where fileName1: " + fileName + " or fileName2: " + fileName);
		}
		return matchScores;
		
	}

	@Override
	public List<IDSLMatchScoreModel> getAllFiles() {
		log.info("get all matchscore data");
		List<IDSLMatchScoreModel> matchscores = matchScoreRepository.findAll();
		return matchscores;
	}
	
	@Override
	public Map<String, Boolean> removeMatchScoreFile(String fileName1, String fileName2) {
		log.info("remove matchscore date: fileName1:" + fileName1 + " fileName2: " + fileName2);
		IDSLMatchScoreModel matchscore = null;
		matchscore = getMatchScoreFile(fileName1, fileName2);
		if (matchscore == null) {
			throw new FileNotFoundException("File not found fileName1: " + fileName1 + " fileName2: " + fileName2);
		}
		Map<String, Boolean> response = new HashMap<>();
		String key = "deleted matchscore data for fileName1: " + matchscore.getFile1Name() + " fileName2: " + matchscore.getFile2Name();
		boolean value = false;
		try {
			matchScoreRepository.delete(matchscore);
			value = true;
		}
		catch(Exception e) {
			value = false;
		}
		finally {
			response.put(key , value);
		}
	    return response;
	}
	
	@Override
	public Map<String, Boolean> removeAllMatchScoreFiles() {
		log.info("removing all matchscore files");
		Map<String, Boolean> response = new HashMap<>();
		List<IDSLMatchScoreModel> matchscores = getAllFiles();
		for (IDSLMatchScoreModel matchscore: matchscores) {
			Map<String, Boolean> map = removeMatchScoreFile(matchscore.getFile1Name(), matchscore.getFile2Name());
			response.putAll(map);
		}
		return response;
	}

	@Override
	public Map<String, Boolean> removeMatchScoreFiles(String fileName) {
		log.info("remove all matchscore files that match this filename either fileName1 or fileName2");
		Iterable<IDSLMatchScoreModel> matchscores =  getMatchScoreFiles(fileName);
		if (matchscores == null) {
			throw new FileNotFoundException("File not found fileName: " + fileName);
		}
		Map<String, Boolean> response = new HashMap<>();
		for (IDSLMatchScoreModel matchscore: matchscores) {
			String key = "deleted matchscore data for fileName1: " + matchscore.getFile1Name() + " or fileName2: " + matchscore.getFile2Name();
			boolean value = false;
			try {
				matchScoreRepository.delete(matchscore);
				value = true;
			}
			catch(Exception e) {
				value = false;
			}
			finally {
				response.put(key , value);
			}
		}
		return response;
	}

}
