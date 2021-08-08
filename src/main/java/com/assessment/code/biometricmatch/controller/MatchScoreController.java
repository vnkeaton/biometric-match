package com.assessment.code.biometricmatch.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;
import com.assessment.code.biometricmatch.service.MatchScoreFileStorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(value = "biometric", tags = {"biometric MatchScore Endpoints"})
@RequestMapping("/biometric/matchscore")
public class MatchScoreController {
	
	private final MatchScoreFileStorageService fileStorageService;
	
	@Autowired
	public MatchScoreController(MatchScoreFileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}
	
	//Posting is the result of the match operation in ImageController
	
	@GetMapping("/downloadFile/{fileName1}/{fileName2}")
	@ApiOperation(value = "Download match score files with a matching file name for fileName1 AND fileName2",
		          notes = "Returns a 200 when successful.",
		          response = IDSLMatchScoreModel.class)
	public ResponseEntity<IDSLMatchScoreModel> downloadFile(@PathVariable String fileName1, 
			                                                          @PathVariable String fileName2,
			                                                          HttpServletRequest request) {
       log.info("download match score file, filename1: " + fileName1 + " fileName2: " + fileName2);
	   IDSLMatchScoreModel matchSoreFile = fileStorageService.getMatchScoreFile(fileName1, fileName2);
       return new ResponseEntity<IDSLMatchScoreModel>(matchSoreFile, HttpStatus.OK);
	}   
	
	@GetMapping("/downloadFile/{fileName}")
	@ApiOperation(value = "Download match score files with a matching file name as fileName1 OR fileName2",
		          notes = "Returns a 200 when successful.",
		          response = IDSLMatchScoreModel.class,
		          responseContainer = "List")
	public ResponseEntity<Iterable<IDSLMatchScoreModel>> downloadFiles(@PathVariable String fileName,
			                                                          HttpServletRequest request) {
       log.info("download match score files, filename: " + fileName);
	   Iterable<IDSLMatchScoreModel> matchScoreFiles = fileStorageService.getMatchScoreFiles(fileName);
	   return new ResponseEntity<Iterable<IDSLMatchScoreModel>>(matchScoreFiles, HttpStatus.OK);
	}  
	
	@GetMapping("/downloadFile/all")
	@ApiOperation(value = "Downloads all match score rows from database",
		              notes = "Returns a 200 when successful.",
		              response = IDSLMatchScoreModel.class,
		              responseContainer = "List")
	 public ResponseEntity<Iterable<IDSLMatchScoreModel>> downloadAllFiles(HttpServletRequest request) {
		   log.info("match score downloadAllFiles...");
		   List<IDSLMatchScoreModel> matchScores = fileStorageService.getAllFiles();
		   return new ResponseEntity<Iterable<IDSLMatchScoreModel>>(matchScores, HttpStatus.OK);
	 }   
	
	@DeleteMapping("/removeFile/{fileName1}/{fileName2}")
	@ApiOperation(value = "Remove match score files with a matching file name for fileName1 AND fileName2",
		          notes = "Returns a 200 when successful.",
		          response = IDSLMatchScoreModel.class)
	public Map<String, Boolean> removeFile(@PathVariable String fileName1, 
			                                              @PathVariable String fileName2,
			                                              HttpServletRequest request) {
       log.info("remove match score file, filename1: " + fileName1 + " fileName2: " + fileName2);
	   return fileStorageService.removeMatchScoreFile(fileName1, fileName2);
	}  
	
	@DeleteMapping("/removeFile/{fileName}")
	@ApiOperation(value = "Remove match score files with a matching file name for fileName1 OR fileName2",
		          notes = "Returns a 200 when successful.",
		          response = IDSLMatchScoreModel.class)
	public Map<String, Boolean> removeFiles(@PathVariable String fileName, HttpServletRequest request) {
       log.info("remove match score files, filename1: " + fileName + " or fileName: " + fileName);
	   return fileStorageService.removeMatchScoreFiles(fileName);
	}  
	
	 @DeleteMapping("/removeFile/all")
	 @ApiOperation(value = "remove all matchscore data",
                   notes = "Returns a 200 when successful.")
	 public Map<String, Boolean> deleteAllFiles() {
		   log.info("matchscore deleteAllFiles..."); 
	       return fileStorageService.removeAllMatchScoreFiles();
	 }

}
