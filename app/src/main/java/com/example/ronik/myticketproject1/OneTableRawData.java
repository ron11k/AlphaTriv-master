package com.example.ronik.myticketproject1;

public class OneTableRawData {
    String question;
    String correctAnswer;
    String wrongAnswer0;
    String wrongAnswer1;
    String wrongAnswer2;



    public OneTableRawData(String question, String correctAnswer, String wrongAnswer0, String wrongAnswer1, String wrongAnswer2){
        this.question=question;
        this.correctAnswer=correctAnswer;
        this.wrongAnswer0=wrongAnswer0;
        this.wrongAnswer1=wrongAnswer1;
        this.wrongAnswer2=wrongAnswer2;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer0() {
        return wrongAnswer0;
    }

    public void setWrongAnswer0(String wrongAnswer0) {
        this.wrongAnswer0 = wrongAnswer0;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
}



