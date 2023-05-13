package com.bptn.feedapp.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Tells us this class is a repo
@Repository
public class UserDao {

	// instence variable 
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired // Tells Spring to inject a JdbcTemplate instence into a jdbcTemplate variable 
	JdbcTemplate jdbcTemplate;

	// retrive all users in DB
	public List<UserBean> listUsers() {

		String sql = "SELECT * FROM \"User\"";

		return this.jdbcTemplate.query(sql, new UserMapper());

	}

	// Find Users by username
	public UserBean findByUsername(String username) {

		String sql = "SELECT * FROM \"User\" WHERE username = ?";

		List<UserBean> users = this.jdbcTemplate.query(sql, new UserMapper(), username);

		/*
		 * Returns null if users is empty otherwise, returns the first element in the
		 * list
		 */
		return users.isEmpty() ? null : users.get(0);

	}

	// Inserts Users into DB
	public void createUser(UserBean user) {

		String sql = "INSERT INTO \"User\" (\"firstName\", \"lastName\", username, phone, \"emailId\", password, \"emailVerified\", \"createdOn\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		logger.debug("Insert Query: {}", sql);

		/* Executes the Insert Statement */
		this.jdbcTemplate.update(sql, new Object[] { user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getPhone(), user.getEmailId(), user.getPassword(), user.getEmailVerified(), user.getCreatedOn() });

	}

}
