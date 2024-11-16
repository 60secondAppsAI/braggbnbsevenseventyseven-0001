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

import com.braggbnbsevenseventyseven.domain.Guide;
import com.braggbnbsevenseventyseven.dto.GuideDTO;
import com.braggbnbsevenseventyseven.dto.GuideSearchDTO;
import com.braggbnbsevenseventyseven.dto.GuidePageDTO;
import com.braggbnbsevenseventyseven.service.GuideService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/guide")
@RestController
public class GuideController {

	private final static Logger logger = LoggerFactory.getLogger(GuideController.class);

	@Autowired
	GuideService guideService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Guide> getAll() {

		List<Guide> guides = guideService.findAll();
		
		return guides;	
	}

	@GetMapping(value = "/{guideId}")
	@ResponseBody
	public GuideDTO getGuide(@PathVariable Integer guideId) {
		
		return (guideService.getGuideDTOById(guideId));
	}

 	@RequestMapping(value = "/addGuide", method = RequestMethod.POST)
	public ResponseEntity<?> addGuide(@RequestBody GuideDTO guideDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = guideService.addGuide(guideDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/guides")
	public ResponseEntity<GuidePageDTO> getGuides(GuideSearchDTO guideSearchDTO) {
 
		return guideService.getGuides(guideSearchDTO);
	}	

	@RequestMapping(value = "/updateGuide", method = RequestMethod.POST)
	public ResponseEntity<?> updateGuide(@RequestBody GuideDTO guideDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = guideService.updateGuide(guideDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
