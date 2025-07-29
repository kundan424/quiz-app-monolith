package com.example.QuizApp.controller;

import com.example.QuizApp.entity.QuestionDetailsDto;
import com.example.QuizApp.entity.Response;
import com.example.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam Integer numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionDetailsDto>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(
            @PathVariable Integer id,
            @RequestBody List<Response> responses
    ) {
        Integer correctCount = quizService.calculateResult(id, responses);

        if (correctCount == -1) {
            //Quiz not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // Return the count of correct answers with OK status
            return new ResponseEntity<>(correctCount, HttpStatus.OK);
        }
    }
}
