package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.Property;





public interface PropertyDAO extends GenericDAO<Property, Integer> {
  
	List<Property> findAll();
	






}


