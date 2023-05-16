package com.bptn.feedapp.repository;

import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository is a Spring Data interface that extends the CrudRepository interface and provides additional methods for working with JPA entities.
import org.springframework.data.repository.PagingAndSortingRepository; // PagingAndSortingRepository is a Spring Data interface that extends the CrudRepository interface and provides additional methods for working with paginated and sorted data.

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bptn.feedapp.jpa.User;
import com.bptn.feedapp.jpa.Feed;

public interface FeedRepository extends JpaRepository<Feed, Integer>, PagingAndSortingRepository<Feed, Integer> {
	// methods
	Page<Feed> findByUser(User user, Pageable pageable); // returns a page of feed object 
	Page<Feed> findByUserNot(User user, Pageable pageable); // returns a page object that is not associated with a given user
}
