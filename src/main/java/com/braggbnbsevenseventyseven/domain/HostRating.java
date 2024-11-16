package com.braggbnbsevenseventyseven.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="host_ratings")
@Getter @Setter @NoArgsConstructor
public class HostRating {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="host_rating_id")
	private Integer hostRatingId;
    
  	@Column(name="rating")
	private int rating;
    
  	@Column(name="feedback")
	private String feedback;
    
	




}