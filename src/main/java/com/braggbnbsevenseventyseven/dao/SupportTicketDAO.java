package com.braggbnbsevenseventyseven.dao;

import java.util.List;

import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.domain.SupportTicket;





public interface SupportTicketDAO extends GenericDAO<SupportTicket, Integer> {
  
	List<SupportTicket> findAll();
	






}


