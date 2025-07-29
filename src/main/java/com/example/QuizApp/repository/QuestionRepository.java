package com.example.QuizApp.repository;

import com.example.QuizApp.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question , Integer> { // Use String for MongoDB ID type
    List<Question> findByCategory(String category);

    List<Question> findRandomQuestionByCategory(String category, int numQ);

}
