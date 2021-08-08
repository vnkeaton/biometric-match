package com.assessment.code.biometricmatch.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="idsl_match_scores",
       indexes = {@Index( name="uniqueFNIndex", columnList = "file1Name,file2Name", unique = true)})
@ApiModel(value = "IDSL Match Score Model", description = "Model for persisting match scores.")
public class IDSLMatchScoreModel {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Size(min = 1, max = 250, message="Invalid dir for file 1! Max length is 250 bytes")
	@ApiModelProperty(notes = "dir for file 1 name")
	private String dir1;
	
	@NotNull(message = "file name 1 can not be null")
	@Size(min = 1, max = 250, message="Invalid name for file 1! Max length is 250 bytes")
	@ApiModelProperty(notes = "file 1 name")
	private String file1Name;
	
	@Size(min = 1, max = 250, message="Invalid dir for file 2! Max length is 250 bytes")
	@ApiModelProperty(notes = "dir for file 2 name")
	private String dir2;
	
	@NotNull(message = "file 2 name can not be null")
	@Size(min = 1, max = 250, message="Invalid name for file 2! Max length is 250 bytes")
	@ApiModelProperty(notes = "file 2 name")
	private String file2Name;
	
	@Column(name="match_score", nullable=false)
	@ApiModelProperty(value = "match score")
	@Digits(integer = 10, fraction = 12, message = "Score has format 10.12")
	private BigDecimal matchScore;

	public IDSLMatchScoreModel(String dir1, String file1Name, String dir2, String file2Name, BigDecimal matchScore) {
		super();
		this.dir1 = dir1;
		this.file1Name = file1Name;
		this.dir2 = dir2;
		this.file2Name = file2Name;
		this.matchScore = matchScore;
	}
}
