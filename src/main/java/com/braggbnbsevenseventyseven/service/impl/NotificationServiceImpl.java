package com.braggbnbsevenseventyseven.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.braggbnbsevenseventyseven.dao.GenericDAO;
import com.braggbnbsevenseventyseven.service.GenericService;
import com.braggbnbsevenseventyseven.service.impl.GenericServiceImpl;
import com.braggbnbsevenseventyseven.dao.NotificationDAO;
import com.braggbnbsevenseventyseven.domain.Notification;
import com.braggbnbsevenseventyseven.dto.NotificationDTO;
import com.braggbnbsevenseventyseven.dto.NotificationSearchDTO;
import com.braggbnbsevenseventyseven.dto.NotificationPageDTO;
import com.braggbnbsevenseventyseven.dto.NotificationConvertCriteriaDTO;
import com.braggbnbsevenseventyseven.dto.common.RequestDTO;
import com.braggbnbsevenseventyseven.dto.common.ResultDTO;
import com.braggbnbsevenseventyseven.service.NotificationService;
import com.braggbnbsevenseventyseven.util.ControllerUtils;





@Service
public class NotificationServiceImpl extends GenericServiceImpl<Notification, Integer> implements NotificationService {

    private final static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	NotificationDAO notificationDao;

	


	@Override
	public GenericDAO<Notification, Integer> getDAO() {
		return (GenericDAO<Notification, Integer>) notificationDao;
	}
	
	public List<Notification> findAll () {
		List<Notification> notifications = notificationDao.findAll();
		
		return notifications;	
		
	}

	public ResultDTO addNotification(NotificationDTO notificationDTO, RequestDTO requestDTO) {

		Notification notification = new Notification();

		notification.setNotificationId(notificationDTO.getNotificationId());


		notification.setMessage(notificationDTO.getMessage());


		notification.setIsRead(notificationDTO.getIsRead());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		notification = notificationDao.save(notification);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Notification> getAllNotifications(Pageable pageable) {
		return notificationDao.findAll(pageable);
	}

	public Page<Notification> getAllNotifications(Specification<Notification> spec, Pageable pageable) {
		return notificationDao.findAll(spec, pageable);
	}

	public ResponseEntity<NotificationPageDTO> getNotifications(NotificationSearchDTO notificationSearchDTO) {
	
			Integer notificationId = notificationSearchDTO.getNotificationId(); 
 			String message = notificationSearchDTO.getMessage(); 
 			String isRead = notificationSearchDTO.getIsRead(); 
 			String sortBy = notificationSearchDTO.getSortBy();
			String sortOrder = notificationSearchDTO.getSortOrder();
			String searchQuery = notificationSearchDTO.getSearchQuery();
			Integer page = notificationSearchDTO.getPage();
			Integer size = notificationSearchDTO.getSize();

	        Specification<Notification> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, notificationId, "notificationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, message, "message"); 
			
			spec = ControllerUtils.andIfNecessary(spec, isRead, "isRead"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("message")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("isRead")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Notification> notifications = this.getAllNotifications(spec, pageable);
		
		//System.out.println(String.valueOf(notifications.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(notifications.getTotalPages()));
		
		List<Notification> notificationsList = notifications.getContent();
		
		NotificationConvertCriteriaDTO convertCriteria = new NotificationConvertCriteriaDTO();
		List<NotificationDTO> notificationDTOs = this.convertNotificationsToNotificationDTOs(notificationsList,convertCriteria);
		
		NotificationPageDTO notificationPageDTO = new NotificationPageDTO();
		notificationPageDTO.setNotifications(notificationDTOs);
		notificationPageDTO.setTotalElements(notifications.getTotalElements());
		return ResponseEntity.ok(notificationPageDTO);
	}

	public List<NotificationDTO> convertNotificationsToNotificationDTOs(List<Notification> notifications, NotificationConvertCriteriaDTO convertCriteria) {
		
		List<NotificationDTO> notificationDTOs = new ArrayList<NotificationDTO>();
		
		for (Notification notification : notifications) {
			notificationDTOs.add(convertNotificationToNotificationDTO(notification,convertCriteria));
		}
		
		return notificationDTOs;

	}
	
	public NotificationDTO convertNotificationToNotificationDTO(Notification notification, NotificationConvertCriteriaDTO convertCriteria) {
		
		NotificationDTO notificationDTO = new NotificationDTO();
		
		notificationDTO.setNotificationId(notification.getNotificationId());

	
		notificationDTO.setMessage(notification.getMessage());

	
		notificationDTO.setIsRead(notification.getIsRead());

	

		
		return notificationDTO;
	}

	public ResultDTO updateNotification(NotificationDTO notificationDTO, RequestDTO requestDTO) {
		
		Notification notification = notificationDao.getById(notificationDTO.getNotificationId());

		notification.setNotificationId(ControllerUtils.setValue(notification.getNotificationId(), notificationDTO.getNotificationId()));

		notification.setMessage(ControllerUtils.setValue(notification.getMessage(), notificationDTO.getMessage()));

		notification.setIsRead(ControllerUtils.setValue(notification.getIsRead(), notificationDTO.getIsRead()));



        notification = notificationDao.save(notification);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public NotificationDTO getNotificationDTOById(Integer notificationId) {
	
		Notification notification = notificationDao.getById(notificationId);
			
		
		NotificationConvertCriteriaDTO convertCriteria = new NotificationConvertCriteriaDTO();
		return(this.convertNotificationToNotificationDTO(notification,convertCriteria));
	}







}
