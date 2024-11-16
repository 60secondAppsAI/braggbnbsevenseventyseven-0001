package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.Guest;





public interface GuestDAO extends GenericDAO<Guest, Integer> {
  
	List<Guest> findAll();
	






}


