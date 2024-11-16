package com.braggbnbsevenseventyseven.service;

import com.braggbnbsevenseventyseven.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}