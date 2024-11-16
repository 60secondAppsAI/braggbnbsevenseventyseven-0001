package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.HostRating;
import com.braggbnbsevenseventyseven.dto.HostRatingDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingSearchDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingPageDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface HostRatingService extends GenericService<HostRating, Integer> {

	List<HostRating> findAll();

	ResultDTO addHostRating(HostRatingDTO hostRatingDTO, RequestDTO requestDTO);

	ResultDTO updateHostRating(HostRatingDTO hostRatingDTO, RequestDTO requestDTO);

    Page<HostRating> getAllHostRatings(Pageable pageable);

    Page<HostRating> getAllHostRatings(Specification<HostRating> spec, Pageable pageable);

	ResponseEntity<HostRatingPageDTO> getHostRatings(HostRatingSearchDTO hostRatingSearchDTO);
	
	List<HostRatingDTO> convertHostRatingsToHostRatingDTOs(List<HostRating> hostRatings, HostRatingConvertCriteriaDTO convertCriteria);

	HostRatingDTO getHostRatingDTOById(Integer hostRatingId);







}





