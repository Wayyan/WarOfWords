package com.team12.warofwords.word.checking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.team12.warofwords.SharePreferences.SharePreferenceStorage;
import com.team12.warofwords.database.SQLDatabase;
import com.team12.warofwords.model.CheckTimes;
import com.team12.warofwords.model.RealmDatabaseModel;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Way yan on 10/12/2018.
 */

public class QuaryingData {
    private Context context;
    private SQLDatabase myDb;
    private SQLiteDatabase sqlDb;
    private String normalQuaryInit = "SELECT * FROM FTSdictionary WHERE suggest_text_1='";
    private String normalQuary = "";
    private String allQuaryInit = "SELECT * FROM FTSdictionary WHERE suggest_text_1 LIKE '";
    private String allQuary;
    private String insertQuery = "INSERT INTO FTSdictionary VALUES('";
    private Realm realm;
    public static String[] A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
    public static String[] hA, hB, hC, hD, hE, hF, hG, hH, hI, hJ, hK, hL, hM, hN, hO, hP, hQ, hR, hS, hT, hU, hV, hW, hX, hY, hZ;

    public QuaryingData(Context context) {
        this.context = context;
        myDb = new SQLDatabase(this.context, "wowdictionary.bin");
        sqlDb = myDb.getDb();

    }

    public QuaryingData(Context context, boolean queryAll) {
        this.context = context;
        SharePreferenceStorage sharePreferenceStorage = new SharePreferenceStorage(this.context);
        myDb = new SQLDatabase(this.context, "wowdictionary.bin");
        sqlDb = myDb.getDb();
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        int time = sharePreferenceStorage.getAppTime();
        // Toast.makeText(context,time+"",Toast.LENGTH_SHORT).show();
        // Log.d("hass",time+"");
        if (time == 0) {
            deleteFromRealm();
            insertIntoRealm();
            sharePreferenceStorage.setAppTime(1);
        }

        // realm.beginTransaction();

        // insertIntoRealm();

        convertRealmIntoStringArray();
        // createObject();
//        Thread thread = new Thread(new MyThread());
//        thread.start();

    }

