package com.assessment.code.biometricmatch.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="idsl_image")
@ApiModel(value = "IDSL ImageModel", description = "Model for persisting image characteristics.")
public class IDSLImageModel {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@NotNull(message = "file name must not be null")
	@Column(name = "file_name", unique = true)
	private String fileName;

	private String fileType;
	
	private BigInteger size;

	@Lob
	private byte[] data;

	public IDSLImageModel(String fileName, String fileType, BigInteger size, byte[] data) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.size = size;
		this.data = data;
	}


	/*public IDSLImageModel(String fileName, String fileType, BigInteger size, byte[] data) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.size = size;
		this.data = data;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonIgnore
	private String id;
	
	@Column(name="file_name", nullable=false)
	@NotNull(message = "fileName can not be null.")
	@Size(min = 1, max = 250, message="Invalid fileName! Max length is 250 bytes")
	@ApiModelProperty(notes = "file name")
	private String fileName;
	
	@Column(name="file_type", nullable=false)
	@NotNull(message = "fileType can not be null.")
	@Size(min = 1, max = 250, message="Invalid fileType! Max length is 250 bytes")
	@ApiModelProperty(notes = "image file type")
	private String fileType;
	
	@Column(name = "size", nullable=false)
	@NotNull(message = "Image size can not be null.")
	@ApiModelProperty("image size")
	@Digits(integer = 12, fraction = 0, message = "Image size can not exceed 12 digits.")
	private BigInteger size;
	
	@Lob
	@Column(name = "data", columnDefinition="BLOB", nullable=false)
	@NotNull(message = "Image data can not be null.")
	@ApiModelProperty("image data")
	private byte[] data;
	*/
	
	
	
}
