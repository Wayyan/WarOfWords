package com.team12.warofwords.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "normal")
public class Normal {

    @PrimaryKey(autoGenerate = true)
    private int levelID;

    @ColumnInfo(name = "questions")
    private int questions;

    @ColumnInfo(name = "scores")
    private int scores;

    @ColumnInfo(name = "diamonds")
    private int diamonds;

    @ColumnInfo(name = "isOn")
    Boolean isOn;

    @ColumnInfo(name = "gameCount")
    int gameCount;

    public Normal() {
    }

    public Normal(int levelID, int questions, int scores, int diamonds, Boolean isOn, int gameCount) {
        this.levelID = levelID;
        this.questions = questions;
        this.scores = scores;
        this.diamonds = diamonds;
        this.isOn = isOn;
        this.gameCount = gameCount;
    }

    public int getLevelID() {
        return levelID;
    }

    public int getQuestions() {
        return questions;
    }

    public int getScores() {
        return scores;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public Boolean getOn() {
        return isOn;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public static Normal[] populateData() {
        return new Normal[]{
                new Normal(1, 25, 0, 1, true, 0),
                new Normal(2, 45, 0, 1, false, 0),
                new Normal(3, 50, 0, 1, false, 0),
                new Normal(4, 0, 0, 1, false, 0),
        };
    }
}
