package com.team12.warofwords;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.team12.warofwords.Adapter.IntermediatesAdapter;
import com.team12.warofwords.Adapter.NormalsAdapter;
import com.team12.warofwords.DB.AppDatabase;
import com.team12.warofwords.entity.Intermediate;
import com.team12.warofwords.entity.Normal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.myatminsoe.mdetect.MDetect;

public class KidPuzzleActivity extends AppCompatActivity {

    @BindView(R.id.rcNormal)
    RecyclerView rcNormal;

    @BindView(R.id.rcIntermediate)
    RecyclerView rcIntermediate;

    private NormalsAdapter normalsAdapter;
    private IntermediatesAdapter intermediatesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDetect.INSTANCE.init(this);
        setContentView(R.layout.activity_kids_puzzle_main);

        insertFirstData();

        ButterKnife.bind(this, this);

        normalsAdapter = new NormalsAdapter(this, new ArrayList<Normal>());
        prepareRecyclerView(true);

        intermediatesAdapter = new IntermediatesAdapter(this, new ArrayList<Intermediate>());
        prepareRecyclerView(false);

        prepareData(true);
        prepareData(false);


    }

    private void insertFirstData() {
        if (AppDatabase.getAppDatabase(this).normalDAO().countNormals() == 0) {
            AppDatabase.getAppDatabase(this).normalDAO().insertAll(Normal.populateData());

        }
        if (AppDatabase.getAppDatabase(this).intermediateDAO().countIntermediates() == 0) {
            AppDatabase.getAppDatabase(this).intermediateDAO().insertAll(Intermediate.populateData());
        }
    }

    public void prepareRecyclerView(Boolean isNormal) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        if (isNormal) {

            rcNormal.setLayoutManager(mLayoutManager);
            rcNormal.setItemAnimator(new DefaultItemAnimator());
            rcNormal.setAdapter(normalsAdapter);

        } else {

            rcIntermediate.setLayoutManager(mLayoutManager);
            rcIntermediate.setItemAnimator(new DefaultItemAnimator());
            rcIntermediate.setAdapter(intermediatesAdapter);

        }
    }

    public void prepareData(Boolean isNormal) {
        if (isNormal) {
            AppDatabase.getAppDatabase(this).normalDAO().getAll().observe(this, new Observer<List<Normal>>() {
                @Override
                public void onChanged(@Nullable List<Normal> normals) {
                    normalsAdapter.swipeData(normals);
                }
            });

        } else {
            AppDatabase.getAppDatabase(this).intermediateDAO().getAll().observe(this, new Observer<List<Intermediate>>() {
                @Override
                public void onChanged(@Nullable List<Intermediate> intermediates) {
                    intermediatesAdapter.swipeData(intermediates);
                }
            });
        }
    }
}
