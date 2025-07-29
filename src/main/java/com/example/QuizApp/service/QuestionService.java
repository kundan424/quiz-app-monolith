package com.example.QuizApp.service;

import com.example.QuizApp.entity.Question;
import com.example.QuizApp.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class); // Initialize logger

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occur while get all question" ,e);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occur while getting question of category: {}", category);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occur while adding question",e);
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteQuestion(Integer id) { // Return type changed to Void as no body on 204
        try {
            // Check if the question exists first
            Optional<Question> questionOptional = questionRepository.findById(id);

            if (questionOptional.isPresent()) {
                questionRepository.deleteById(id);
                logger.info("Question with ID {} deleted successfully.", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
            } else {
                logger.warn("Attempted to delete non-existent question with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
            }
        } catch (Exception e) {
            // Log the specific error
            logger.error("An error occurred while deleting question with ID: {}", id, e);
            // Return 500 Internal Server Error for unexpected issues
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
