package com.example.musicme.objects;

public class QuestionAttributes {
    private int correctIndex;
    private QuestionNameHolder questionNameHolder;
    private String soundfile;

    public QuestionAttributes(int correctIndex, QuestionNameHolder questionNameHolder, String soundfile) {
        this.correctIndex = correctIndex;
        this.questionNameHolder = questionNameHolder;
        this.soundfile = soundfile;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public QuestionNameHolder getQuestionNameHolder() {
        return questionNameHolder;
    }

    public void setQuestionNameHolder(QuestionNameHolder questionNameHolder) {
        this.questionNameHolder = questionNameHolder;
    }

    public String getSoundfile() {
        return soundfile;
    }

    public void setSoundfile(String soundfile) {
        this.soundfile = soundfile;
    }
}


