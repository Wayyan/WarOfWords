package com.team12.warofwords.word.checking;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Way yan on 10/12/2018.
 */

public class CheckIsValidWord {
    private Context context;
    private QuaryingData quaryingData;
    private String result;
    private CheckUsedWord checkUsedWord;


    public CheckIsValidWord(Context context) {
        this.context = context;
        quaryingData = new QuaryingData(this.context);
        checkUsedWord = new CheckUsedWord(this.context);
    }

    public boolean checkUserEnglishWord(String word) {

        boolean exist_in_db = false;
        boolean isused = checkUsedWord.checkIsUsed(word);

        if (isused) {
            Toast.makeText(context, "Word have been used", Toast.LENGTH_LONG).show();
            return false;
        } else {
            String cur = quaryingData.normalQuarying(word);
            if (cur != "") {
                exist_in_db = true;
               // Toast.makeText(context, cur, Toast.LENGTH_LONG).show();
                checkUsedWord.insertWord(word);
            } else
                Toast.makeText(context, "It is not a word", Toast.LENGTH_SHORT).show();
            return exist_in_db;
        }
    }

    public boolean checkAiEnglishWord(String word) {
      boolean isUsed=checkUsedWord.checkIsUsed(word);
      if(isUsed)
          return false;
      else
      {
          checkUsedWord.insertWord(word);
          return true;
      }
    }

    public void deleteWords() {
        checkUsedWord.clearAllWords();
    }

    public void closeRealm(){
        checkUsedWord.closeRealmUsedWord();
    }

    public boolean startIsEnd(String first, String second) {
        first=first.toLowerCase();
        second=second.toLowerCase();
        char endChar = first.charAt(first.length() - 1);
        char startChar = second.charAt(0);
        return endChar == startChar;
    }
}
