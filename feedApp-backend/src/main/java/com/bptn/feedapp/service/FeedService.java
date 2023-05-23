package com.bptn.feedapp.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bptn.feedapp.domain.PageResponse;
import com.bptn.feedapp.exception.domain.FeedNotFoundException;
import com.bptn.feedapp.exception.domain.FeedNotUserException;
import com.bptn.feedapp.exception.domain.LikeExistException;
import com.bptn.feedapp.exception.domain.UserNotFoundException;
import com.bptn.feedapp.jpa.Feed;
import com.bptn.feedapp.jpa.FeedMetaData;
import com.bptn.feedapp.jpa.User;
import com.bptn.feedapp.repository.FeedMetaDataRepository;
import com.bptn.feedapp.repository.FeedRepository;
import com.bptn.feedapp.repository.UserRepository;

@Service // Spring service class
public class FeedService {
	 
	// log activity 
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// inject instances
	@Autowired
	UserRepository userRepository;

	@Autowired
	FeedRepository feedRepository;

	@Autowired
	FeedMetaDataRepository feedMetaDataRepository;

	// create feed
	public Feed createFeed(Feed feed) {

		// Retrieve the username of the currently logged-in user
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = this.userRepository.findByUsername(username)
					.orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));

	    // Set the user attribute of the Feed object to the retrieved user
		feed.setUser(user);
		feed.setCreatedOn(Timestamp.from(Instant.now()));

		// save the feed object
		return this.feedRepository.save(feed);
	}
	
	public Feed getFeedById(int feedId) {

		return this.feedRepository.findById(feedId)
		.orElseThrow(() -> new FeedNotFoundException(String.format("Feed doesn't exist, %d", feedId)));
	}
	
	public PageResponse<Feed> getUserFeeds(int pageNum, int pageSize) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
			
		User user = this.userRepository.findByUsername(username)
		             .orElseThrow(()-> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
					
		Page<Feed> paged = this.feedRepository.findByUser(user, PageRequest.of(pageNum, pageSize, Sort.by("feedId").descending()));
			
		return new PageResponse<Feed>(paged);
	}
	

	public PageResponse<Feed> getOtherUsersFeeds(int pageNum, int pageSize) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
		User user = this.userRepository.findByUsername(username)
		             .orElseThrow(()-> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
					
		Page<Feed> paged = this.feedRepository.findByUserNot(user, PageRequest.of(pageNum, pageSize, Sort.by("feedId").descending()));
			
		return new PageResponse<Feed>(paged);
	}
	
	
	public FeedMetaData createFeedMetaData(int feedId, FeedMetaData meta) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
		User user = this.userRepository.findByUsername(username)
					             .orElseThrow(()-> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
			
		Feed feed = this.feedRepository.findById(feedId)
					             .orElseThrow(()-> new FeedNotFoundException(String.format("Feed doesn't exist, %d", feedId)));

		FeedMetaData newMeta = new FeedMetaData();
			
		newMeta.setIsLike(false);
		newMeta.setUser(user);
		newMeta.setFeed(feed);
		newMeta.setCreatedOn(Timestamp.from(Instant.now()));
			
	    if (Optional.ofNullable(meta.getIsLike()).isPresent()) {
	        	
	        newMeta.setIsLike( meta.getIsLike() );
	            
	        if (meta.getIsLike()) {
	        		
	            feed.getFeedMetaData().stream()
	                      .filter(m -> m.getUser().getUsername().equals(username))
	      	              .filter(m -> m.getIsLike().equals(true)).findAny()
	      	              .ifPresent(m -> {throw new LikeExistException(String.format("Feed already liked, feedId: %d, username: %s", feedId, username));});
	            	
	            newMeta.setComment("");
	        }
	    } 
	        
	    if (!newMeta.getIsLike()) {
	        newMeta.setComment(meta.getComment());
	    }
	        
		return this.feedMetaDataRepository.save(newMeta);
	}
	
	public void deleteFeed(int feedId) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Feed feed = this.feedRepository.findById(feedId)			
		             .orElseThrow(()-> new FeedNotFoundException(String.format("Feed doesn't exist, %d", feedId)));

		Optional.of(feed).filter(f -> f.getUser().getUsername().equals(username))
			         .orElseThrow(()-> new FeedNotUserException(String.format("Feed doesn't belong to current User, feedId: %d, username: %s", feedId, username)));
			
		this.feedRepository.delete(feed);
	}
	
}
