package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.Property;
import com.braggbnbsevenseventyseven.dto.PropertyDTO;
import com.braggbnbsevenseventyseven.dto.PropertySearchDTO;
import com.braggbnbsevenseventyseven.dto.PropertyPageDTO;
import com.braggbnbsevenseventyseven.dto.PropertyConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PropertyService extends GenericService<Property, Integer> {

	List<Property> findAll();

	ResultDTO addProperty(PropertyDTO propertyDTO, RequestDTO requestDTO);

	ResultDTO updateProperty(PropertyDTO propertyDTO, RequestDTO requestDTO);

    Page<Property> getAllPropertys(Pageable pageable);

    Page<Property> getAllPropertys(Specification<Property> spec, Pageable pageable);

	ResponseEntity<PropertyPageDTO> getPropertys(PropertySearchDTO propertySearchDTO);
	
	List<PropertyDTO> convertPropertysToPropertyDTOs(List<Property> propertys, PropertyConvertCriteriaDTO convertCriteria);

	PropertyDTO getPropertyDTOById(Integer propertyId);







}





