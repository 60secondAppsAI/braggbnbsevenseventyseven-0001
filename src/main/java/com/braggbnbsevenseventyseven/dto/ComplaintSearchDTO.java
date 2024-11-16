package com.braggbnbsevenseventyseven.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplaintSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer complaintId;
	
	private String issueDescription;
	
	private Date complaintDate;
	
}
