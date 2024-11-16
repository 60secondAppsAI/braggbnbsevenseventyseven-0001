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
import com.braggbnbsevenseventyseven.dao.WishlistDAO;
import com.braggbnbsevenseventyseven.domain.Wishlist;
import com.braggbnbsevenseventyseven.dto.WishlistDTO;
import com.braggbnbsevenseventyseven.dto.WishlistSearchDTO;
import com.braggbnbsevenseventyseven.dto.WishlistPageDTO;
import com.braggbnbsevenseventyseven.dto.WishlistConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.WishlistService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class WishlistServiceImpl extends GenericServiceImpl<Wishlist, Integer> implements WishlistService {

    private final static Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);

	@Autowired
	WishlistDAO wishlistDao;

	


	@Override
	public GenericDAO<Wishlist, Integer> getDAO() {
		return (GenericDAO<Wishlist, Integer>) wishlistDao;
	}
	
	public List<Wishlist> findAll () {
		List<Wishlist> wishlists = wishlistDao.findAll();
		
		return wishlists;	
		
	}

	public ResultDTO addWishlist(WishlistDTO wishlistDTO, RequestDTO requestDTO) {

		Wishlist wishlist = new Wishlist();

		wishlist.setWishlistId(wishlistDTO.getWishlistId());


		wishlist.setName(wishlistDTO.getName());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		wishlist = wishlistDao.save(wishlist);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Wishlist> getAllWishlists(Pageable pageable) {
		return wishlistDao.findAll(pageable);
	}

	public Page<Wishlist> getAllWishlists(Specification<Wishlist> spec, Pageable pageable) {
		return wishlistDao.findAll(spec, pageable);
	}

	public ResponseEntity<WishlistPageDTO> getWishlists(WishlistSearchDTO wishlistSearchDTO) {
	
			Integer wishlistId = wishlistSearchDTO.getWishlistId(); 
 			String name = wishlistSearchDTO.getName(); 
 			String sortBy = wishlistSearchDTO.getSortBy();
			String sortOrder = wishlistSearchDTO.getSortOrder();
			String searchQuery = wishlistSearchDTO.getSearchQuery();
			Integer page = wishlistSearchDTO.getPage();
			Integer size = wishlistSearchDTO.getSize();

	        Specification<Wishlist> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, wishlistId, "wishlistId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Wishlist> wishlists = this.getAllWishlists(spec, pageable);
		
		//System.out.println(String.valueOf(wishlists.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(wishlists.getTotalPages()));
		
		List<Wishlist> wishlistsList = wishlists.getContent();
		
		WishlistConvertCriteriaDTO convertCriteria = new WishlistConvertCriteriaDTO();
		List<WishlistDTO> wishlistDTOs = this.convertWishlistsToWishlistDTOs(wishlistsList,convertCriteria);
		
		WishlistPageDTO wishlistPageDTO = new WishlistPageDTO();
		wishlistPageDTO.setWishlists(wishlistDTOs);
		wishlistPageDTO.setTotalElements(wishlists.getTotalElements());
		return ResponseEntity.ok(wishlistPageDTO);
	}

	public List<WishlistDTO> convertWishlistsToWishlistDTOs(List<Wishlist> wishlists, WishlistConvertCriteriaDTO convertCriteria) {
		
		List<WishlistDTO> wishlistDTOs = new ArrayList<WishlistDTO>();
		
		for (Wishlist wishlist : wishlists) {
			wishlistDTOs.add(convertWishlistToWishlistDTO(wishlist,convertCriteria));
		}
		
		return wishlistDTOs;

	}
	
	public WishlistDTO convertWishlistToWishlistDTO(Wishlist wishlist, WishlistConvertCriteriaDTO convertCriteria) {
		
		WishlistDTO wishlistDTO = new WishlistDTO();
		
		wishlistDTO.setWishlistId(wishlist.getWishlistId());

	
		wishlistDTO.setName(wishlist.getName());

	

		
		return wishlistDTO;
	}

	public ResultDTO updateWishlist(WishlistDTO wishlistDTO, RequestDTO requestDTO) {
		
		Wishlist wishlist = wishlistDao.getById(wishlistDTO.getWishlistId());

		wishlist.setWishlistId(ControllerUtils.setValue(wishlist.getWishlistId(), wishlistDTO.getWishlistId()));

		wishlist.setName(ControllerUtils.setValue(wishlist.getName(), wishlistDTO.getName()));



        wishlist = wishlistDao.save(wishlist);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public WishlistDTO getWishlistDTOById(Integer wishlistId) {
	
		Wishlist wishlist = wishlistDao.getById(wishlistId);
			
		
		WishlistConvertCriteriaDTO convertCriteria = new WishlistConvertCriteriaDTO();
		return(this.convertWishlistToWishlistDTO(wishlist,convertCriteria));
	}







}
