package com.assessment.code.biometricmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;

public interface IDSLMatchScoreRepository extends JpaRepository<IDSLMatchScoreModel, String> {

	@Query (value = "select n from IDSLMatchScoreModel n where n.file1Name = :file1Name and "
		                                                    + "n.file2Name = :file2Name")
	IDSLMatchScoreModel findByFileName1AndFileName2(@Param ("file1Name") String file1Name, 
			                                        @Param ("file2Name") String file2Name);

}
