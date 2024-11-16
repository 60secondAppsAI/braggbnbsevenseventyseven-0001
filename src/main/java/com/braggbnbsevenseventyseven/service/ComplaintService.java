package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.Complaint;
import com.braggbnbsevenseventyseven.dto.ComplaintDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintSearchDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintPageDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ComplaintService extends GenericService<Complaint, Integer> {

	List<Complaint> findAll();

	ResultDTO addComplaint(ComplaintDTO complaintDTO, RequestDTO requestDTO);

	ResultDTO updateComplaint(ComplaintDTO complaintDTO, RequestDTO requestDTO);

    Page<Complaint> getAllComplaints(Pageable pageable);

    Page<Complaint> getAllComplaints(Specification<Complaint> spec, Pageable pageable);

	ResponseEntity<ComplaintPageDTO> getComplaints(ComplaintSearchDTO complaintSearchDTO);
	
	List<ComplaintDTO> convertComplaintsToComplaintDTOs(List<Complaint> complaints, ComplaintConvertCriteriaDTO convertCriteria);

	ComplaintDTO getComplaintDTOById(Integer complaintId);







}





