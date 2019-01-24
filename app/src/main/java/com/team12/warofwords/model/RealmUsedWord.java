package com.team12.warofwords.model;

import io.realm.RealmObject;

/**
 * Created by Way yan on 10/28/2018.
 */

public class RealmUsedWord extends RealmObject {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
