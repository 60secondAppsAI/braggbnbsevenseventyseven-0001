package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.Image;





public interface ImageDAO extends GenericDAO<Image, Integer> {
  
	List<Image> findAll();
	






}


