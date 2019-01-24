package com.team12.warofwords.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Way yan on 11/5/2018.
 */

public class FireDB {
    private static DatabaseReference INSTANCE;

    public static DatabaseReference getFirebaseDB(){

        if(INSTANCE == null){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            INSTANCE = FirebaseDatabase.getInstance().getReference();
        }
        return INSTANCE;
    }
}
