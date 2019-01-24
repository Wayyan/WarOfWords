package com.team12.warofwords;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.team12.warofwords.fragment.intermediate_fragment1;
import com.team12.warofwords.fragment.intermediate_fragment2;
import com.team12.warofwords.fragment.intermediate_fragment3;
import com.team12.warofwords.fragment.normal_fragment1;
import com.team12.warofwords.fragment.normal_fragment2;
import com.team12.warofwords.fragment.normal_fragment3;


public class AlterActivity extends AppCompatActivity {


    private int levelID;
    private Boolean isNormal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);


        Bundle bundle = getIntent().getExtras();
        levelID = bundle.getInt("levelID");
        isNormal = bundle.getBoolean("isNormal");


        Fragment fragment = null;
        if (isNormal)
            fragment = goToNormal(fragment);
        else fragment = goToIntermediate(fragment);


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();//call from activity to load fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);//to replace at Linear layout
            fragmentTransaction.commit();
        }


    }

    public Fragment goToNormal(Fragment fg) {
        String title = null;

        switch (levelID) {
            case 1:
                fg = new normal_fragment1();
                title="Mission 1";
                break;
            case 2:
                fg = new normal_fragment2();
                title="Mission 2";
                break;
            case 3:
                fg = new normal_fragment3();
                title="Mission 3";
                break;

        }
        getSupportActionBar().setTitle(title);
        return fg;
    }

    public Fragment goToIntermediate(Fragment fg) {
        String title=null;
        switch (levelID) {
            case 1:
                fg = new intermediate_fragment1();
                title="Mission 1";
                break;
            case 2:
                fg = new intermediate_fragment2();
                title="Mission 2";
                break;
            case 3:
                fg = new intermediate_fragment3();
                title="Mission 3";
                break;

        }
        getSupportActionBar().setTitle(title);
        return fg;
    }
}
