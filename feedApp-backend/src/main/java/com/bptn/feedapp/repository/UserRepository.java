package com.bptn.feedapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bptn.feedapp.jpa.User;

// The User can now use methods from thr Jpa interface
public interface UserRepository extends JpaRepository<User, Integer> {
	
    // Methods
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailId(String email);

}