    private void insertIntoRealm() {
        realm.beginTransaction();
        RealmDatabaseModel aModel = realm.createObject(RealmDatabaseModel.class);
        aModel.setId(0);
        aModel.setSingleWordRow(allWordQuarying("a"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel bModel = realm.createObject(RealmDatabaseModel.class);
        bModel.setId(1);
        bModel.setSingleWordRow(allWordQuarying("b"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel cModel = realm.createObject(RealmDatabaseModel.class);
        cModel.setId(2);
        cModel.setSingleWordRow(allWordQuarying("c"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel dModel = realm.createObject(RealmDatabaseModel.class);
        dModel.setId(3);
        dModel.setSingleWordRow(allWordQuarying("d"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel eModel = realm.createObject(RealmDatabaseModel.class);
        eModel.setId(4);
        eModel.setSingleWordRow(allWordQuarying("e"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel fModel = realm.createObject(RealmDatabaseModel.class);
        fModel.setId(5);
        fModel.setSingleWordRow(allWordQuarying("f"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel gModel = realm.createObject(RealmDatabaseModel.class);
        gModel.setId(6);
        gModel.setSingleWordRow(allWordQuarying("g"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel hModel = realm.createObject(RealmDatabaseModel.class);
        hModel.setId(7);
        hModel.setSingleWordRow(allWordQuarying("h"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel iModel = realm.createObject(RealmDatabaseModel.class);
        iModel.setId(8);
        iModel.setSingleWordRow(allWordQuarying("i"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel jModel = realm.createObject(RealmDatabaseModel.class);
        jModel.setId(9);
        jModel.setSingleWordRow(allWordQuarying("j"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel kModel = realm.createObject(RealmDatabaseModel.class);
        kModel.setId(10);
        kModel.setSingleWordRow(allWordQuarying("k"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel lModel = realm.createObject(RealmDatabaseModel.class);
        lModel.setId(11);
        lModel.setSingleWordRow(allWordQuarying("l"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel mModel = realm.createObject(RealmDatabaseModel.class);
        mModel.setId(12);
        mModel.setSingleWordRow(allWordQuarying("m"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel nModel = realm.createObject(RealmDatabaseModel.class);
        nModel.setId(13);
        nModel.setSingleWordRow(allWordQuarying("n"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel oModel = realm.createObject(RealmDatabaseModel.class);
        oModel.setId(14);
        oModel.setSingleWordRow(allWordQuarying("o"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel pModel = realm.createObject(RealmDatabaseModel.class);
        pModel.setId(15);
        pModel.setSingleWordRow(allWordQuarying("p"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel qModel = realm.createObject(RealmDatabaseModel.class);
        qModel.setId(16);
        qModel.setSingleWordRow(allWordQuarying("q"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel rModel = realm.createObject(RealmDatabaseModel.class);
        rModel.setId(17);
        rModel.setSingleWordRow(allWordQuarying("r"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel sModel = realm.createObject(RealmDatabaseModel.class);
        sModel.setId(18);
        sModel.setSingleWordRow(allWordQuarying("s"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel tModel = realm.createObject(RealmDatabaseModel.class);
        tModel.setId(19);
        tModel.setSingleWordRow(allWordQuarying("t"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel uModel = realm.createObject(RealmDatabaseModel.class);
        uModel.setId(20);
        uModel.setSingleWordRow(allWordQuarying("u"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel vModel = realm.createObject(RealmDatabaseModel.class);
        vModel.setId(21);
        vModel.setSingleWordRow(allWordQuarying("v"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel wModel = realm.createObject(RealmDatabaseModel.class);
        wModel.setId(22);
        wModel.setSingleWordRow(allWordQuarying("w"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel xModel = realm.createObject(RealmDatabaseModel.class);
        xModel.setId(23);
        xModel.setSingleWordRow(allWordQuarying("x"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel yModel = realm.createObject(RealmDatabaseModel.class);
        yModel.setId(24);
        yModel.setSingleWordRow(allWordQuarying("y"));
        realm.commitTransaction();

        realm.beginTransaction();
        RealmDatabaseModel zModel = realm.createObject(RealmDatabaseModel.class);
        zModel.setId(25);
        zModel.setSingleWordRow(allWordQuarying("z"));
        realm.commitTransaction();
    }

    public String getRandomWord(char startWord) {

        //CheckUsedWord checkUsedWord = new CheckUsedWord(this.context);
        String[] getStartWordArray = null;
        String randomWord = "";
        switch (startWord) {
            case 'a':
                getStartWordArray = A;
                break;
            case 'b':
                getStartWordArray = B;
                break;
            case 'c':
                getStartWordArray = C;
                break;
            case 'd':
                getStartWordArray = D;
                break;
            case 'e':
                getStartWordArray = E;
                break;
            case 'f':
                getStartWordArray = F;
                break;
            case 'g':
                getStartWordArray = G;
                break;
            case 'h':
                getStartWordArray = H;
                break;
            case 'i':
                getStartWordArray = I;
                break;
            case 'j':
                getStartWordArray = J;
                break;
            case 'k':
                getStartWordArray = K;
                break;
            case 'l':
                getStartWordArray = L;
                break;
            case 'm':
                getStartWordArray = M;
                break;
            case 'n':
                getStartWordArray = N;
                break;
            case 'o':
                getStartWordArray = O;
                break;
            case 'p':
                getStartWordArray = P;
                break;
            case 'q':
                getStartWordArray = Q;
                break;
            case 'r':
                getStartWordArray = R;
                break;
            case 's':
                getStartWordArray = S;
                break;
            case 't':
                getStartWordArray = T;
                break;
            case 'u':
                getStartWordArray = U;
                break;
            case 'v':
                getStartWordArray = V;
                break;
            case 'w':
                getStartWordArray = W;
                break;
            case 'x':
                getStartWordArray = X;
                break;
            case 'y':
                getStartWordArray = Y;
                break;
            case 'z':
                getStartWordArray = Z;
                break;
        }
        int min = 0;
        int max = getStartWordArray.length;
        while (true) {
            int randomIndex = (int) (Math.random() * (max));
            randomWord = getStartWordArray[randomIndex];
            // if (!checkUsedWord.checkIsUsed(randomWord.split("//")[0])) {
            //  checkUsedWord.closeRealmUsedWord();
            return randomWord;
            // }

        }
    }

    public String getHardRandomWord(char startWord) {
        // CheckUsedWord checkUsedWord = new CheckUsedWord(this.context);
        String[] getStartWordArray = null;
        String randomWord = "";
        switch (startWord) {
            case 'a':
                getStartWordArray = hA;
                break;
            case 'b':
                getStartWordArray = hB;
                break;
            case 'c':
                getStartWordArray = hC;
                break;
            case 'd':
                getStartWordArray = hD;
                break;
            case 'e':
                getStartWordArray = E;
                break;
            case 'f':
                getStartWordArray = hF;
                break;
            case 'g':
                getStartWordArray = hG;
                break;
            case 'h':
                getStartWordArray = hH;
                break;
            case 'i':
                getStartWordArray = hI;
                break;
            case 'j':
                getStartWordArray = hJ;
                break;
            case 'k':
                getStartWordArray = hK;
                break;
            case 'l':
                getStartWordArray = hL;
                break;
            case 'm':
                getStartWordArray = hM;
                break;
            case 'n':
                getStartWordArray = N;
                break;
            case 'o':
                getStartWordArray = hO;
                break;
            case 'p':
                getStartWordArray = hP;
                break;
            case 'q':
                getStartWordArray = hQ;
                break;
            case 'r':
                getStartWordArray = hR;
                break;
            case 's':
                getStartWordArray = hS;
                break;
            case 't':
                getStartWordArray = hT;
                break;
            case 'u':
                getStartWordArray = U;
                break;
            case 'v':
                getStartWordArray = hV;
                break;
            case 'w':
                getStartWordArray = hW;
                break;
            case 'x':
                getStartWordArray = X;
                break;
            case 'y':
                getStartWordArray = Y;
                break;
            case 'z':
                getStartWordArray = hZ;
                break;
        }
        int min = 0;
        int max = getStartWordArray.length;
        //  while (true) {
        try {
            int randomIndex = (int) (Math.random() * (max));
            Log.d("whats", getStartWordArray.length + "  " + randomIndex);
            randomWord = getStartWordArray[randomIndex];
            //  if (!checkUsedWord.checkIsUsed(randomWord.split("//")[0])) {
            //  checkUsedWord.closeRealmUsedWord();
            Log.d("whats", randomWord + "  " + randomIndex);
            return randomWord;
        } catch (Exception e) {
        }
        // }
        // }
        return "error";
    }

    public String normalQuarying(String word) {
        String mmMeaning = "";
        normalQuary = normalQuaryInit + word + "'";
        Cursor cur = sqlDb.rawQuery(normalQuary, null);
        cur.moveToFirst();
        if (cur.getCount() > 0) {
            mmMeaning = cur.getString(1);//+ "//" + cur.getString(2);
        }
        return mmMeaning;
    }

    private String allWordQuarying(String startWord) {
        //  String[] returnArr = null;
        allQuary = allQuaryInit + startWord + "%'";
        Cursor cur = sqlDb.rawQuery(allQuary, null);
        String singleRow = "";
        String returnString = "";
        String hardString = "";
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            if (cur.getString(0).split(" ").length < 4) {
                singleRow = cur.getString(0) + "//" + cur.getString(1);// + "//" + cur.getString(2);
                returnString += singleRow + ">>";
                char lastChar = cur.getString(0).charAt(cur.getString(0).length() - 1);
                if (lastChar == 'x' || lastChar == 'q' || lastChar == 'v' || lastChar == 'z') //|| lastChar == 'y'
                    hardString += singleRow + ">>";
            }
        }
        if (startWord.equals("a")) {
            //realm.beginTransaction();
            RealmDatabaseModel aHardModel = realm.createObject(RealmDatabaseModel.class);
            aHardModel.setId(26);
            aHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("b")) {
            // realm.beginTransaction();
            RealmDatabaseModel bHardModel = realm.createObject(RealmDatabaseModel.class);
            bHardModel.setId(27);
            bHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("c")) {
            // realm.beginTransaction();
            RealmDatabaseModel cHardModel = realm.createObject(RealmDatabaseModel.class);
            cHardModel.setId(28);
            cHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("d")) {
            //realm.beginTransaction();
            RealmDatabaseModel dHardModel = realm.createObject(RealmDatabaseModel.class);
            dHardModel.setId(29);
            dHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("e")) {
            //realm.beginTransaction();
            RealmDatabaseModel eHardModel = realm.createObject(RealmDatabaseModel.class);
            eHardModel.setId(30);
            eHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("f")) {
            //realm.beginTransaction();
            RealmDatabaseModel fHardModel = realm.createObject(RealmDatabaseModel.class);
            fHardModel.setId(31);
            fHardModel.setSingleWordRow(hardString);
            //  realm.commitTransaction();
        } else if (startWord.equals("g")) {
            //realm.beginTransaction();
            RealmDatabaseModel gHardModel = realm.createObject(RealmDatabaseModel.class);
            gHardModel.setId(32);
            gHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("h")) {
            //  realm.beginTransaction();
            RealmDatabaseModel hHardModel = realm.createObject(RealmDatabaseModel.class);
            hHardModel.setId(33);
            hHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("i")) {
            // realm.beginTransaction();
            RealmDatabaseModel iHardModel = realm.createObject(RealmDatabaseModel.class);
            iHardModel.setId(34);
            iHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("j")) {
            //   realm.beginTransaction();
            RealmDatabaseModel jHardModel = realm.createObject(RealmDatabaseModel.class);
            jHardModel.setId(35);
            jHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("k")) {
            //  realm.beginTransaction();
            RealmDatabaseModel kHardModel = realm.createObject(RealmDatabaseModel.class);
            kHardModel.setId(36);
            kHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("l")) {
            //  realm.beginTransaction();
            RealmDatabaseModel lHardModel = realm.createObject(RealmDatabaseModel.class);
            lHardModel.setId(37);
            lHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("m")) {
            // realm.beginTransaction();
            RealmDatabaseModel mHardModel = realm.createObject(RealmDatabaseModel.class);
            mHardModel.setId(38);
            mHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("n")) {
            //realm.beginTransaction();
            RealmDatabaseModel nHardModel = realm.createObject(RealmDatabaseModel.class);
            nHardModel.setId(39);
            nHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("o")) {
            //realm.beginTransaction();
            RealmDatabaseModel oHardModel = realm.createObject(RealmDatabaseModel.class);
            oHardModel.setId(40);
            oHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("p")) {
            //  realm.beginTransaction();
            RealmDatabaseModel pHardModel = realm.createObject(RealmDatabaseModel.class);
            pHardModel.setId(41);
            pHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("q")) {
            //realm.beginTransaction();
            RealmDatabaseModel qHardModel = realm.createObject(RealmDatabaseModel.class);
            qHardModel.setId(42);
            qHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("r")) {
            //   realm.beginTransaction();
            RealmDatabaseModel rHardModel = realm.createObject(RealmDatabaseModel.class);
            rHardModel.setId(43);
            rHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("s")) {
            //   realm.beginTransaction();
            RealmDatabaseModel sHardModel = realm.createObject(RealmDatabaseModel.class);
            sHardModel.setId(44);
            sHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("t")) {
            //  realm.beginTransaction();
            RealmDatabaseModel tHardModel = realm.createObject(RealmDatabaseModel.class);
            tHardModel.setId(45);
            tHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("u")) {
            //  realm.beginTransaction();
            RealmDatabaseModel uHardModel = realm.createObject(RealmDatabaseModel.class);
            uHardModel.setId(46);
            uHardModel.setSingleWordRow(hardString);
            //realm.commitTransaction();
        } else if (startWord.equals("v")) {
            //  realm.beginTransaction();
            RealmDatabaseModel vHardModel = realm.createObject(RealmDatabaseModel.class);
            vHardModel.setId(47);
            vHardModel.setSingleWordRow(hardString);
            //  realm.commitTransaction();
        } else if (startWord.equals("w")) {
            //   realm.beginTransaction();
            RealmDatabaseModel wHardModel = realm.createObject(RealmDatabaseModel.class);
            wHardModel.setId(48);
            wHardModel.setSingleWordRow(hardString);
            //  realm.commitTransaction();
        } else if (startWord.equals("x")) {
            //  realm.beginTransaction();
            RealmDatabaseModel xHardModel = realm.createObject(RealmDatabaseModel.class);
            xHardModel.setId(49);
            xHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("y")) {
            //  realm.beginTransaction();
            RealmDatabaseModel yHardModel = realm.createObject(RealmDatabaseModel.class);
            yHardModel.setId(50);
            yHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        } else if (startWord.equals("z")) {
            //  realm.beginTransaction();
            RealmDatabaseModel zHardModel = realm.createObject(RealmDatabaseModel.class);
            zHardModel.setId(51);
            zHardModel.setSingleWordRow(hardString);
            // realm.commitTransaction();
        }


        return returnString;
    }

    private void convertRealmIntoStringArray() {
        RealmResults<RealmDatabaseModel> lModel = realm.where(RealmDatabaseModel.class).greaterThan("id", -1).findAll();
//        Toast.makeText(context, lModel.size() + "", Toast.LENGTH_SHORT).show();
        A = realm.where(RealmDatabaseModel.class).equalTo("id", 0).findAll().get(0).getSingleWordRow().split(">>");
        B = realm.where(RealmDatabaseModel.class).equalTo("id", 1).findAll().get(0).getSingleWordRow().split(">>");
        C = realm.where(RealmDatabaseModel.class).equalTo("id", 2).findAll().get(0).getSingleWordRow().split(">>");
        D = realm.where(RealmDatabaseModel.class).equalTo("id", 3).findAll().get(0).getSingleWordRow().split(">>");
        E = realm.where(RealmDatabaseModel.class).equalTo("id", 4).findAll().get(0).getSingleWordRow().split(">>");
        F = realm.where(RealmDatabaseModel.class).equalTo("id", 5).findAll().get(0).getSingleWordRow().split(">>");
        G = realm.where(RealmDatabaseModel.class).equalTo("id", 6).findAll().get(0).getSingleWordRow().split(">>");
        H = realm.where(RealmDatabaseModel.class).equalTo("id", 7).findAll().get(0).getSingleWordRow().split(">>");
        I = realm.where(RealmDatabaseModel.class).equalTo("id", 8).findAll().get(0).getSingleWordRow().split(">>");
        J = realm.where(RealmDatabaseModel.class).equalTo("id", 9).findAll().get(0).getSingleWordRow().split(">>");
        K = realm.where(RealmDatabaseModel.class).equalTo("id", 10).findAll().get(0).getSingleWordRow().split(">>");
        L = realm.where(RealmDatabaseModel.class).equalTo("id", 11).findAll().get(0).getSingleWordRow().split(">>");
        M = realm.where(RealmDatabaseModel.class).equalTo("id", 12).findAll().get(0).getSingleWordRow().split(">>");
        N = realm.where(RealmDatabaseModel.class).equalTo("id", 13).findAll().get(0).getSingleWordRow().split(">>");
        O = realm.where(RealmDatabaseModel.class).equalTo("id", 14).findAll().get(0).getSingleWordRow().split(">>");
        P = realm.where(RealmDatabaseModel.class).equalTo("id", 15).findAll().get(0).getSingleWordRow().split(">>");
        Q = realm.where(RealmDatabaseModel.class).equalTo("id", 16).findAll().get(0).getSingleWordRow().split(">>");
        R = realm.where(RealmDatabaseModel.class).equalTo("id", 17).findAll().get(0).getSingleWordRow().split(">>");
        S = realm.where(RealmDatabaseModel.class).equalTo("id", 18).findAll().get(0).getSingleWordRow().split(">>");
        T = realm.where(RealmDatabaseModel.class).equalTo("id", 19).findAll().get(0).getSingleWordRow().split(">>");
        U = realm.where(RealmDatabaseModel.class).equalTo("id", 20).findAll().get(0).getSingleWordRow().split(">>");
        V = realm.where(RealmDatabaseModel.class).equalTo("id", 21).findAll().get(0).getSingleWordRow().split(">>");
        W = realm.where(RealmDatabaseModel.class).equalTo("id", 22).findAll().get(0).getSingleWordRow().split(">>");
        X = realm.where(RealmDatabaseModel.class).equalTo("id", 23).findAll().get(0).getSingleWordRow().split(">>");
        Y = realm.where(RealmDatabaseModel.class).equalTo("id", 24).findAll().get(0).getSingleWordRow().split(">>");
        Z = realm.where(RealmDatabaseModel.class).equalTo("id", 25).findAll().get(0).getSingleWordRow().split(">>");

        hA = realm.where(RealmDatabaseModel.class).equalTo("id", 26).findAll().get(0).getSingleWordRow().split(">>");
        hB = realm.where(RealmDatabaseModel.class).equalTo("id", 27).findAll().get(0).getSingleWordRow().split(">>");
        hC = realm.where(RealmDatabaseModel.class).equalTo("id", 28).findAll().get(0).getSingleWordRow().split(">>");
        hD = realm.where(RealmDatabaseModel.class).equalTo("id", 29).findAll().get(0).getSingleWordRow().split(">>");
        hE = realm.where(RealmDatabaseModel.class).equalTo("id", 30).findAll().get(0).getSingleWordRow().split(">>");
        hF = realm.where(RealmDatabaseModel.class).equalTo("id", 31).findAll().get(0).getSingleWordRow().split(">>");
        hG = realm.where(RealmDatabaseModel.class).equalTo("id", 32).findAll().get(0).getSingleWordRow().split(">>");
        hH = realm.where(RealmDatabaseModel.class).equalTo("id", 33).findAll().get(0).getSingleWordRow().split(">>");
        hI = realm.where(RealmDatabaseModel.class).equalTo("id", 34).findAll().get(0).getSingleWordRow().split(">>");
        hJ = realm.where(RealmDatabaseModel.class).equalTo("id", 35).findAll().get(0).getSingleWordRow().split(">>");
        hK = realm.where(RealmDatabaseModel.class).equalTo("id", 36).findAll().get(0).getSingleWordRow().split(">>");
        hL = realm.where(RealmDatabaseModel.class).equalTo("id", 37).findAll().get(0).getSingleWordRow().split(">>");
        hM = realm.where(RealmDatabaseModel.class).equalTo("id", 38).findAll().get(0).getSingleWordRow().split(">>");
        hN = realm.where(RealmDatabaseModel.class).equalTo("id", 39).findAll().get(0).getSingleWordRow().split(">>");
        hO = realm.where(RealmDatabaseModel.class).equalTo("id", 40).findAll().get(0).getSingleWordRow().split(">>");
        hP = realm.where(RealmDatabaseModel.class).equalTo("id", 41).findAll().get(0).getSingleWordRow().split(">>");
        hQ = realm.where(RealmDatabaseModel.class).equalTo("id", 42).findAll().get(0).getSingleWordRow().split(">>");
        hR = realm.where(RealmDatabaseModel.class).equalTo("id", 43).findAll().get(0).getSingleWordRow().split(">>");
        hS = realm.where(RealmDatabaseModel.class).equalTo("id", 44).findAll().get(0).getSingleWordRow().split(">>");
        hT = realm.where(RealmDatabaseModel.class).equalTo("id", 45).findAll().get(0).getSingleWordRow().split(">>");
        hU = realm.where(RealmDatabaseModel.class).equalTo("id", 46).findAll().get(0).getSingleWordRow().split(">>");
        hV = realm.where(RealmDatabaseModel.class).equalTo("id", 47).findAll().get(0).getSingleWordRow().split(">>");
        hW = realm.where(RealmDatabaseModel.class).equalTo("id", 48).findAll().get(0).getSingleWordRow().split(">>");
        hX = realm.where(RealmDatabaseModel.class).equalTo("id", 49).findAll().get(0).getSingleWordRow().split(">>");
        hY = realm.where(RealmDatabaseModel.class).equalTo("id", 50).findAll().get(0).getSingleWordRow().split(">>");
        hZ = realm.where(RealmDatabaseModel.class).equalTo("id", 51).findAll().get(0).getSingleWordRow().split(">>");

//        for (String s : hG) {
//            Log.d("Hards", s);
//        }
//        for (String s : hH) {
//            Log.d("Hards", s);
//        }
//        for (String s : hI) {
//            Log.d("Hards", s);
//        }
//        for (String s : hJ) {
//            Log.d("Hards", s);
//        }
//        for (String s : hK) {
//            Log.d("Hards", s);
//        }
//        for (String s : hL) {
//            Log.d("Hards", s);
//        }
//        for (String s : hM) {
//            Log.d("Hards", s);
//        }
//        for (String s : hN) {
//            Log.d("Hards", s);
//        }
//        for (String s : hO) {
//            Log.d("Hards", s);
//        }
//        for (String s : hP) {
//            Log.d("Hards", s);
//        }
//        for (String s : hQ) {
//            Log.d("Hards", s);
//        }
//        for (String s : hR) {
//            Log.d("Hards", s);
//        }
//        for (String s : hS) {
//            Log.d("Hards", s);
//        }
//        for (String s : hT) {
//            Log.d("Hards", s);
//        }
//        for (String s : hU) {
//            Log.d("Hards", s);
//        }
//        for (String s : hV) {
//            Log.d("Hards", s);
//        }
//        for (String s : hW) {
//            Log.d("Hards", s);
//        }
//        for (String s : hY) {
//            Log.d("Hards", s);
//        }
//        for (String s : hZ) {
//            Log.d("Hards", s);
//        }
        for (String s : S) {
            Log.d("Success", s);
        }
        realm.close();
    }

    public void deleteFromRealm() {
        realm.beginTransaction();
        realm.delete(RealmDatabaseModel.class);
        realm.commitTransaction();
        A = B = C = D = E = F = G = H = I = J = K = L = M = N = O = P = Q = R = S = T = U = V = W = X = Y = Z = hA = hB = hC = hD = hE = hF = hG = hH = hI = hJ = hK = hL = hM = hN = hO = hP = hQ = hR = hS = hT = hU = hV = hW = hX = hY = hZ = null;
    }

    public void insertWord(String engWord, String mmWord) {
        if ("".equals(normalQuarying(engWord))) {
            // String insert=insertQuery+engWord+"','"+mmWord+"'";
            ContentValues contentValues = new ContentValues();
            contentValues.put("suggest_text_1", engWord);
            contentValues.put("suggest_text_2", mmWord);
            sqlDb.insert("FTSdictionary", null, contentValues);
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public String getDesiredWords(String userWord,String prepareWord){
        String startWord=userWord.charAt(userWord.length()-1)+"";
        String endWord=prepareWord.charAt(0)+"";
        return getPrepareWord(startWord,endWord);
    }

    private String getPrepareWord(String start,String end){
        CheckUsedWord checkUsedWord = new CheckUsedWord(this.context);
        String query=allQuaryInit+start+"%%"+end+"'";
        Cursor cur=sqlDb.rawQuery(query,null);
        int max=cur.getCount();
        Log.d("kids1",max+"");
        if(max>2){
        while(true){
        int randomIndex=(int) (Math.random() * (max)+1);
        Log.d("kids2",randomIndex+"");
        cur.move(randomIndex);
//        if(checkUsedWord.checkIsUsed(cur.getString(0)))
        return cur.getString(0)+"//"+cur.getString(1);}}
        else{
            return getRandomWord(start.charAt(0));
        }
    }


}
//
//    public class MyThread implements Runnable {
//
//        @Override
//        public void run() {
//            queryAllWords();
//        }
//    }

