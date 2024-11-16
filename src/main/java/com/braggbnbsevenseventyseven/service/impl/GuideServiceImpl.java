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
import com.braggbnbsevenseventyseven.dao.GuideDAO;
import com.braggbnbsevenseventyseven.domain.Guide;
import com.braggbnbsevenseventyseven.dto.GuideDTO;
import com.braggbnbsevenseventyseven.dto.GuideSearchDTO;
import com.braggbnbsevenseventyseven.dto.GuidePageDTO;
import com.braggbnbsevenseventyseven.dto.GuideConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.GuideService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class GuideServiceImpl extends GenericServiceImpl<Guide, Integer> implements GuideService {

    private final static Logger logger = LoggerFactory.getLogger(GuideServiceImpl.class);

	@Autowired
	GuideDAO guideDao;

	


	@Override
	public GenericDAO<Guide, Integer> getDAO() {
		return (GenericDAO<Guide, Integer>) guideDao;
	}
	
	public List<Guide> findAll () {
		List<Guide> guides = guideDao.findAll();
		
		return guides;	
		
	}

	public ResultDTO addGuide(GuideDTO guideDTO, RequestDTO requestDTO) {

		Guide guide = new Guide();

		guide.setGuideId(guideDTO.getGuideId());


		guide.setTitle(guideDTO.getTitle());


		guide.setContent(guideDTO.getContent());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		guide = guideDao.save(guide);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Guide> getAllGuides(Pageable pageable) {
		return guideDao.findAll(pageable);
	}

	public Page<Guide> getAllGuides(Specification<Guide> spec, Pageable pageable) {
		return guideDao.findAll(spec, pageable);
	}

	public ResponseEntity<GuidePageDTO> getGuides(GuideSearchDTO guideSearchDTO) {
	
			Integer guideId = guideSearchDTO.getGuideId(); 
 			String title = guideSearchDTO.getTitle(); 
 			String content = guideSearchDTO.getContent(); 
 			String sortBy = guideSearchDTO.getSortBy();
			String sortOrder = guideSearchDTO.getSortOrder();
			String searchQuery = guideSearchDTO.getSearchQuery();
			Integer page = guideSearchDTO.getPage();
			Integer size = guideSearchDTO.getSize();

	        Specification<Guide> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, guideId, "guideId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, title, "title"); 
			
			spec = ControllerUtils.andIfNecessary(spec, content, "content"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("content")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Guide> guides = this.getAllGuides(spec, pageable);
		
		//System.out.println(String.valueOf(guides.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(guides.getTotalPages()));
		
		List<Guide> guidesList = guides.getContent();
		
		GuideConvertCriteriaDTO convertCriteria = new GuideConvertCriteriaDTO();
		List<GuideDTO> guideDTOs = this.convertGuidesToGuideDTOs(guidesList,convertCriteria);
		
		GuidePageDTO guidePageDTO = new GuidePageDTO();
		guidePageDTO.setGuides(guideDTOs);
		guidePageDTO.setTotalElements(guides.getTotalElements());
		return ResponseEntity.ok(guidePageDTO);
	}

	public List<GuideDTO> convertGuidesToGuideDTOs(List<Guide> guides, GuideConvertCriteriaDTO convertCriteria) {
		
		List<GuideDTO> guideDTOs = new ArrayList<GuideDTO>();
		
		for (Guide guide : guides) {
			guideDTOs.add(convertGuideToGuideDTO(guide,convertCriteria));
		}
		
		return guideDTOs;

	}
	
	public GuideDTO convertGuideToGuideDTO(Guide guide, GuideConvertCriteriaDTO convertCriteria) {
		
		GuideDTO guideDTO = new GuideDTO();
		
		guideDTO.setGuideId(guide.getGuideId());

	
		guideDTO.setTitle(guide.getTitle());

	
		guideDTO.setContent(guide.getContent());

	

		
		return guideDTO;
	}

	public ResultDTO updateGuide(GuideDTO guideDTO, RequestDTO requestDTO) {
		
		Guide guide = guideDao.getById(guideDTO.getGuideId());

		guide.setGuideId(ControllerUtils.setValue(guide.getGuideId(), guideDTO.getGuideId()));

		guide.setTitle(ControllerUtils.setValue(guide.getTitle(), guideDTO.getTitle()));

		guide.setContent(ControllerUtils.setValue(guide.getContent(), guideDTO.getContent()));



        guide = guideDao.save(guide);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GuideDTO getGuideDTOById(Integer guideId) {
	
		Guide guide = guideDao.getById(guideId);
			
		
		GuideConvertCriteriaDTO convertCriteria = new GuideConvertCriteriaDTO();
		return(this.convertGuideToGuideDTO(guide,convertCriteria));
	}







}
