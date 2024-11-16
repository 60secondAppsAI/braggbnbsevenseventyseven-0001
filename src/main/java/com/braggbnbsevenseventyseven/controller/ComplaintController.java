package com.braggbnbsevenseventyseven.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.braggbnbsevenseventyseven.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.braggbnbsevenseventyseven.domain.Complaint;
import com.braggbnbsevenseventyseven.dto.ComplaintDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintSearchDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintPageDTO;
import com.braggbnbsevenseventyseven.service.ComplaintService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/complaint")
@RestController
public class ComplaintController {

	private final static Logger logger = LoggerFactory.getLogger(ComplaintController.class);

	@Autowired
	ComplaintService complaintService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Complaint> getAll() {

		List<Complaint> complaints = complaintService.findAll();
		
		return complaints;	
	}

	@GetMapping(value = "/{complaintId}")
	@ResponseBody
	public ComplaintDTO getComplaint(@PathVariable Integer complaintId) {
		
		return (complaintService.getComplaintDTOById(complaintId));
	}

 	@RequestMapping(value = "/addComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> addComplaint(@RequestBody ComplaintDTO complaintDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complaintService.addComplaint(complaintDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/complaints")
	public ResponseEntity<ComplaintPageDTO> getComplaints(ComplaintSearchDTO complaintSearchDTO) {
 
		return complaintService.getComplaints(complaintSearchDTO);
	}	

	@RequestMapping(value = "/updateComplaint", method = RequestMethod.POST)
	public ResponseEntity<?> updateComplaint(@RequestBody ComplaintDTO complaintDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = complaintService.updateComplaint(complaintDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}