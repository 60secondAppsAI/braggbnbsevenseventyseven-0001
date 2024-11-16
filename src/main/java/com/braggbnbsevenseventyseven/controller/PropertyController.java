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

import com.braggbnbsevenseventyseven.domain.Property;
import com.braggbnbsevenseventyseven.dto.PropertyDTO;
import com.braggbnbsevenseventyseven.dto.PropertySearchDTO;
import com.braggbnbsevenseventyseven.dto.PropertyPageDTO;
import com.braggbnbsevenseventyseven.service.PropertyService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/property")
@RestController
public class PropertyController {

	private final static Logger logger = LoggerFactory.getLogger(PropertyController.class);

	@Autowired
	PropertyService propertyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Property> getAll() {

		List<Property> propertys = propertyService.findAll();
		
		return propertys;	
	}

	@GetMapping(value = "/{propertyId}")
	@ResponseBody
	public PropertyDTO getProperty(@PathVariable Integer propertyId) {
		
		return (propertyService.getPropertyDTOById(propertyId));
	}

 	@RequestMapping(value = "/addProperty", method = RequestMethod.POST)
	public ResponseEntity<?> addProperty(@RequestBody PropertyDTO propertyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = propertyService.addProperty(propertyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/propertys")
	public ResponseEntity<PropertyPageDTO> getPropertys(PropertySearchDTO propertySearchDTO) {
 
		return propertyService.getPropertys(propertySearchDTO);
	}	

	@RequestMapping(value = "/updateProperty", method = RequestMethod.POST)
	public ResponseEntity<?> updateProperty(@RequestBody PropertyDTO propertyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = propertyService.updateProperty(propertyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
