package com.assessment.code.biometricmatch.service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.code.biometricmatch.controller.ImageController;
import com.assessment.code.biometricmatch.exception.FileNotFoundException;
import com.assessment.code.biometricmatch.exception.FileStorageException;
import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.property.FileStorageProperties;
import com.assessment.code.biometricmatch.repository.IDSLImageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService{

	
	 //private final Path fileStorageLocation;
	 
	 @Autowired
	 private IDSLImageRepository imageRepository;

	   /* @Autowired
	    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
	        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
	                .toAbsolutePath().normalize();

	        try {
	            Files.createDirectories(this.fileStorageLocation);
	        } catch (Exception ex) {
	            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
	        }
	    }
	    */
	    
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
				throw new FileNotFoundException("File not found: " + fileName);
			}
			return image;
			//return imageRepository.findByFileName(fileName)
	        //        .orElseThrow(() -> new FileNotFoundException("File not found: " + fileName));
		}
}
