package com.assessment.code.biometricmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assessment.code.biometricmatch.model.IDSLMatchScoreModel;

@Repository
public interface IDSLMatchScoreRepository extends JpaRepository<IDSLMatchScoreModel, String> {

	@Query (value = "select n from IDSLMatchScoreModel n where n.file1Name = :file1Name and "
		                                                    + "n.file2Name = :file2Name")
	IDSLMatchScoreModel findByFileName1AndFileName2(@Param ("file1Name") String file1Name, 
			                                        @Param ("file2Name") String file2Name);

	Iterable<IDSLMatchScoreModel> findByFile1Name(String fileName);
	
	@Query (value = "select n from IDSLMatchScoreModel n where n.file1Name = :fileName or "
                                                                 + "n.file2Name = :fileName")
	Iterable<IDSLMatchScoreModel> findByFileName1OrFileName(String fileName);

}
