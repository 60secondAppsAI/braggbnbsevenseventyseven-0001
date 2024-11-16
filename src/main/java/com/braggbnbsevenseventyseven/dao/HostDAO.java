package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.Host;





public interface HostDAO extends GenericDAO<Host, Integer> {
  
	List<Host> findAll();
	






}


