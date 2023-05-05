package com.bptn.feedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FeedApp1Application {

	public static void main(String[] args) {
		SpringApplication.run(FeedApp1Application.class, args);
	}

}
