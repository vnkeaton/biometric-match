package com.assessment.code.biometricmatch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.model.MatchResponse;
import com.assessment.code.biometricmatch.model.UploadResponse;
import com.assessment.code.biometricmatch.service.FileStorageService;
import com.assessment.code.biometricmatch.service.MatchingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(value = "biometric", tags = {"biometric Endpoints"})
@RequestMapping("/biometric")
public class ImageController {
	
	private final MatchingService matchingService;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public ImageController(MatchingService matchingService,
			               FileStorageService fileStorageService) {
		this.matchingService = matchingService;
		this.fileStorageService = fileStorageService;
	}
	
	 @GetMapping("/hello/{name}")
	 @ApiOperation(value = "Hello world test",
     notes = "Returns a 200 when successful.")
	 public String hello(@PathVariable("name") String name) {
	        log.info("I am here...");
	        String response = null;
	        response = (name != null && !name.isBlank()&& !name.isEmpty()) ?
	        		"Hello " + name:
	        		"Hello World!";
	        return response;
	 }
	
	/*@PostMapping(path="/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Uploads images for matching",
				  notes = "Returns a 201 when successful.  Will overwrite pre-existing files with the same name.",
				  consumes = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<ImageResponse> uploadImages(
								 @RequestPart("file1") MultipartFile file1,
								 @RequestPart("file2") MultipartFile file2) throws IOException{
		log.info("Uploading file1 " + file1.getName()  + ".");
		ImageResponse response = matchingService.compareImages(file1, file2);
		return new ResponseEntity<ImageResponse>(response, HttpStatus.OK);
	}*/
	
	 /*@PostMapping("/uploadFile")
	 public UploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
	        String fileName = fileStorageService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(fileName)
	                .toUriString();

	        return new UploadResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	  }

	  @PostMapping("/uploadMultipleFiles")
	  public List<UploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	        return Arrays.asList(files)
	                .stream()
	                .map(file -> uploadFile(file))
	                .collect(Collectors.toList());
	  }*/
	    
	  @ApiOperation(value = "Uploads an image",
				    notes = "Returns a 201 when successful.",
				    consumes = MediaType.IMAGE_PNG_VALUE)
	  @PostMapping("/uploadFile")
	  public UploadResponse uploadFileDB(@RequestParam("file") MultipartFile file) {
		    log.info("uploadFileDB...");
	    	IDSLImageModel fileName = fileStorageService.storeFileToDatabase(file);
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(fileName.getFileName())
	                .toUriString();
	        return new UploadResponse(fileName.getFileName(), fileDownloadUri,
	                                  file.getContentType(), file.getSize());
	   }
	  
	   /*@ApiOperation(value = "Retrieve an image",
			         notes = "Returns a 200 when successful.",
			         consumes = MediaType.IMAGE_PNG_VALUE)
       @GetMapping("/uploadFile")
       public UploadResponse getFileDB(@RequestParam("file") MultipartFile file) {
	        log.info("uploadFileDB...");
    	    IDSLImageModel fileName = fileStorageService.storeFileToDatabase(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName.getFileName())
                    .toUriString();
        return new UploadResponse(fileName.getFileName(), fileDownloadUri,
                                  file.getContentType(), file.getSize());
   }*/
	    
	   @PostMapping("/uploadMultipleFiles")
	   @ApiOperation(value = "Uploads multiple images",
		              notes = "Returns a 201 when successful.  Will overwrite pre-existing files with the same name.",
		              consumes = MediaType.IMAGE_PNG_VALUE)
	   public List<UploadResponse> uploadMultipleFilesDB(@RequestParam("files") MultipartFile[] files) {
		    log.info("uploadMultipleFilesDB...");
	        return Arrays.asList(files)
	                .stream()
	                .map(file -> uploadFileDB(file))
	                .collect(Collectors.toList());
	   }
	    
	   @PostMapping("/match")
	   @ApiOperation(value = "Uploads images and creates a match score",
		              notes = "Returns a 201 when successful.  Will overwrite pre-existing files with the same name.",
		              consumes = MediaType.IMAGE_PNG_VALUE)
	   public MatchResponse matchFiles(@RequestParam("files") MultipartFile[] files) {
		    log.info("matchFiles...");
	    	List<IDSLImageModel> images =  Arrays.asList(files)
	                                         .stream()
	                                         .map(file -> fileStorageService.storeFileToDatabase(file))
	                                         .collect(Collectors.toList());
	    	if (images.size() > 2) {
	    		log.info("Only comparing the first 2 files.");
	    	}
	    	//compare the first two images
	    	return matchingService.compareImages(images.get(0), images.get(1));  	
	   }
}
