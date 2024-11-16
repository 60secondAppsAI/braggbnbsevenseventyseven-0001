package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.HostRating;





public interface HostRatingDAO extends GenericDAO<HostRating, Integer> {
  
	List<HostRating> findAll();
	






}


