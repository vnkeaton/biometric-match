package com.assessment.code.biometricmatch.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.exception.DatabaseConstraintException;
import com.assessment.code.biometricmatch.exception.EmptyFileException;
import com.assessment.code.biometricmatch.exception.FileNotFoundException;
import com.assessment.code.biometricmatch.exception.FileStorageException;
import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.repository.IDSLImageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageFileStorageServiceImpl implements ImageFileStorageService{

	 private final IDSLImageRepository imageRepository;
	 
	 @Autowired
	 public ImageFileStorageServiceImpl(IDSLImageRepository imageRepository) {
			this.imageRepository = imageRepository;
		}
	    
	@Override
	 public IDSLImageModel storeImageFile(MultipartFile file) {
			log.info("store image file to database");
			// Check for empty file and normalize file name
			if (file.isEmpty()) {
				throw new EmptyFileException("File is empty.  File name: "+ file.getName());
			}
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        // Check if the file's name contains invalid characters
	         if(fileName.contains("..")) {
	            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	         } 
	         IDSLImageModel dbFile;
			 try {
				dbFile = new IDSLImageModel(fileName, file.getContentType(), BigInteger.valueOf(file.getSize()), file.getBytes());
			 } catch (IOException e1) {
				throw new FileStorageException("Issues obtaining new instance for image: " + fileName);
			 }	
			
			 IDSLImageModel savedImage = null;
			 try {
				 savedImage = imageRepository.save(dbFile);
			 } catch(Exception e) {
				 throw new DatabaseConstraintException("Issues saving image to database.  Check filename uniqueness.");
			 }
			 return savedImage;
	    }
	    
		@Override
		public IDSLImageModel getImageFile(String fileName) {
			log.info("get image file: " + fileName);
			IDSLImageModel image = imageRepository.findByFileName(fileName);
			if (image == null) {
				log.info("image not found");
				throw new FileNotFoundException("File not found: " + fileName);
			}
			return image;
		}
		
		@Override
		public Map<String, Boolean> removeImageFile(String fileName) {
			log.info("remove image file: " + fileName);
			IDSLImageModel image = null;
			image = getImageFile(fileName);
			if (image == null) {
				throw new FileNotFoundException("File not found: " + fileName);
			}
			try {
				imageRepository.delete(image);
			}
			catch(Exception e) {
				throw new FileStorageException("Trouble removing file: " + fileName);
			}
		    Map<String, Boolean> response = new HashMap<>();
		    response.put("deleted: " + fileName, Boolean.TRUE);
		    return response;
		}

		@Override
		public List<IDSLImageModel> getAllFiles() {
			log.info("get all files");
			List<IDSLImageModel> images = imageRepository.findAll();
			return images;
		}

		@Override
		public Map<String, Boolean> removeAllImageFiles() {
			log.info("removing all image files");
			Map<String, Boolean> response = new HashMap<>();
			List<IDSLImageModel> images = getAllFiles();
			for (IDSLImageModel image: images) {
				Map<String, Boolean> map = removeImageFile(image.getFileName());
				response.putAll(map);
			}
			return response;
		}
		
		@Override
		public boolean doesImageFileExist(String fileName) {
			log.info("does image file exists: " + fileName);
			IDSLImageModel image =  imageRepository.findByFileName(fileName);
			return (image != null) ? true : false; 
		}
}
