# KAHANI – Stories Beyond Words

## Project Overview

**KAHANI – Stories Beyond Words** is an accessible storytelling and learning application developed in Java for children.

The application provides interactive stories through scene-by-scene reading, simple navigation, quizzes, learning progress, and accessibility features. It is designed with a particular focus on children with visual difficulties and other accessibility needs.

### Key Accessibility Features

* Large text support
* High-contrast interface
* Speech support
* Sign-language cues
* Visual scene descriptions
* Unicode Braille software support
* Simple and clear navigation
* Large, easy-to-use buttons

---

## Main Features

### 1. Story Library

Users can browse available stories and select a story to read.

### 2. Story Reader

Stories are presented scene by scene with:

* Story title
* Scene text
* Visual description
* Sign-language cue
* Previous/Next navigation
* Read-aloud support
* Quiz access

### 3. Accessibility Centre

The application provides controls for:

* Large text
* High contrast
* Speech support
* Sign cues
* Braille support

### 4. Speech Support

KAHANI uses Windows Speech Synthesis to read the current scene aloud.

The speech functionality is implemented through:

* `SpeechSupport.java`
* `WindowsSpeechSupport.java`
* `AccessibilityService.java`

### 5. Sign-Language Support

Scenes contain sign-language cues that provide an additional way of representing story content.

### 6. Braille Software Support

The project includes a Unicode Braille software layer that converts supported text into Unicode Braille characters.

**Note:** This implementation demonstrates software-based Braille conversion. It does not require or claim integration with physical refreshable Braille hardware.

### 7. Quiz Module

Users can take quizzes related to stories and receive their results.

### 8. Learning Progress

The application records learning-related information such as story interaction and quiz performance.

Learning records are stored using CSV-based data storage.

### 9. Story Recommendation

The project includes a recommendation component that uses learning data and user preferences to suggest stories.

### 10. Learning Data Analysis

The project includes data-mining functionality for analysing learning records.

### 11. Optimization

An optimization component using gradient descent is included as part of the project implementation.

These technical components operate in the backend and are not exposed as technical concepts in the child-facing interface.

---

## Technologies Used

* **Programming Language:** Java
* **GUI:** Java Swing
* **Data Storage:** CSV
* **Speech:** Windows Speech Synthesis
* **Accessibility:** Java-based accessibility services and Unicode Braille
* **Data Analysis:** Java-based data mining
* **Recommendation:** Story recommendation component
* **Optimization:** Gradient Descent
* **Development Environment:** Visual Studio Code / Java Development Kit

---

## Project Structure

```text
Kahani/
│
├── src/
│   └── kahani/
│       ├── app/
│       │   ├── KahaniApp.java
│       │   └── KahaniBackendDemo.java
│       │
│       ├── model/
│       │   ├── Scene.java
│       │   ├── Story.java
│       │   ├── AnimalStory.java
│       │   ├── AdventureStory.java
│       │   └── StoryRepository.java
│       │
│       ├── accessibility/
│       │   ├── AccessibilityService.java
│       │   ├── SignLanguageSupport.java
│       │   ├── SpeechSupport.java
│       │   ├── WindowsSpeechSupport.java
│       │   ├── BrailleSupport.java
│       │   └── UnicodeBrailleSupport.java
│       │
│       ├── quiz/
│       │   ├── Quiz.java
│       │   └── QuizQuestion.java
│       │
│       ├── data/
│       │   ├── LearningRecord.java
│       │   └── LearningDataStore.java
│       │
│       ├── ml/
│       │   ├── FeatureVector.java
│       │   ├── DataMiner.java
│       │   └── StoryRecommender.java
│       │
│       ├── optimization/
│       │   ├── GradientDescent.java
│       │   └── OptimizationResult.java
│       │
│       └── exception/
│           ├── KahaniException.java
│           └── DataValidationException.java
│
├── data/
│   └── sample_learning_data.csv
│
└── README.md
```

---

## How to Run

### Step 1: Open the Project

Open the project folder in **Visual Studio Code** or another Java IDE.

### Step 2: Open the Terminal

Navigate to the project root directory.

### Step 3: Compile the Project

For PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

### Step 4: Run the Application

```powershell
java -cp out kahani.app.KahaniApp
```

The KAHANI graphical application should open.

---

## Backend Demonstration

A separate backend demonstration class is also included.

To run it:

```powershell
java -cp out kahani.app.KahaniBackendDemo
```

This can be used to demonstrate the underlying project components separately from the main GUI.

---

## Accessibility Notes

### Speech

Speech support uses the Windows Speech Synthesis system available on Windows.

The system must have an available audio output device such as speakers or headphones.

### Braille

The project provides **Unicode Braille software output** for demonstration and accessibility processing.

It is not a physical Braille display implementation.

### Sign Support

Sign cues are associated with individual story scenes and can be accessed while reading a story.

---

## Learning Data

The application uses CSV-based learning records to store relevant learning information.

Example data is provided in:

```text
data/sample_learning_data.csv
```

The learning data can be processed by the project's data analysis and recommendation components.

---

## Project Objectives

The main objectives of KAHANI are:

1. To create an interactive storytelling environment for children.
2. To provide accessible learning features.
3. To support children with visual difficulties and other accessibility needs.
4. To provide multiple ways of experiencing story content.
5. To include quizzes and learning progress.
6. To analyse learning interaction data.
7. To provide story recommendations based on available learning information.
8. To demonstrate the integration of Java GUI, accessibility, data processing, recommendation, and optimization components in a single application.

---

## Intended Users

KAHANI is primarily designed for:

* Children
* Children with visual difficulties
* Children requiring additional accessibility support
* Parents and educators interested in accessible digital learning

---

## Project Highlights

**KAHANI combines:**

```text
Interactive Storytelling
        +
Accessibility
        +
Quizzes
        +
Learning Progress
        +
Learning Data Analysis
        +
Story Recommendation
        +
Optimization
```

The application keeps the user interface simple and child-friendly while the underlying Java implementation provides the supporting technical functionality.

---

## Future Enhancements

Possible future improvements include:

* Integration with physical Braille displays
* Additional stories and learning activities
* More sign-language resources
* More language options
* Cloud-based learning profiles
* Expanded accessibility and keyboard support
* Additional recommendation techniques
* Mobile or web-based versions

---

## Complete Source Code

The complete Java source code of **KAHANI – Stories Beyond Words** is included in this repository.

The repository can be used to study the project structure, implementation, accessibility components, learning modules, and application interface.

**GitHub Repository:**
`[Paste your GitHub repository link here]`

---

## Project Documentation

The project report contains detailed information about:

* Introduction
* Problem statement
* Objectives
* System design
* Functional modules
* Accessibility implementation
* Data processing
* Recommendation system
* Optimization
* Testing
* Results
* Limitations
* Future enhancements

Screenshots of the application are included in the project report to demonstrate the implemented features.

---

## Conclusion

**KAHANI – Stories Beyond Words** demonstrates how a Java-based storytelling application can combine interactive learning with accessibility features. The project brings together storytelling, quizzes, learning records, accessibility services, recommendation, data analysis, and optimization into one application designed for children.

**Developed as an academic project in Computer Science and Artificial Intelligence.**
