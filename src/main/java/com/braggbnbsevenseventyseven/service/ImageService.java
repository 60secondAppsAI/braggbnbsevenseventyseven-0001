package com.braggbnbsevenseventyseven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbnbsevenseventyseven.domain.Image;
import com.braggbnbsevenseventyseven.dto.ImageDTO;
import com.braggbnbsevenseventyseven.dto.ImageSearchDTO;
import com.braggbnbsevenseventyseven.dto.ImagePageDTO;
import com.braggbnbsevenseventyseven.dto.ImageConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ImageService extends GenericService<Image, Integer> {

	List<Image> findAll();

	ResultDTO addImage(ImageDTO imageDTO, RequestDTO requestDTO);

	ResultDTO updateImage(ImageDTO imageDTO, RequestDTO requestDTO);

    Page<Image> getAllImages(Pageable pageable);

    Page<Image> getAllImages(Specification<Image> spec, Pageable pageable);

	ResponseEntity<ImagePageDTO> getImages(ImageSearchDTO imageSearchDTO);
	
	List<ImageDTO> convertImagesToImageDTOs(List<Image> images, ImageConvertCriteriaDTO convertCriteria);

	ImageDTO getImageDTOById(Integer imageId);







}





