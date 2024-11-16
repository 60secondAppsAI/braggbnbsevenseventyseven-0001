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
import com.braggbnbsevenseventyseven.dao.GuestDAO;
import com.braggbnbsevenseventyseven.domain.Guest;
import com.braggbnbsevenseventyseven.dto.GuestDTO;
import com.braggbnbsevenseventyseven.dto.GuestSearchDTO;
import com.braggbnbsevenseventyseven.dto.GuestPageDTO;
import com.braggbnbsevenseventyseven.dto.GuestConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.GuestService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class GuestServiceImpl extends GenericServiceImpl<Guest, Integer> implements GuestService {

    private final static Logger logger = LoggerFactory.getLogger(GuestServiceImpl.class);

	@Autowired
	GuestDAO guestDao;

	


	@Override
	public GenericDAO<Guest, Integer> getDAO() {
		return (GenericDAO<Guest, Integer>) guestDao;
	}
	
	public List<Guest> findAll () {
		List<Guest> guests = guestDao.findAll();
		
		return guests;	
		
	}

	public ResultDTO addGuest(GuestDTO guestDTO, RequestDTO requestDTO) {

		Guest guest = new Guest();

		guest.setGuestId(guestDTO.getGuestId());


		guest.setTotalBookings(guestDTO.getTotalBookings());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		guest = guestDao.save(guest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Guest> getAllGuests(Pageable pageable) {
		return guestDao.findAll(pageable);
	}

	public Page<Guest> getAllGuests(Specification<Guest> spec, Pageable pageable) {
		return guestDao.findAll(spec, pageable);
	}

	public ResponseEntity<GuestPageDTO> getGuests(GuestSearchDTO guestSearchDTO) {
	
			Integer guestId = guestSearchDTO.getGuestId(); 
  			String sortBy = guestSearchDTO.getSortBy();
			String sortOrder = guestSearchDTO.getSortOrder();
			String searchQuery = guestSearchDTO.getSearchQuery();
			Integer page = guestSearchDTO.getPage();
			Integer size = guestSearchDTO.getSize();

	        Specification<Guest> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, guestId, "guestId"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<Guest> guests = this.getAllGuests(spec, pageable);
		
		//System.out.println(String.valueOf(guests.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(guests.getTotalPages()));
		
		List<Guest> guestsList = guests.getContent();
		
		GuestConvertCriteriaDTO convertCriteria = new GuestConvertCriteriaDTO();
		List<GuestDTO> guestDTOs = this.convertGuestsToGuestDTOs(guestsList,convertCriteria);
		
		GuestPageDTO guestPageDTO = new GuestPageDTO();
		guestPageDTO.setGuests(guestDTOs);
		guestPageDTO.setTotalElements(guests.getTotalElements());
		return ResponseEntity.ok(guestPageDTO);
	}

	public List<GuestDTO> convertGuestsToGuestDTOs(List<Guest> guests, GuestConvertCriteriaDTO convertCriteria) {
		
		List<GuestDTO> guestDTOs = new ArrayList<GuestDTO>();
		
		for (Guest guest : guests) {
			guestDTOs.add(convertGuestToGuestDTO(guest,convertCriteria));
		}
		
		return guestDTOs;

	}
	
	public GuestDTO convertGuestToGuestDTO(Guest guest, GuestConvertCriteriaDTO convertCriteria) {
		
		GuestDTO guestDTO = new GuestDTO();
		
		guestDTO.setGuestId(guest.getGuestId());

	
		guestDTO.setTotalBookings(guest.getTotalBookings());

	

		
		return guestDTO;
	}

	public ResultDTO updateGuest(GuestDTO guestDTO, RequestDTO requestDTO) {
		
		Guest guest = guestDao.getById(guestDTO.getGuestId());

		guest.setGuestId(ControllerUtils.setValue(guest.getGuestId(), guestDTO.getGuestId()));

		guest.setTotalBookings(ControllerUtils.setValue(guest.getTotalBookings(), guestDTO.getTotalBookings()));



        guest = guestDao.save(guest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GuestDTO getGuestDTOById(Integer guestId) {
	
		Guest guest = guestDao.getById(guestId);
			
		
		GuestConvertCriteriaDTO convertCriteria = new GuestConvertCriteriaDTO();
		return(this.convertGuestToGuestDTO(guest,convertCriteria));
	}







}
