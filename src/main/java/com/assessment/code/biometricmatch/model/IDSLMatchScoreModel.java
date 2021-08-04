package com.assessment.code.biometricmatch.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="idsl_match_scores")
@ApiModel(value = "IDSL Match Score Model", description = "Model for persisting match scores.")
public class IDSLMatchScoreModel {
	
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "file 1 can not be null")
	@Size(min = 1, max = 250, message="Invalid identifier for file 1! Max length is 250 bytes")
	@ApiModelProperty(notes = "file 1 identifier")
	@JoinColumn(name = "id")
	private String file1Id;
	
	@NotNull(message = "file 2 can not be null")
	@Size(min = 1, max = 250, message="Invalid identifier for file 2! Max length is 250 bytes")
	@ApiModelProperty(notes = "file 2 identifier")
	@JoinColumn(name = "id")
	private String file2Id;
	
	@Column(name="match_score", nullable=false)
	@ApiModelProperty(value = "match score")
	@Digits(integer = 10, fraction = 12, message = "Total actual revenue has format 10.12")
	private BigDecimal matchScore;

	public IDSLMatchScoreModel(IDSLImageModel file1, IDSLImageModel file2, BigDecimal matchScore) {
		super();
		this.file1Id = file1.getId();
		this.file2Id = file2.getId();
		this.matchScore = matchScore;
	}

	public IDSLMatchScoreModel(String uuid1, String uuid2, BigDecimal matchScore2) {
		// TODO Auto-generated constructor stub
	}
	
	

}
