package com.team12.warofwords.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Way yan on 10/11/2018.
 */

public class DbUsedWordsAdapter {
    private Context context;
    private DbUsedWords dbUsedWords;
    private SQLiteDatabase sqlDatabase;

    public DbUsedWordsAdapter(Context context) {
        this.context = context;

    }

    public void dbOpen() {
        dbUsedWords = new DbUsedWords(context);
        sqlDatabase = dbUsedWords.getWritableDatabase();
    }

    public void dbClose() {
        sqlDatabase.close();
    }

    public void dataDelete()
    {
       sqlDatabase.execSQL("delete  from "+DbUsedWords.TABLE_NAME);
    }
    public boolean isUsedWord(String word) {
        boolean isUsed=false;
        String query1 = "SELECT * FROM " + DbUsedWords.TABLE_NAME + " WHERE " +DbUsedWords.word+"='"+ word + "'";
        Cursor cur = sqlDatabase.rawQuery(query1, null);
        cur.moveToFirst();
        if(cur.getCount()>0)
            isUsed=true;
        else
            isUsed=false;
        return isUsed;

    }

    public long insertData(String word) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbUsedWords.word, word);
        return sqlDatabase.insert(DbUsedWords.TABLE_NAME, null, contentValues);
    }

    class DbUsedWords extends SQLiteOpenHelper {
        private final static String DB_NAME = "RAW_DB";
        private final static int version = 1;
        private final static String TABLE_NAME = "USED_WORDS";
        public final static String word = "WORD";

        public DbUsedWords(Context context) {
            super(context, DB_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (WID integer PRIMARY KEY AUTOINCREMENT," + word + " varchar(225))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
