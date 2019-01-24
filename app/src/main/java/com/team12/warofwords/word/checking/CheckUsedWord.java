package com.team12.warofwords.word.checking;

import android.content.Context;

import com.team12.warofwords.model.RealmUsedWord;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Way yan on 10/28/2018.
 */

public class CheckUsedWord {
    private  Context context;
    private Realm mRealm;
    public CheckUsedWord(Context context){
        this.context=context;
        Realm.init(this.context);
        mRealm=Realm.getDefaultInstance();
    }
    public boolean checkIsUsed(String word)
    {   boolean used=false;
        RealmResults<RealmUsedWord> results=mRealm.where(RealmUsedWord.class).equalTo("word",word).findAll();
        if(results.isEmpty()){
            used=false;
        }
        else {
            used=true;
        }
        return  used;
    }

    public void insertWord(String word){
        mRealm.beginTransaction();
        RealmUsedWord realmUsedWord=mRealm.createObject(RealmUsedWord.class);
        realmUsedWord.setWord(word);
        mRealm.commitTransaction();
    }

    public void clearAllWords(){
        mRealm.beginTransaction();
        mRealm.delete(RealmUsedWord.class);
        mRealm.commitTransaction();
        //closeRealmUsedWord();
    }

    public void closeRealmUsedWord(){
        mRealm.close();
    }

}
