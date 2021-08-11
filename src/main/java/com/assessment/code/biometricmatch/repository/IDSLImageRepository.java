package com.assessment.code.biometricmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.code.biometricmatch.model.IDSLImageModel;

@Repository
public interface IDSLImageRepository extends JpaRepository<IDSLImageModel, String> {

	IDSLImageModel findByFileName(String fileName);
}
