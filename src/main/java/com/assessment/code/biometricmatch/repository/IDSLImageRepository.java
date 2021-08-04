package com.assessment.code.biometricmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.code.biometricmatch.model.IDSLImageModel;

public interface IDSLImageRepository extends JpaRepository<IDSLImageModel, String> {
	//Optional<IDSLImageModel> findByName(String name);
}
