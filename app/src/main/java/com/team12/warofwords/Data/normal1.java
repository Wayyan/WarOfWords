package com.team12.warofwords.Data;

public class normal1  {
    private String Question,Answers,rightAnswer;


    public normal1(String Question, String Answers, String rightAnswer) {
        this.Question = Question;
        this.Answers = Answers;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswers() {
        return Answers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setnQuestion(String Question) {
        this.Question = Question;
    }

    public void setAnswers(String Answers) {
        this.Answers = Answers;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
