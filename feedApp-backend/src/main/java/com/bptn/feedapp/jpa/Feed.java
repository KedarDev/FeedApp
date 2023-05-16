package com.bptn.feedapp.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // Makes class a persistent entity in the DB
@Table(name = "\"Feed\"") // name of table in database
public class Feed implements Serializable { // maker interface enables the ability

	private static final long serialVersionUID = 1L; // unique identifier

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generated, type Identity
	@Column(name = "\"feedId\"") // Mapping class feedId to table feedId
	private Integer feedId;

	private String picture;

	private String content;

	@Column(name = "\"createdOn\"")
	private Timestamp createdOn;

	@ManyToOne // relationship between feed entity & user entity
	@JoinColumn(name = "\"userId\"") // FK
	private User user;

	@JsonInclude(Include.NON_NULL) // this annotation insures Feed metadata should not be included in the JSON output
	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // mappedby tells use this relation is mapped by feed entity & FeedMetaData entity
	private List<FeedMetaData> feedMetaData;
	// cascade=CascadeType.ALL specifies that any operation (insert, update, delete) performed on a Feed object should also be performed on its associated FeedMetaData objects default constructor
	public Feed() {

	}

	// GETTERS & SETTERS
	public Integer getFeedId() {
		return feedId;
	}

	public void setFeedId(Integer feedId) {
		this.feedId = feedId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<FeedMetaData> getFeedMetaData() {
		return feedMetaData;
	}

	public void setFeedMetaData(List<FeedMetaData> feedMetaData) {
		this.feedMetaData = feedMetaData;
	}

	// toString method
	@Override
	public String toString() {
		return "Feed [feedId=" + feedId + ", picture=" + picture + ", content=" + content + ", createdOn=" + createdOn
				+ "]";
	}

}
