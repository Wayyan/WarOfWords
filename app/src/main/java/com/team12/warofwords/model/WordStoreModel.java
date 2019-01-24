package com.team12.warofwords.model;

/**
 * Created by Way yan on 10/16/2018.
 */

public class WordStoreModel {
    private String word;
    private boolean isleft;
    private String myanmrWord;
    public WordStoreModel(String word,boolean isleft,String myanmrWord){
        this.word=word;
        this.isleft=isleft;
        this.myanmrWord=myanmrWord;
    }

    public boolean isIsleft() {
        return isleft;
    }

    public String getWord() {
        return word;
    }

    public String getMyanmrWord(){return myanmrWord;}
}

