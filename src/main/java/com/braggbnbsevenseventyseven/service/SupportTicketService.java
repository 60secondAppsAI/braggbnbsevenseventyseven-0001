package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.SupportTicket;
import com.braggbnbsevenseventyseven.dto.SupportTicketDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketSearchDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketPageDTO;
import com.braggbnbsevenseventyseven.dto.SupportTicketConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SupportTicketService extends GenericService<SupportTicket, Integer> {

	List<SupportTicket> findAll();

	ResultDTO addSupportTicket(SupportTicketDTO supportTicketDTO, RequestDTO requestDTO);

	ResultDTO updateSupportTicket(SupportTicketDTO supportTicketDTO, RequestDTO requestDTO);

    Page<SupportTicket> getAllSupportTickets(Pageable pageable);

    Page<SupportTicket> getAllSupportTickets(Specification<SupportTicket> spec, Pageable pageable);

	ResponseEntity<SupportTicketPageDTO> getSupportTickets(SupportTicketSearchDTO supportTicketSearchDTO);
	
	List<SupportTicketDTO> convertSupportTicketsToSupportTicketDTOs(List<SupportTicket> supportTickets, SupportTicketConvertCriteriaDTO convertCriteria);

	SupportTicketDTO getSupportTicketDTOById(Integer supportTicketId);







}





