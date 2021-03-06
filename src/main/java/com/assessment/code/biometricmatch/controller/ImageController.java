package com.assessment.code.biometricmatch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.exception.EmptyFileException;
import com.assessment.code.biometricmatch.exception.FileNotFoundException;
import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.model.MatchResponse;
import com.assessment.code.biometricmatch.model.UploadResponse;
import com.assessment.code.biometricmatch.service.ImageFileStorageService;
import com.assessment.code.biometricmatch.service.MatchingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(value = "biometric", tags = {"biometric Image Endpoints"})
@RequestMapping("/biometric/image")
public class ImageController {
	
	private final MatchingService matchingService;
	private final ImageFileStorageService fileStorageService;
	
	@Autowired
	public ImageController(MatchingService matchingService,
			               ImageFileStorageService fileStorageService) {
		this.matchingService = matchingService;
		this.fileStorageService = fileStorageService;
	}
	 
	 @PostMapping("/uploadFile")   
	 @ApiOperation(value = "Uploads an image",
				    notes = "Returns a 201 when successful.",
				    consumes = MediaType.IMAGE_PNG_VALUE)	 
	  public UploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
		    log.info("image uploadFileDB, file: " + file.getName());
		    if (file.isEmpty()) {
		    	throw new EmptyFileException("Uploaded file is empty");
		    }
	    	IDSLImageModel fileName = fileStorageService.storeImageFile(file);
	    	log.info("uploadFile=" + fileName);
	        return new UploadResponse(fileName.getFileName(), file.getContentType(), file.getSize());
	   }
	    
	   @PostMapping("/uploadMultipleFiles")
	   @ApiOperation(value = "Uploads multiple images",
		              notes = "Returns a 201 when successful.  Will overwrite pre-existing files with the same name.",
		              consumes = MediaType.IMAGE_PNG_VALUE)
	   public List<UploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		    log.info("image uploadMultipleFiles...");
		    if (files.length == 0) {
		    	throw new FileNotFoundException("There are no files to process.");
		    }
	        return Arrays.asList(files)
	                .stream()
	                .map(file -> uploadFile(file))
	                .collect(Collectors.toList());
	   }
	    
	   @PostMapping("/match")
	   @ApiOperation(value = "Uploads images and creates a match score",
		              notes = "Returns a 200 when successful.",
		              consumes = MediaType.IMAGE_PNG_VALUE)
	   public MatchResponse matchFiles(@RequestParam("files") MultipartFile[] files) {
		    log.info("image match");
		    for (MultipartFile file: files) {
		    	 if (file == null || file.isEmpty()) { 	
		            throw new EmptyFileException("2 image files are required for processing");
		         }
		    }
		    List<IDSLImageModel> images = matchingService.processMatchFiles(files); 
	    	return matchingService.compareImages(images.get(0), images.get(1));  	    		
	   }
	   
	   @GetMapping("/downloadFile/{fileName}")
	   @ApiOperation(value = "Download an image",
		              notes = "Returns a 200 when successful.",
		              produces = MediaType.IMAGE_PNG_VALUE)
	   public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		   log.info("image downloadFile, filename: " + fileName);
		   IDSLImageModel image = fileStorageService.getImageFile(fileName);
	       return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(image.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
	                .body(new ByteArrayResource(image.getData()));
	    }   
	   
	   @GetMapping("/downloadFile/all")
	   @ApiOperation(value = "Downloads all images rows from database",
		              notes = "Returns a 200 when successful.",
		              produces = MediaType.IMAGE_PNG_VALUE)
	   public ResponseEntity<Iterable<IDSLImageModel>> downloadAllFiles(HttpServletRequest request) {
		   log.info("image downloadAllFiles...");
		   List<IDSLImageModel> images = fileStorageService.getAllFiles();
		   return new ResponseEntity<Iterable<IDSLImageModel>>(images, HttpStatus.OK);
	    }   
	   
	   @DeleteMapping("/removeFile/{fileName}")
	   @ApiOperation(value = "remove image",
                     notes = "Returns a 200 when successful.")
	   public Map<String, Boolean> deleteFile(@PathVariable(value = "fileName") String fileName)
	                                          throws FileNotFoundException {
	       log.info("image deleteFile, filename: " + fileName);
	       return fileStorageService.removeImageFile(fileName);
	   }
	   
	   @DeleteMapping("/removeFile/all")
	   @ApiOperation(value = "remove images",
                     notes = "Returns a 200 when successful.")
	   public Map<String, Boolean> deleteAllFiles() {
		   log.info("image deleteAllFiles..."); 
	       return fileStorageService.removeAllImageFiles();
	   }
	   
	   //TODO Add Put to finish out CRUD operations?
}
