package com.assessment.code.biometricmatch.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.Value;

@Value
public class MatchResponse {
	
	private BigDecimal matchResult;
	private String fileName1;
	private String fileId1;
	private String fileName2;
	private String fileId2;
	
	public MatchResponse(BigDecimal matchResult, String fileName1, String fileId1, String fileName2, String fileId2) {
		super();
		this.matchResult = matchResult;
		this.fileName1 = fileName1;
		this.fileId1 = fileId1;
		this.fileName2 = fileName2;
		this.fileId2 = fileId2;
	}
	

}
