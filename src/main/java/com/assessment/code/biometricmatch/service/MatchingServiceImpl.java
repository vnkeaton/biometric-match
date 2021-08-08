package com.assessment.code.biometricmatch.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.exception.FileStorageException;
import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;
import com.assessment.code.biometricmatch.model.MatchResponse;
import com.assessment.code.biometricmatch.repository.IDSLImageRepository;
import com.assessment.code.biometricmatch.repository.IDSLMatchScoreRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MatchingServiceImpl implements MatchingService {
	
	private final IDSLMatchScoreRepository matchRepository;
	private final ImageFileStorageService fileStorageService;
	 
	 @Autowired
	 public MatchingServiceImpl(IDSLMatchScoreRepository matchRepository,
			                    ImageFileStorageService fileStorageService) {
			this.matchRepository = matchRepository;
			this.fileStorageService = fileStorageService;
		}
	
	@Value("${biometricmatch.random.generator.min:2}")
	int min;
	
	@Value("${biometricmatch.random.generator.max:8}")
	int max;

	@Override
	@Transactional
	public MatchResponse compareImages(IDSLImageModel image1, IDSLImageModel image2) {
        log.info("Comparing images:" + image1.getFileName() + ", " + image2.getFileName());
        //check to see if these 2 files have already been compared
        IDSLMatchScoreModel matchScoreModel = null;
        matchScoreModel = doesFileExist(image1.getFileName(), image2.getFileName());
        if (matchScoreModel == null) {
        	BigDecimal score = image1.getFileName().equals(image2.getFileName()) ? new BigDecimal(0.0) : getScore();
        	matchScoreModel = new IDSLMatchScoreModel(image1.getDir(), image1.getFileName(), image2.getDir(), image2.getFileName(), score);
        	matchRepository.save(matchScoreModel);	
        }
		return new MatchResponse(matchScoreModel.getMatchScore(), image1.getFileName(), image2.getFileName());
	}
	
	private BigDecimal getScore() {
		log.info("get score");
		RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
		return new BigDecimal(randomDataGenerator.nextInt(min, max));
	}

	@Override
	public List<IDSLImageModel> processMatchFiles(MultipartFile[] files) {
		log.info("process match");
		List<IDSLImageModel> images = new ArrayList<IDSLImageModel>();
		for (MultipartFile file: files) {
			log.info("file is:" + file.getOriginalFilename());
			String fname = parseFileName(file.getOriginalFilename());
		    IDSLImageModel image = (fileStorageService.doesImageFileExist(fname) ?
				                   fileStorageService.getImageFile(fname) :
				                   fileStorageService.storeImageFile(file));
		    images.add(image);
		}
		return images;
	}
	
	private IDSLMatchScoreModel doesFileExist(String fileName1, String fileName2) {
		log.info("does score exists for: " + fileName1 + " and " + fileName2);
		IDSLMatchScoreModel matchScoreModel = null;
		matchScoreModel =  matchRepository.findByFileName1AndFileName2(fileName1, fileName2);
		//Only if we assume the match operation is symmetric
		/*if (matchScoreModel == null) {
			matchScoreModel =  matchRepository.findByFileName1AndFileName2(fileName2, fileName1);
		}*/
		return matchScoreModel;
	}
	
	private String parseFileName(String fileName) {
         if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
         } 
		 return FilenameUtils.getBaseName(fileName) + "." + FilenameUtils.getExtension(fileName);		
	}
	
}
