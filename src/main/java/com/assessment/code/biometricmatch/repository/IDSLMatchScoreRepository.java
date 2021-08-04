package com.assessment.code.biometricmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.code.biometricmatch.model.IDSLImageModel;
import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;

public interface IDSLMatchScoreRepository extends JpaRepository<IDSLMatchScoreModel, Long> {

}
