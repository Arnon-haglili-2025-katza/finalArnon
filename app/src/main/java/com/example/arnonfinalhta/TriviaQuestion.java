package com.example.arnonfinalhta;

public class TriviaQuestion {

    public String question;
    public String[] options;
    public int correctIndex;

    public TriviaQuestion(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}