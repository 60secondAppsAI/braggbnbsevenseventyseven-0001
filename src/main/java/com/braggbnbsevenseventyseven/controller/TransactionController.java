package com.braggbnbsevenseventyseven.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.braggbnbsevenseventyseven.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.braggbnbsevenseventyseven.domain.Transaction;
import com.braggbnbsevenseventyseven.dto.TransactionDTO;
import com.braggbnbsevenseventyseven.dto.TransactionSearchDTO;
import com.braggbnbsevenseventyseven.dto.TransactionPageDTO;
import com.braggbnbsevenseventyseven.service.TransactionService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/transaction")
@RestController
public class TransactionController {

	private final static Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Transaction> getAll() {

		List<Transaction> transactions = transactionService.findAll();
		
		return transactions;	
	}

	@GetMapping(value = "/{transactionId}")
	@ResponseBody
	public TransactionDTO getTransaction(@PathVariable Integer transactionId) {
		
		return (transactionService.getTransactionDTOById(transactionId));
	}

 	@RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
	public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = transactionService.addTransaction(transactionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/transactions")
	public ResponseEntity<TransactionPageDTO> getTransactions(TransactionSearchDTO transactionSearchDTO) {
 
		return transactionService.getTransactions(transactionSearchDTO);
	}	

	@RequestMapping(value = "/updateTransaction", method = RequestMethod.POST)
	public ResponseEntity<?> updateTransaction(@RequestBody TransactionDTO transactionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = transactionService.updateTransaction(transactionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}