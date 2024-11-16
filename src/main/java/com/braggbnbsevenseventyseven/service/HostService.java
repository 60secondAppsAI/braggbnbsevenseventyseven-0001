package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.Host;
import com.braggbnbsevenseventyseven.dto.HostDTO;
import com.braggbnbsevenseventyseven.dto.HostSearchDTO;
import com.braggbnbsevenseventyseven.dto.HostPageDTO;
import com.braggbnbsevenseventyseven.dto.HostConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface HostService extends GenericService<Host, Integer> {

	List<Host> findAll();

	ResultDTO addHost(HostDTO hostDTO, RequestDTO requestDTO);

	ResultDTO updateHost(HostDTO hostDTO, RequestDTO requestDTO);

    Page<Host> getAllHosts(Pageable pageable);

    Page<Host> getAllHosts(Specification<Host> spec, Pageable pageable);

	ResponseEntity<HostPageDTO> getHosts(HostSearchDTO hostSearchDTO);
	
	List<HostDTO> convertHostsToHostDTOs(List<Host> hosts, HostConvertCriteriaDTO convertCriteria);

	HostDTO getHostDTOById(Integer hostId);







}





