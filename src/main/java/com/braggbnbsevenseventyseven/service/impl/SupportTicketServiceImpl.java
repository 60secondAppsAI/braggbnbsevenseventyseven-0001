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
import com.braggbnbsevenseventyseven.dao.SupportTicketDAO;
import com.braggbnbsevenseventyseven.domain.SupportTicket;
import com.braggbnbsevenseventyseven.dto.SupportTicketDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketSearchDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketPageDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.SupportTicketService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class SupportTicketServiceImpl extends GenericServiceImpl<SupportTicket, Integer> implements SupportTicketService {

    private final static Logger logger = LoggerFactory.getLogger(SupportTicketServiceImpl.class);

	@Autowired
	SupportTicketDAO supportTicketDao;

	


	@Override
	public GenericDAO<SupportTicket, Integer> getDAO() {
		return (GenericDAO<SupportTicket, Integer>) supportTicketDao;
	}
	
	public List<SupportTicket> findAll () {
		List<SupportTicket> supportTickets = supportTicketDao.findAll();
		
		return supportTickets;	
		
	}

	public ResultDTO addSupportTicket(SupportTicketDTO supportTicketDTO, RequestDTO requestDTO) {

		SupportTicket supportTicket = new SupportTicket();

		supportTicket.setSupportTicketId(supportTicketDTO.getSupportTicketId());


		supportTicket.setIssueDescription(supportTicketDTO.getIssueDescription());


		supportTicket.setTicketStatus(supportTicketDTO.getTicketStatus());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		supportTicket = supportTicketDao.save(supportTicket);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<SupportTicket> getAllSupportTickets(Pageable pageable) {
		return supportTicketDao.findAll(pageable);
	}

	public Page<SupportTicket> getAllSupportTickets(Specification<SupportTicket> spec, Pageable pageable) {
		return supportTicketDao.findAll(spec, pageable);
	}

	public ResponseEntity<SupportTicketPageDTO> getSupportTickets(SupportTicketSearchDTO supportTicketSearchDTO) {
	
			Integer supportTicketId = supportTicketSearchDTO.getSupportTicketId(); 
 			String issueDescription = supportTicketSearchDTO.getIssueDescription(); 
 			String ticketStatus = supportTicketSearchDTO.getTicketStatus(); 
 			String sortBy = supportTicketSearchDTO.getSortBy();
			String sortOrder = supportTicketSearchDTO.getSortOrder();
			String searchQuery = supportTicketSearchDTO.getSearchQuery();
			Integer page = supportTicketSearchDTO.getPage();
			Integer size = supportTicketSearchDTO.getSize();

	        Specification<SupportTicket> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, supportTicketId, "supportTicketId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, issueDescription, "issueDescription"); 
			
			spec = ControllerUtils.andIfNecessary(spec, ticketStatus, "ticketStatus"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("issueDescription")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("ticketStatus")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<SupportTicket> supportTickets = this.getAllSupportTickets(spec, pageable);
		
		//System.out.println(String.valueOf(supportTickets.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(supportTickets.getTotalPages()));
		
		List<SupportTicket> supportTicketsList = supportTickets.getContent();
		
		SupportTicketConvertCriteriaDTO convertCriteria = new SupportTicketConvertCriteriaDTO();
		List<SupportTicketDTO> supportTicketDTOs = this.convertSupportTicketsToSupportTicketDTOs(supportTicketsList,convertCriteria);
		
		SupportTicketPageDTO supportTicketPageDTO = new SupportTicketPageDTO();
		supportTicketPageDTO.setSupportTickets(supportTicketDTOs);
		supportTicketPageDTO.setTotalElements(supportTickets.getTotalElements());
		return ResponseEntity.ok(supportTicketPageDTO);
	}

	public List<SupportTicketDTO> convertSupportTicketsToSupportTicketDTOs(List<SupportTicket> supportTickets, SupportTicketConvertCriteriaDTO convertCriteria) {
		
		List<SupportTicketDTO> supportTicketDTOs = new ArrayList<SupportTicketDTO>();
		
		for (SupportTicket supportTicket : supportTickets) {
			supportTicketDTOs.add(convertSupportTicketToSupportTicketDTO(supportTicket,convertCriteria));
		}
		
		return supportTicketDTOs;

	}
	
	public SupportTicketDTO convertSupportTicketToSupportTicketDTO(SupportTicket supportTicket, SupportTicketConvertCriteriaDTO convertCriteria) {
		
		SupportTicketDTO supportTicketDTO = new SupportTicketDTO();
		
		supportTicketDTO.setSupportTicketId(supportTicket.getSupportTicketId());

	
		supportTicketDTO.setIssueDescription(supportTicket.getIssueDescription());

	
		supportTicketDTO.setTicketStatus(supportTicket.getTicketStatus());

	

		
		return supportTicketDTO;
	}

	public ResultDTO updateSupportTicket(SupportTicketDTO supportTicketDTO, RequestDTO requestDTO) {
		
		SupportTicket supportTicket = supportTicketDao.getById(supportTicketDTO.getSupportTicketId());

		supportTicket.setSupportTicketId(ControllerUtils.setValue(supportTicket.getSupportTicketId(), supportTicketDTO.getSupportTicketId()));

		supportTicket.setIssueDescription(ControllerUtils.setValue(supportTicket.getIssueDescription(), supportTicketDTO.getIssueDescription()));

		supportTicket.setTicketStatus(ControllerUtils.setValue(supportTicket.getTicketStatus(), supportTicketDTO.getTicketStatus()));



        supportTicket = supportTicketDao.save(supportTicket);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SupportTicketDTO getSupportTicketDTOById(Integer supportTicketId) {
	
		SupportTicket supportTicket = supportTicketDao.getById(supportTicketId);
			
		
		SupportTicketConvertCriteriaDTO convertCriteria = new SupportTicketConvertCriteriaDTO();
		return(this.convertSupportTicketToSupportTicketDTO(supportTicket,convertCriteria));
	}







}
