package com.team12.warofwords.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.team12.warofwords.DB.AppDatabase;
import com.team12.warofwords.Data.intermediate1;
import com.team12.warofwords.FinishActivity;
import com.team12.warofwords.R;
import com.team12.warofwords.entity.Intermediate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.myatminsoe.mdetect.MDetect;

public class intermediate_fragment2 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.tScore)
    TextView tScore;

    @BindView(R.id.tDiamond)
    TextView tDiamond;

    @BindView(R.id.iQuestion)
    TextView tQuestion;

    @BindView(R.id.rgIntermediate)
    RadioGroup rgIntermediate;

    @BindView(R.id.iRbtn1)
    RadioButton rdButton1;

    @BindView(R.id.iRbtn2)
    RadioButton rdButton2;


    @BindView(R.id.ibtnFree)
    Button btnFree;

    @BindView(R.id.ibtnOkay)
    Button btnOkay;

    private com.team12.warofwords.entity.Intermediate Intermediate = AppDatabase.getAppDatabase(getContext()).intermediateDAO().findByID(2);
    private int totalScore = Intermediate.getScores();
    private int totalDiamond = Intermediate.getDiamonds();
    private int totalGameCount = Intermediate.getGameCount();

    private int[] gameCountList = new int[]{R.id.v1, R.id.v2, R.id.v3, R.id.v4, R.id.v5};
    private ArrayList<intermediate1> intermediateList = new ArrayList<>();
    private static List<Integer> quesCount = null;
    private List<Integer> ansCount = new ArrayList<>();

    private int diamond = 0, score = 0, gameCount = 0;
    private String[] Answers;
    private String rigthAnswer;


    public intermediate_fragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intermediate2, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getActivity());


        resetGame();
        setResult();
        prepareNormalData();

        btnOkay.setText("NEXT");
        getRandomData();

        rgIntermediate.setOnCheckedChangeListener(this);
        btnOkay.setOnClickListener(this);
        btnFree.setOnClickListener(this);

        Toast.makeText(getActivity().getApplicationContext(), "Level " +(Intermediate.getGameCount()+1), Toast.LENGTH_SHORT).show();
    }

    public void resetGame() {

        if (totalGameCount == 5) {
            Intermediate.setScores(0);
            Intermediate.setDiamonds(1);
            Intermediate.setGameCount(0);

            totalScore = Intermediate.getScores();
            totalDiamond = Intermediate.getDiamonds();
            totalGameCount = Intermediate.getGameCount();

        }
        if (totalGameCount == 0) {
            quesCount = new ArrayList<>();
            Toast.makeText(getActivity().getApplicationContext(), "Start mission "+Intermediate.getLevelID(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setResult() {
        tDiamond.setText(totalDiamond + "");
        tScore.setText("" + totalScore);
    }


    private void prepareNormalData() {

        try {
            JSONArray arrJSON = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < arrJSON.length(); i++) {
                JSONObject objJSON = arrJSON.getJSONObject(i);

                intermediate1 intermediate1 = new intermediate1(objJSON.getString("question"), objJSON.getString("answers"), objJSON.getString("ranswer"));
                intermediateList.add(intermediate1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String loadJSONFromAsset() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getActivity().getAssets().open("intermediate2.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(is));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

            Log.d("RESPONSE ", stringBuilder.toString());

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void getRandomData() {

        View view = getActivity().findViewById(gameCountList[gameCount]);
        view.setBackgroundColor(getResources().getColor(R.color.checkedColor));


        intermediate1 intermediate1 = intermediateList.get(getRandom(quesCount, intermediateList.size()));

        tQuestion.setText(intermediate1.getQuestion());
        rigthAnswer = intermediate1.getRightAnswer();

        Answers = intermediate1.getAnswers().split(",");
        ansCount.clear();


        for (int i = 0; i < Answers.length; i++) {
            getRandom(ansCount, Answers.length);
        }

        rdButton1.setText(MDetect.INSTANCE.getText(Answers[ansCount.get(0)]));
        rdButton2.setText(MDetect.INSTANCE.getText(Answers[ansCount.get(1)]));


        rdButton1.setEnabled(true);
        rdButton2.setEnabled(true);


        if (totalDiamond <= 0)
            btnFree.setEnabled(false);
        else btnFree.setEnabled(true);


    }


    public int getRandom(List<Integer> count, int bound) {
        Random random = new Random();
        int index;

        Boolean same = false;

        do {

            index = random.nextInt(bound);
            int ct = 0;
            if (count==null)
            {
                count=new ArrayList<>();
            }
            for (int i = 0; i < count.size(); i++) {
                if (count.get(i) == index) {
                    same = true;
                    ct++;
                }
            }

            if (ct == 0)
                same = false;
        } while (same);


        count.add(index);
        return index;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (!btnOkay.isEnabled()) {
            isRight();
            showRigthAnswer();
            if (gameCount < 7) {
                gameCount++;
            } else {
                gameCount = 0;
                btnOkay.setText("FINISH");
            }
            btnOkay.setEnabled(true);
        }
        else resetColor();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnFree:
                totalDiamond--;
                setResult();

                if (totalDiamond <= 0)
                    btnFree.setEnabled(false);

                rgIntermediate.check(getRightAnswer());


                break;
            case R.id.ibtnOkay:
                doOkay();
                break;
        }
    }

    public int getRightAnswer() {

        rdButton1.setEnabled(false);
        rdButton2.setEnabled(false);

        if (rigthAnswer.equals(rdButton1.getText().toString()))
            return R.id.iRbtn1;
        else return R.id.iRbtn2;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void doOkay() {
        switch (btnOkay.getText().toString()) {
            case "FINISH":

                Intermediate.setDiamonds(totalDiamond);
                Intermediate.setScores(totalScore);
                Intermediate.setGameCount(totalGameCount + 1);

                AppDatabase.getAppDatabase(getContext()).intermediateDAO().update(Intermediate);

                Intent i = new Intent(getActivity(), FinishActivity.class);
                i.putExtra("levelID", Intermediate.getLevelID());
                i.putExtra("isNormal", false);
                i.putExtra("score", score);
                i.putExtra("diamond", diamond);

                getActivity().finish();
                startActivity(i);

                break;

            case "NEXT":


                RadioButton rbtn = getActivity().findViewById(rgIntermediate.getCheckedRadioButtonId());
                rbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                rgIntermediate.clearCheck();
                getRandomData();


                btnOkay.setEnabled(false);


                break;




        }
    }


    public void isRight() {

        RadioButton rbtn = getActivity().findViewById(rgIntermediate.getCheckedRadioButtonId());
        rbtn.setCompoundDrawablePadding(20);


        if (rbtn.getText().toString().equals(rigthAnswer)) {

            rbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_right), null);

            score++;
            totalScore++;

            setResult();

        } else
            rbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_wrong), null);


        if (score == 7) {
            diamond = 1;
            totalDiamond++;
            setResult();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void resetColor() {
        rdButton1.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
        rdButton2.setButtonTintList(ColorStateList.valueOf(Color.GRAY));

    }


    //need to test which one is right answer
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showRigthAnswer() {

        if (rigthAnswer.equals(rdButton1.getText().toString())) {
            rdButton1.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.rightCheckedColor)));
            rdButton2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));

        } else if (rigthAnswer.equals(rdButton2.getText().toString())) {
            rdButton1.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));
            rdButton2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.rightCheckedColor)));


        }

        rdButton1.setEnabled(false);
        rdButton2.setEnabled(false);

        btnFree.setEnabled(false);

    }
}

