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
	private String fileName2;
	
	public MatchResponse(BigDecimal matchResult, String fileName1, String fileName2) {
		super();
		this.matchResult = matchResult;
		this.fileName1 = fileName1;
		this.fileName2 = fileName2;
	}	
}
