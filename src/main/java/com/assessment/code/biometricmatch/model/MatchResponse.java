package com.assessment.code.biometricmatch.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
