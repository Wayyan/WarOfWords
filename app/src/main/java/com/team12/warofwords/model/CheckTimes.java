package com.team12.warofwords.model;

import io.realm.RealmObject;

/**
 * Created by Way yan on 10/19/2018.
 */

public class CheckTimes extends RealmObject {
    private int id,times;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
