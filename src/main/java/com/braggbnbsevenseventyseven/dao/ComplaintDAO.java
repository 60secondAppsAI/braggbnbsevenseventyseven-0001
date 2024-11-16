package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.Complaint;





public interface ComplaintDAO extends GenericDAO<Complaint, Integer> {
  
	List<Complaint> findAll();
	






}


