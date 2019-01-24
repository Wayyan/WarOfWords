package com.team12.warofwords.model;

import io.realm.RealmObject;

/**
 * Created by Way yan on 10/19/2018.
 */

public class RealmDatabaseModel extends RealmObject {
    private int id;
    private String singleWordRow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSingleWordRow() {
        return this.singleWordRow;
    }

    public void setSingleWordRow(String singleWordRow) {
        this.singleWordRow = singleWordRow;
    }
}
