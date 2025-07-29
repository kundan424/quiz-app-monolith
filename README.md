
<h1 align="center">📘 QuizApp (Monolithic Version)</h1>

<p align="center">
  A dynamic and interactive quiz platform built with <strong>Spring Boot</strong> and <strong>MongoDB</strong>. Perfect for educators, trainers, and quiz enthusiasts!
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-blue.svg" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.6-brightgreen.svg" />
  <img src="https://img.shields.io/badge/MongoDB-NoSQL-green.svg" />
  <img src="https://img.shields.io/badge/License-Unlicensed-lightgrey.svg" />
</p>


## 🚀 Features

✅ Create quizzes with customizable categories  
✅ Retrieve quiz questions and submit answers for instant scoring  
✅ Manage a flexible question bank  
✅ RESTful API access for integration  
✅ Built with modular, maintainable code using Spring Boot

---

## 🛠️ Tech Stack

| Tool        | Description                            |
|-------------|----------------------------------------|
| ☕ Java 21   | Language                                |
| 🌱 Spring Boot 3.4.6 | Framework for REST APIs            |
| 🍃 MongoDB   | NoSQL database for fast persistence     |
| 🔧 Maven     | Dependency and build management         |
| ✨ Lombok     | Reduces boilerplate for cleaner code   |

---

## ⚙️ Getting Started

### 🔍 Prerequisites

- Java 21+
- Maven 3.8+
- MongoDB (running locally or remotely)

### 📦 Installation

```bash
git clone https://github.com/kundan424/quiz-app-monolith.git
cd quiz-app-monolith
````

### ⚒️ Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

➡️ Visit: `http://localhost:8080`

---

## 🔌 MongoDB Configuration

Update the connection in `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/quizapp
```

---

## 📡 API Overview

Test using Postman, Swagger (if added), or `curl`.

### 🎯 Quiz APIs

| Endpoint                | Method | Description         |
| ----------------------- | ------ | ------------------- |
| `/quiz/create`          | `POST` | Create a new quiz   |
| `/quiz/get/{quizId}`    | `GET`  | Get quiz questions  |
| `/quiz/submit/{quizId}` | `POST` | Submit quiz answers |

### 📘 Question APIs

| Endpoint                        | Method   | Description         |
| ------------------------------- | -------- | ------------------- |
| `/question/allQuestions`        | `GET`    | Fetch all questions |
| `/question/category/{category}` | `GET`    | Filter by category  |
| `/question/add`                 | `POST`   | Add a new question  |
| `/question/delete/{questionId}` | `DELETE` | Delete a question   |

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 🧠 Sample Payloads

### ➕ Add Question

```json
{
 "id": 1,
    "questionTitle": "What is the capital of India?",
    "option1": "Mumbai",
    "option2": "Delhi",
    "option3": "Kolkata",
    "option4": "Chennai",
    "rightAnswer": "Delhi",
    "difficultyLevel": "Easy",
    "category": "Geography"
}
```

### 📤 Submit Answers

```json
[
  { "questionId": "1", "response": "A" },
  { "questionId": "2", "": "B" }
]
```

### 🎯 Create a Quiz
Creates a new quiz by randomly selecting questions from the given category.

#### 🧩 Endpoint
```http
POST /quiz/create
````

#### 🔸 Query Parameters

| Parameter  | Type     | Required | Description                                |
| ---------- | -------- | -------- | ------------------------------------------ |
| `category` | `String` | ✅ Yes    | The category to select questions from      |
| `numQ`     | `int`    | ✅ Yes    | Number of questions to include in the quiz |
| `title`    | `String` | ✅ Yes    | Title of the quiz                          |

#### 🔸 Example Request

```bash
curl -X POST "http://localhost:8080/quiz/create?category=Science&numQ=5&title=Physics Quiz"
```

#### 🔸 What It Does

* Fetches `numQ` random questions from the `question` collection based on `category`
* Extracts their `questionIds`
* Creates a new `Quiz` object:

  ```json
  {
    "id": 101,
    "title": "Physics Quiz",
    "questionIds": [5, 8, 10, 12, 19]
  }
  ```
* Stores this quiz in the `quiz` collection

#### ✅ Success Response

```json
{
  "message": "Quiz created successfully",
  "quizId": 101
}
```

#### ⚠️ Notes

* Ensure enough questions exist for the given category.
* Handle the case where `numQ` exceeds available questions by returning a 400 error or limiting the selection.

```
```
## 👨‍💻 Contributing

1. Fork the repo
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push and create a Pull Request

---

## 🤝 Contact

Maintained by **Kundan**
Questions? Suggestions? Feel free to open an issue or reach out on GitHub.

---

## 📄 License

---

> *Happy Coding! 🎉*

```
