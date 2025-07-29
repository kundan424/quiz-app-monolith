package com.example.QuizApp;

import com.example.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizAppApplication {

	@Autowired
	private QuizService quizService;

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}

}
