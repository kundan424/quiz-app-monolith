package com.example.QuizApp.repository;

import com.example.QuizApp.entity.Quiz;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz,Integer> {
}
