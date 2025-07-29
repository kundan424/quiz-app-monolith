package com.example.QuizApp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    private Integer id;  // Assuming Quiz IDs are also Integers
    private String title;
    private List<Integer> questionIds; // this stores the IDs of the question

}
