package com.assessment.code.biometricmatch.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	private final FileStorageService fileStorageService;
	 
	 @Autowired
	 public MatchingServiceImpl(IDSLMatchScoreRepository matchRepository,
			                    FileStorageService fileStorageService) {
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
		BigDecimal score = image1.getFileName().equals(image2.getFileName()) ? new BigDecimal(0.0) : getScore();
		IDSLMatchScoreModel matchScore = new IDSLMatchScoreModel(image1, image2, score);
		matchRepository.save(matchScore);		
		return new MatchResponse(matchScore.getMatchScore(), image1.getFileName(), image1.getId(),
				image2.getFileName(), image2.getId());
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
		    IDSLImageModel image = (fileStorageService.doesFileExist(file.getOriginalFilename()) ?
				                   fileStorageService.getFile(file.getOriginalFilename()) :
				                   fileStorageService.storeFile(file));
		    images.add(image);
		}
		return images;
	}

}
