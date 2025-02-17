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

import com.braggbnbsevenseventyseven.domain.SupportTicket;
import com.braggbnbsevenseventyseven.dto.SupportTicketDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketSearchDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketPageDTO;
import com.braggbnbsevenseventyseven.service.SupportTicketService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/supportTicket")
@RestController
public class SupportTicketController {

	private final static Logger logger = LoggerFactory.getLogger(SupportTicketController.class);

	@Autowired
	SupportTicketService supportTicketService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<SupportTicket> getAll() {

		List<SupportTicket> supportTickets = supportTicketService.findAll();
		
		return supportTickets;	
	}

	@GetMapping(value = "/{supportTicketId}")
	@ResponseBody
	public SupportTicketDTO getSupportTicket(@PathVariable Integer supportTicketId) {
		
		return (supportTicketService.getSupportTicketDTOById(supportTicketId));
	}

 	@RequestMapping(value = "/addSupportTicket", method = RequestMethod.POST)
	public ResponseEntity<?> addSupportTicket(@RequestBody SupportTicketDTO supportTicketDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = supportTicketService.addSupportTicket(supportTicketDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/supportTickets")
	public ResponseEntity<SupportTicketPageDTO> getSupportTickets(SupportTicketSearchDTO supportTicketSearchDTO) {
 
		return supportTicketService.getSupportTickets(supportTicketSearchDTO);
	}	

	@RequestMapping(value = "/updateSupportTicket", method = RequestMethod.POST)
	public ResponseEntity<?> updateSupportTicket(@RequestBody SupportTicketDTO supportTicketDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = supportTicketService.updateSupportTicket(supportTicketDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
