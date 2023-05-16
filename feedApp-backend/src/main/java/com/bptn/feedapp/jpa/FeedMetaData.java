package com.bptn.feedapp.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // @Entity annotation marks this class as a persistent entity in the database
@Table(name="\"FeedMetaData\"") // @Table annotation is used to specify the name of the database table associated with this entity.
public class FeedMetaData implements Serializable { // Serializable is a marker interface in Java that tells the compiler that the class is serializable.
	
	//his is a unique identifier for the class version used for serialization and deserialization
	private static final long serialVersionUID = 1L;
	
	@Id //PK
	@GeneratedValue(strategy=GenerationType.IDENTITY) // PK generated automatically, type IDENTITY 
	@Column(name="\"feedMetaDataId\"") // mapping of the annotated field to a column in the database table
	private Integer feedMetaDataId;
	
	private String comment;
	
	@Column(name="\"createdOn\"")
	private Timestamp createdOn;
	
	@Column(name="\"isLike\"")
	private Boolean isLike;
	
	@ManyToOne // relationship type
	@JsonIgnore // @JsonIgnore annotation on the feed field specifies that this field should be excluded from the JSON representation of the entity
	@JoinColumn(name="\"feedId\"")
	private Feed feed;
	
	@ManyToOne
	@JoinColumn(name="\"actionUserId\"") // FK
	private User user;
	
	// Default constructor
	public FeedMetaData() {
		
	}

	// Getter & Setters
	public Integer getFeedMetaDataId() {
		return feedMetaDataId;
	}

	public void setFeedMetaDataId(Integer feedMetaDataId) {
		this.feedMetaDataId = feedMetaDataId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getIsLike() {
		return isLike;
	}

	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
}