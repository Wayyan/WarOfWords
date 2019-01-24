package com.team12.warofwords.SharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Way yan on 10/19/2018.
 */

public class SharePreferenceStorage {
    private Context context;
    private SharedPreferences shp;
    final String KEY1 = "UPDATE_LAVEL";
    final String KEY_2 = "RELOAD_REALM";
    final String KEY_SCORE = "Highest Score";
    final String KEY_HARD = "Highest Hard";
    final String KEY_KID = "Highest Kid";
    final String KEY_SINGLE = "Single";
    final String KEY_DUAL = "Dual";
    final String KEY_CHALLENGE = "Challenge";
    final String KEY_LOGIN = "Login";
    final String KEY_USERNAME = "UserName";
    final String KEY_PROFILE_PIC = "Profile Pic";
    final String KEY_FIRST_LOGIN = "First Login";
    final String KEY_TITLE="Title";
    final String KEY_PATANAR_AVATAR="Partaner";
    final String FILE_NAME = "MY_PREF";

    public SharePreferenceStorage(Context context) {
        this.context = context;
        shp = this.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getUpdateLevel() {
        return shp.getString(KEY1, "0");
    }

    public void setUpdateLavel(String lavel) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(KEY1, lavel);
        editor.commit();
    }

    public int getAppTime() {
        return shp.getInt(KEY_2, 0);
    }

    public void setAppTime(int time) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_2, time);
        editor.commit();
    }

    public void setHighestScore(int score) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_SCORE, score);
        editor.commit();
    }

    public int getHighestScore() {
        return shp.getInt(KEY_SCORE, 100);
    }

    public void setHighestHard(int score) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_HARD, score);
        editor.commit();
    }

    public int getHighestHard() {
        return shp.getInt(KEY_HARD, 100);
    }

    public void setHighestKid(int score) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_KID, score);
        editor.commit();
    }

    public int getHighestKid() {
        return shp.getInt(KEY_KID, 100);
    }

    public void setSingleMatch(int match) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_SINGLE, match);
        editor.commit();
    }

    public void setDualMatch(int match) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_DUAL, match);
        editor.commit();
    }

    public void setChallengeMatch(int match) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_CHALLENGE, match);
        editor.commit();
    }

    public void setIsLogIn(boolean login) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putBoolean(KEY_LOGIN, login);
        editor.commit();
    }

    public void setUserName(String name) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(KEY_USERNAME, name);
        editor.commit();
    }

    public void setPartaner(int n) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_PATANAR_AVATAR, n);
        editor.commit();
    }

    public int getPartner(){
        return shp.getInt(KEY_PATANAR_AVATAR,0);
    }

    public void setProfilePicture(int id) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_PROFILE_PIC, id);
        editor.commit();
    }

    public void setFirstLogIn(boolean b) {
        SharedPreferences.Editor editor = shp.edit();
        editor.putBoolean(KEY_FIRST_LOGIN, b);
        editor.commit();
    }

    public void setTitle(String title){
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(KEY_TITLE, title);
        editor.commit();
    }

    public String getTitle(){
        return shp.getString(KEY_TITLE,"(New Born)");
    }
    public boolean getFirstLogIn() {
        return shp.getBoolean(KEY_FIRST_LOGIN, true);
    }

    public String getUserName() {
        return shp.getString(KEY_USERNAME, "");
    }

    public int getProfilePicture() {
        return shp.getInt(KEY_PROFILE_PIC, 0);
    }

    public boolean getIsLogin() {
        return shp.getBoolean(KEY_LOGIN, false);
    }

    public int getSingleMatch() {
        return shp.getInt(KEY_SINGLE, 1);
    }

    public int getDualMatch() {
        return shp.getInt(KEY_DUAL, 1);
    }

    public int getChallengeMatch() {
        return shp.getInt(KEY_CHALLENGE, 1);
    }
}
