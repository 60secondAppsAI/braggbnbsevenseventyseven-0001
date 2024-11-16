package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.Guide;
import com.braggbnbsevenseventyseven.dto.GuideDTO;
import com.braggbnbsevenseventyseven.dto.GuideSearchDTO;
import com.braggbnbsevenseventyseven.dto.GuidePageDTO;
import com.braggbnbsevenseventyseven.dto.GuideConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GuideService extends GenericService<Guide, Integer> {

	List<Guide> findAll();

	ResultDTO addGuide(GuideDTO guideDTO, RequestDTO requestDTO);

	ResultDTO updateGuide(GuideDTO guideDTO, RequestDTO requestDTO);

    Page<Guide> getAllGuides(Pageable pageable);

    Page<Guide> getAllGuides(Specification<Guide> spec, Pageable pageable);

	ResponseEntity<GuidePageDTO> getGuides(GuideSearchDTO guideSearchDTO);
	
	List<GuideDTO> convertGuidesToGuideDTOs(List<Guide> guides, GuideConvertCriteriaDTO convertCriteria);

	GuideDTO getGuideDTOById(Integer guideId);







}





