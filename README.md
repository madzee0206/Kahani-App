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
Application Output:

<img width="1208" height="751" alt="{401AE7A1-BBB5-454B-9BE4-15A876C394B2}" src="https://github.com/user-attachments/assets/f90cbbfa-c983-41ed-b090-7694111054cd" />
<img width="1189" height="804" alt="{E2EA5048-72CA-4C46-81FD-5FA103EE1D92}" src="https://github.com/user-attachments/assets/97ac152d-290e-4ef9-bd56-8288f024e507" />
<img width="1213" height="757" alt="{E650D0C6-C563-4365-95E8-DCE1440CEC6E}" src="https://github.com/user-attachments/assets/f3dc6472-0df1-4cb6-a66d-2d2b0adf5cdc" />
<img width="1206" height="762" alt="{75B2109F-E727-4B2F-8B16-50B6E37B3922}" src="https://github.com/user-attachments/assets/30fb69bc-a2eb-4ad8-8605-bbc8cc0d6a40" />
<img width="1204" height="753" alt="{F2082AD5-7CCA-4A2F-85E1-32D0D9D95C42}" src="https://github.com/user-attachments/assets/2697c555-682a-4825-ab13-b095a04d2fed" />
<img width="1203" height="757" alt="{A5A6E659-EAC3-4AAD-846B-F3C94477D5D5}" src="https://github.com/user-attachments/assets/a3e8a452-1469-42f4-be08-3164c707185a" />
<img width="1209" height="754" alt="{A53C1C91-08A5-434F-B8BB-D3183542E35B}" src="https://github.com/user-attachments/assets/a5a9db91-5010-4889-bfcc-5b74a6ab7904" />
<img width="1207" height="750" alt="{CC5DF598-5EBD-441B-8FE0-27FC8BF15A9B}" src="https://github.com/user-attachments/assets/08c649b8-5400-4a1e-8837-950146d20e90" />
<img width="1209" height="757" alt="{8CDD277B-F896-4EF4-A46B-D54B63F61A43}" src="https://github.com/user-attachments/assets/98efbb68-721a-4d9a-a40d-270a4125a308" />
<img width="1213" height="753" alt="{A52C7675-F70A-4146-A697-BE454BB31CC3}" src="https://github.com/user-attachments/assets/59a7044a-5694-4eef-bfe1-7738ea637a29" />
<img width="1193" height="761" alt="{5C23139A-148E-455A-A49F-8B247F657715}" src="https://github.com/user-attachments/assets/2b904e30-5902-4062-b079-33177d50d399" />
<img width="1178" height="746" alt="{D19867DD-5B51-4EF5-BF22-4CC181D93357}" src="https://github.com/user-attachments/assets/f43fddad-b6d8-4682-98c0-fefda79e23ee" />
<img width="1181" height="762" alt="{4F4BCE27-ED9E-491E-9D74-359F992861BE}" src="https://github.com/user-attachments/assets/20225055-8873-403e-8616-fc4e7135f5c4" />
<img width="1210" height="753" alt="{0602AD39-0E25-4F17-9DDE-987EA5F6D674}" src="https://github.com/user-attachments/assets/c741f99d-6fab-4a87-9c36-e65969faf0e0" />
<img width="1202" height="755" alt="{D09E01A4-6BE4-40E0-B894-E218D1FBFAE7}" src="https://github.com/user-attachments/assets/074e3269-b8a5-477c-91f7-1da5b46e27e3" />
<img width="1209" height="753" alt="{B0E987B1-4ACB-4FDA-94E2-25DFA7AC81BE}" src="https://github.com/user-attachments/assets/bfad5258-ad44-4fed-861c-cd6e6b94b9d9" />
<img width="1184" height="755" alt="{68692A14-ACD1-4139-93AF-6196355088C7}" src="https://github.com/user-attachments/assets/4052a7e5-043e-430f-97b7-b3fe44a3647d" />




















