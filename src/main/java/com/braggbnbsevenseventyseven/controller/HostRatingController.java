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

import com.braggbnbsevenseventyseven.domain.HostRating;
import com.braggbnbsevenseventyseven.dto.HostRatingDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingSearchDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingPageDTO;
import com.braggbnbsevenseventyseven.service.HostRatingService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/hostRating")
@RestController
public class HostRatingController {

	private final static Logger logger = LoggerFactory.getLogger(HostRatingController.class);

	@Autowired
	HostRatingService hostRatingService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<HostRating> getAll() {

		List<HostRating> hostRatings = hostRatingService.findAll();
		
		return hostRatings;	
	}

	@GetMapping(value = "/{hostRatingId}")
	@ResponseBody
	public HostRatingDTO getHostRating(@PathVariable Integer hostRatingId) {
		
		return (hostRatingService.getHostRatingDTOById(hostRatingId));
	}

 	@RequestMapping(value = "/addHostRating", method = RequestMethod.POST)
	public ResponseEntity<?> addHostRating(@RequestBody HostRatingDTO hostRatingDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = hostRatingService.addHostRating(hostRatingDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/hostRatings")
	public ResponseEntity<HostRatingPageDTO> getHostRatings(HostRatingSearchDTO hostRatingSearchDTO) {
 
		return hostRatingService.getHostRatings(hostRatingSearchDTO);
	}	

	@RequestMapping(value = "/updateHostRating", method = RequestMethod.POST)
	public ResponseEntity<?> updateHostRating(@RequestBody HostRatingDTO hostRatingDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = hostRatingService.updateHostRating(hostRatingDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
