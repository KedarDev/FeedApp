package com.bptn.feedapp.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bptn.feedapp.provider.ResourceProvider;

@Component // Spring managed bean
public class JwtService {

	final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log activity 

	@Autowired // inject ResourceProvider instance 
	ResourceProvider provider;

	// Generate JWT token for given username with an expiration time
	public String generateJwtToken(String username, long expiration) {

		// return JWT token as a String
		return JWT.create().withIssuer(this.provider.getJwtIssuer()).withAudience(this.provider.getJwtAudience())
				.withIssuedAt(new Date()).withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.sign(HMAC512(this.provider.getJwtSecret()));
	}
	
	// takes JWT token string input and verifies its authenticity using secret key stored in ResourceProvider
	public DecodedJWT verifyJwtToken(String token) {

		return JWT.require(HMAC512(this.provider.getJwtSecret())).withIssuer(this.provider.getJwtIssuer()).build()
				.verify(token);
	}

	// Takes a single string para representing a JSON Wed Token
	public String getSubject(String token) {

		return JWT.require(HMAC512(this.provider.getJwtSecret())).withIssuer(this.provider.getJwtIssuer()).build()
				.verify(token).getSubject();
	}
}