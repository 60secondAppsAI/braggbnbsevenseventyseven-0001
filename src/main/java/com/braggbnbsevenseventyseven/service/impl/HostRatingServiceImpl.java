package com.braggbnbsevenseventyseven.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.service.impl.GenericServiceImpl;
import com.braggbnbsevenseventyseven.dao.HostRatingDAO;
import com.braggbnbsevenseventyseven.domain.HostRating;
import com.braggbnbsevenseventyseven.dto.HostRatingDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingSearchDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingPageDTO;
import com.braggbnbsevenseventyseven.dto.HostRatingConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.HostRatingService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class HostRatingServiceImpl extends GenericServiceImpl<HostRating, Integer> implements HostRatingService {

    private final static Logger logger = LoggerFactory.getLogger(HostRatingServiceImpl.class);

	@Autowired
	HostRatingDAO hostRatingDao;

	


	@Override
	public GenericDAO<HostRating, Integer> getDAO() {
		return (GenericDAO<HostRating, Integer>) hostRatingDao;
	}
	
	public List<HostRating> findAll () {
		List<HostRating> hostRatings = hostRatingDao.findAll();
		
		return hostRatings;	
		
	}

	public ResultDTO addHostRating(HostRatingDTO hostRatingDTO, RequestDTO requestDTO) {

		HostRating hostRating = new HostRating();

		hostRating.setHostRatingId(hostRatingDTO.getHostRatingId());


		hostRating.setRating(hostRatingDTO.getRating());


		hostRating.setFeedback(hostRatingDTO.getFeedback());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		hostRating = hostRatingDao.save(hostRating);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<HostRating> getAllHostRatings(Pageable pageable) {
		return hostRatingDao.findAll(pageable);
	}

	public Page<HostRating> getAllHostRatings(Specification<HostRating> spec, Pageable pageable) {
		return hostRatingDao.findAll(spec, pageable);
	}

	public ResponseEntity<HostRatingPageDTO> getHostRatings(HostRatingSearchDTO hostRatingSearchDTO) {
	
			Integer hostRatingId = hostRatingSearchDTO.getHostRatingId(); 
  			String feedback = hostRatingSearchDTO.getFeedback(); 
 			String sortBy = hostRatingSearchDTO.getSortBy();
			String sortOrder = hostRatingSearchDTO.getSortOrder();
			String searchQuery = hostRatingSearchDTO.getSearchQuery();
			Integer page = hostRatingSearchDTO.getPage();
			Integer size = hostRatingSearchDTO.getSize();

	        Specification<HostRating> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, hostRatingId, "hostRatingId"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, feedback, "feedback"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("feedback")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<HostRating> hostRatings = this.getAllHostRatings(spec, pageable);
		
		//System.out.println(String.valueOf(hostRatings.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(hostRatings.getTotalPages()));
		
		List<HostRating> hostRatingsList = hostRatings.getContent();
		
		HostRatingConvertCriteriaDTO convertCriteria = new HostRatingConvertCriteriaDTO();
		List<HostRatingDTO> hostRatingDTOs = this.convertHostRatingsToHostRatingDTOs(hostRatingsList,convertCriteria);
		
		HostRatingPageDTO hostRatingPageDTO = new HostRatingPageDTO();
		hostRatingPageDTO.setHostRatings(hostRatingDTOs);
		hostRatingPageDTO.setTotalElements(hostRatings.getTotalElements());
		return ResponseEntity.ok(hostRatingPageDTO);
	}

	public List<HostRatingDTO> convertHostRatingsToHostRatingDTOs(List<HostRating> hostRatings, HostRatingConvertCriteriaDTO convertCriteria) {
		
		List<HostRatingDTO> hostRatingDTOs = new ArrayList<HostRatingDTO>();
		
		for (HostRating hostRating : hostRatings) {
			hostRatingDTOs.add(convertHostRatingToHostRatingDTO(hostRating,convertCriteria));
		}
		
		return hostRatingDTOs;

	}
	
	public HostRatingDTO convertHostRatingToHostRatingDTO(HostRating hostRating, HostRatingConvertCriteriaDTO convertCriteria) {
		
		HostRatingDTO hostRatingDTO = new HostRatingDTO();
		
		hostRatingDTO.setHostRatingId(hostRating.getHostRatingId());

	
		hostRatingDTO.setRating(hostRating.getRating());

	
		hostRatingDTO.setFeedback(hostRating.getFeedback());

	

		
		return hostRatingDTO;
	}

	public ResultDTO updateHostRating(HostRatingDTO hostRatingDTO, RequestDTO requestDTO) {
		
		HostRating hostRating = hostRatingDao.getById(hostRatingDTO.getHostRatingId());

		hostRating.setHostRatingId(ControllerUtils.setValue(hostRating.getHostRatingId(), hostRatingDTO.getHostRatingId()));

		hostRating.setRating(ControllerUtils.setValue(hostRating.getRating(), hostRatingDTO.getRating()));

		hostRating.setFeedback(ControllerUtils.setValue(hostRating.getFeedback(), hostRatingDTO.getFeedback()));



        hostRating = hostRatingDao.save(hostRating);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public HostRatingDTO getHostRatingDTOById(Integer hostRatingId) {
	
		HostRating hostRating = hostRatingDao.getById(hostRatingId);
			
		
		HostRatingConvertCriteriaDTO convertCriteria = new HostRatingConvertCriteriaDTO();
		return(this.convertHostRatingToHostRatingDTO(hostRating,convertCriteria));
	}







}
