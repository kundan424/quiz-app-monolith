package com.example.QuizApp.service;

import com.example.QuizApp.entity.QuestionDetailsDto;
import com.example.QuizApp.entity.Question;
import com.example.QuizApp.entity.Quiz;
import com.example.QuizApp.entity.Response;
import com.example.QuizApp.repository.QuestionRepository;
import com.example.QuizApp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository; // repository for quiz

    @Autowired
    private QuestionRepository questionRepository; // repository for question

    /**
     * Generates a new quiz with random questions from a specified category.
     *
     * @param category The category from which to select questions.
     * @param numQ     The desired number of questions in the quiz.
     * @return A List of Question objects for the generated quiz.
     * Returns an empty list if not enough questions are available or if the category is invalid.
     */

    public List<Question> generateRandomQuizByCategory(String category, int numQ) {
        // 1. Fetch all questions for the given category
        List<Question> questionsByCategory = questionRepository.findByCategory(category);

        // 2. Handle cases where not enough questions are found
        if (questionsByCategory.isEmpty()) {
            System.out.println("No questions found for category: " + category);
            return new ArrayList<>(); // return empty list
        }

        if (questionsByCategory.size() < numQ) {
            System.out.println("Not enough questions in category '" + category +
                    "'. Requested: " + numQ + ", Found: " + questionsByCategory.size());
            return new ArrayList<>(questionsByCategory); // return all available questions for the category
        }
        // 3. Shuffle the list of questions
        Collections.shuffle(questionsByCategory);

        // select the desired number of questions
        List<Question> randomQuestions = questionsByCategory.stream()
                .limit(numQ)
                .collect(Collectors.toList());
        return randomQuestions;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // 1. Generate the random question objects
        List<Question> generatedQuestions = generateRandomQuizByCategory(category, numQ);

        if (generatedQuestions.isEmpty()) {
            // No questions could be generated, handle appropriately (e.g., throw an exception)
            return null;
        }

        // 2. extract the ids of these questions
        List<Integer> questionIds = generatedQuestions.stream()
                .map(Question::getId)
                .collect(Collectors.toList());

        // 3. Create a new Quiz entity
        Quiz quiz = new Quiz();
        quiz.setId(generateUniqueQuizId()); // IMPORTANT: Implement proper ID generation
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds); // Store the IDs of the selected questions

        // save the Quiz entity to MongoDB
        quizRepository.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    private Integer generateUniqueQuizId() {
        // Placeholder: In a production app, use a proper ID generation strategy
        // (e.g., a counter, UUIDs, or let Spring Data MongoDB handle ObjectId if you change Quiz ID type)
        return (int) (System.currentTimeMillis() % 1_000_000_000);
    }

    public ResponseEntity<List<QuestionDetailsDto>> getQuizQuestions(Integer id) {
        // 1. Fetch the Quiz document to get the question IDs
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isEmpty()) {
            // Quiz not found, return an empty list of questions
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOptional.get();
        List<Integer> questionIds = quiz.getQuestionIds();

        // 2. If there are no question IDs (e.g., an empty quiz), return empty list
        if (questionIds == null || questionIds.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        // fetch the actual question entities using their ids
        List<Question> questions = questionRepository.findAllById(questionIds);

        // 4. Map the full Question entities to your desired QuestionDetailsDto
        List<QuestionDetailsDto> questionsForUser = new ArrayList<>();
        for (Question q : questions) {
            QuestionDetailsDto qw = new QuestionDetailsDto(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public Integer calculateResult(Integer id, List<Response> responses) {
        // 1.Fetch the Quiz to get the IDs of the original questions
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isEmpty()) {
            System.out.println("Quiz with ID " + id + " not found for scoring.");
            return -1; // indicate the quiz not find
        }
        List<Integer> questionIdsInQuiz = quizOptional.get().getQuestionIds();
        if (questionIdsInQuiz == null || questionIdsInQuiz.isEmpty()) {
            System.out.println("Quiz with ID " + id + " has no questions to score against.");
            return 0; // No questions in the quiz, so 0 correct
        }

        // 2. fetch the actual question entities using their Ids
        // Map them by ID for efficient lookup
        Map<Integer, Question> originalQuestionsMap = questionRepository.findAllById(questionIdsInQuiz)
                .stream()
                .collect(Collectors.toMap(Question::getId, question -> question));
        int correctAnswers = 0;

        // compare the user's answer with correct answers
        for (Response response : responses) {
            Question originalQuestion = originalQuestionsMap.get(response.getId());

            if (originalQuestion == null) {
                // Ensure case-insensitive comparison or trim whitespace if needed
                if (originalQuestion.getRightAnswer().equalsIgnoreCase(response.getResponse().trim())) {
                    correctAnswers++;
                }
            } else {
                // Log a warning if a submitted question ID doesn't exist in the quiz
                System.out.println("Warning: Submitted answer for unknown question ID " + response.getId() + "in Quiz" + id);
            }
        }
        return correctAnswers;
    }
}