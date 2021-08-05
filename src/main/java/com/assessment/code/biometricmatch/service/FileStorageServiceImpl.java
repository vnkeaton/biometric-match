package com.assessment.code.biometricmatch.service;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.exception.FileNotFoundException;
import com.assessment.code.biometricmatch.exception.FileStorageException;
import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.repository.IDSLImageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService{

	 private final IDSLImageRepository imageRepository;
	 
	 @Autowired
	 public FileStorageServiceImpl(IDSLImageRepository imageRepository) {
			this.imageRepository = imageRepository;
		}
	    
	@Override
	 public IDSLImageModel storeFileToDatabase(MultipartFile file) {
			log.info("store file to database");
			// Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            IDSLImageModel dbFile = new IDSLImageModel(fileName, file.getContentType(), BigInteger.valueOf(file.getSize()), file.getBytes());

	            return imageRepository.save(dbFile);
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }
	    

		@Override
		public IDSLImageModel getFile(String fileName) {
			log.info("get file: " + fileName);
			IDSLImageModel image = imageRepository.findByFileName(fileName);
			if (image == null) {
				log.info("image not found");
				throw new FileNotFoundException("File not found: " + fileName);
			}
			return image;
		}
}
