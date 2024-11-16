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
import com.braggbnbsevenseventyseven.dao.ComplaintDAO;
import com.braggbnbsevenseventyseven.domain.Complaint;
import com.braggbnbsevenseventyseven.dto.ComplaintDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintSearchDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintPageDTO;
import com.braggbnbsevenseventyseven.dto.ComplaintConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.ComplaintService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class ComplaintServiceImpl extends GenericServiceImpl<Complaint, Integer> implements ComplaintService {

    private final static Logger logger = LoggerFactory.getLogger(ComplaintServiceImpl.class);

	@Autowired
	ComplaintDAO complaintDao;

	


	@Override
	public GenericDAO<Complaint, Integer> getDAO() {
		return (GenericDAO<Complaint, Integer>) complaintDao;
	}
	
	public List<Complaint> findAll () {
		List<Complaint> complaints = complaintDao.findAll();
		
		return complaints;	
		
	}

	public ResultDTO addComplaint(ComplaintDTO complaintDTO, RequestDTO requestDTO) {

		Complaint complaint = new Complaint();

		complaint.setComplaintId(complaintDTO.getComplaintId());


		complaint.setIssueDescription(complaintDTO.getIssueDescription());


		complaint.setComplaintDate(complaintDTO.getComplaintDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		complaint = complaintDao.save(complaint);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Complaint> getAllComplaints(Pageable pageable) {
		return complaintDao.findAll(pageable);
	}

	public Page<Complaint> getAllComplaints(Specification<Complaint> spec, Pageable pageable) {
		return complaintDao.findAll(spec, pageable);
	}

	public ResponseEntity<ComplaintPageDTO> getComplaints(ComplaintSearchDTO complaintSearchDTO) {
	
			Integer complaintId = complaintSearchDTO.getComplaintId(); 
 			String issueDescription = complaintSearchDTO.getIssueDescription(); 
   			String sortBy = complaintSearchDTO.getSortBy();
			String sortOrder = complaintSearchDTO.getSortOrder();
			String searchQuery = complaintSearchDTO.getSearchQuery();
			Integer page = complaintSearchDTO.getPage();
			Integer size = complaintSearchDTO.getSize();

	        Specification<Complaint> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, complaintId, "complaintId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, issueDescription, "issueDescription"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("issueDescription")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Complaint> complaints = this.getAllComplaints(spec, pageable);
		
		//System.out.println(String.valueOf(complaints.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(complaints.getTotalPages()));
		
		List<Complaint> complaintsList = complaints.getContent();
		
		ComplaintConvertCriteriaDTO convertCriteria = new ComplaintConvertCriteriaDTO();
		List<ComplaintDTO> complaintDTOs = this.convertComplaintsToComplaintDTOs(complaintsList,convertCriteria);
		
		ComplaintPageDTO complaintPageDTO = new ComplaintPageDTO();
		complaintPageDTO.setComplaints(complaintDTOs);
		complaintPageDTO.setTotalElements(complaints.getTotalElements());
		return ResponseEntity.ok(complaintPageDTO);
	}

	public List<ComplaintDTO> convertComplaintsToComplaintDTOs(List<Complaint> complaints, ComplaintConvertCriteriaDTO convertCriteria) {
		
		List<ComplaintDTO> complaintDTOs = new ArrayList<ComplaintDTO>();
		
		for (Complaint complaint : complaints) {
			complaintDTOs.add(convertComplaintToComplaintDTO(complaint,convertCriteria));
		}
		
		return complaintDTOs;

	}
	
	public ComplaintDTO convertComplaintToComplaintDTO(Complaint complaint, ComplaintConvertCriteriaDTO convertCriteria) {
		
		ComplaintDTO complaintDTO = new ComplaintDTO();
		
		complaintDTO.setComplaintId(complaint.getComplaintId());

	
		complaintDTO.setIssueDescription(complaint.getIssueDescription());

	
		complaintDTO.setComplaintDate(complaint.getComplaintDate());

	

		
		return complaintDTO;
	}

	public ResultDTO updateComplaint(ComplaintDTO complaintDTO, RequestDTO requestDTO) {
		
		Complaint complaint = complaintDao.getById(complaintDTO.getComplaintId());

		complaint.setComplaintId(ControllerUtils.setValue(complaint.getComplaintId(), complaintDTO.getComplaintId()));

		complaint.setIssueDescription(ControllerUtils.setValue(complaint.getIssueDescription(), complaintDTO.getIssueDescription()));

		complaint.setComplaintDate(ControllerUtils.setValue(complaint.getComplaintDate(), complaintDTO.getComplaintDate()));



        complaint = complaintDao.save(complaint);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ComplaintDTO getComplaintDTOById(Integer complaintId) {
	
		Complaint complaint = complaintDao.getById(complaintId);
			
		
		ComplaintConvertCriteriaDTO convertCriteria = new ComplaintConvertCriteriaDTO();
		return(this.convertComplaintToComplaintDTO(complaint,convertCriteria));
	}







}
