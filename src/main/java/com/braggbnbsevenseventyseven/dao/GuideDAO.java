package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.Guide;





public interface GuideDAO extends GenericDAO<Guide, Integer> {
  
	List<Guide> findAll();
	






}


