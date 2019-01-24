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
import com.team12.warofwords.Data.normal1;
import com.team12.warofwords.FinishActivity;
import com.team12.warofwords.R;

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


public class normal_fragment1 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.tScore)
    TextView tScore;

    @BindView(R.id.tDiamond)
    TextView tDiamond;

    @BindView(R.id.nQuestion)
    TextView tQuestion;

    @BindView(R.id.rgNormal)
    RadioGroup rgNormal;

    @BindView(R.id.nRbtn1)
    RadioButton rdButton1;

    @BindView(R.id.nRbtn2)
    RadioButton rdButton2;

    @BindView(R.id.nRbtn3)
    RadioButton rdButton3;

    @BindView(R.id.nbtnFree)
    Button btnFree;

    @BindView(R.id.nbtnOkay)
    Button btnOkay;

    private com.team12.warofwords.entity.Normal Normal = AppDatabase.getAppDatabase(getContext()).normalDAO().findByID(1);
    private int totalScore = Normal.getScores();
    private int totalDiamond = Normal.getDiamonds();
    private int totalGameCount = Normal.getGameCount();

    private int[] gameCountList = new int[]{R.id.v1, R.id.v2, R.id.v3, R.id.v4, R.id.v5};
    private List<normal1> normalList = new ArrayList<>();
    private static List<Integer> quesCount = null;
    private List<Integer> ansCount = new ArrayList<>();

    private int diamond = 0, score = 0, gameCount = 0;
    private String[] Answers;
    private String rigthAnswer;


    public normal_fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_normal1, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getActivity());


        resetGame();
        setResult();
        prepareNormalData();
        getRandomData();

        rgNormal.setOnCheckedChangeListener(this);
        btnOkay.setOnClickListener(this);
        btnFree.setOnClickListener(this);

        Toast.makeText(getActivity().getApplicationContext(), "Level " +(Normal.getGameCount()+1), Toast.LENGTH_SHORT).show();
    }

    public void resetGame() {

        if (totalGameCount == 5) {
            Normal.setScores(0);
            Normal.setDiamonds(1);
            Normal.setGameCount(0);

            totalScore = Normal.getScores();
            totalDiamond = Normal.getDiamonds();
            totalGameCount = Normal.getGameCount();
            AppDatabase.getAppDatabase(getActivity()).normalDAO().update(Normal);

        }
        if (totalGameCount == 0) {
            quesCount = new ArrayList<>();
            Toast.makeText(getActivity().getApplicationContext(), "Start mission "+Normal.getLevelID(), Toast.LENGTH_SHORT).show();
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

                normal1 normal = new normal1(objJSON.getString("questions"), objJSON.getString("answer"), objJSON.getString("ranswer"));
                normalList.add(normal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String loadJSONFromAsset() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getActivity().getAssets().open("normal1.json");
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


        normal1 normal1 = normalList.get(getRandom(quesCount, normalList.size()));

        tQuestion.setText(normal1.getQuestion());
        rigthAnswer = MDetect.INSTANCE.getText(normal1.getRightAnswer());

        Answers = normal1.getAnswers().split(",");
        ansCount.clear();


        for (int i = 0; i < Answers.length; i++) {
            getRandom(ansCount, Answers.length);
        }

        rdButton1.setText(MDetect.INSTANCE.getText(Answers[ansCount.get(0)]));
        rdButton2.setText(MDetect.INSTANCE.getText(Answers[ansCount.get(1)]));
        rdButton3.setText(MDetect.INSTANCE.getText(Answers[ansCount.get(2)]));

        rdButton1.setEnabled(true);
        rdButton2.setEnabled(true);
        rdButton3.setEnabled(true);


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


        resetColor();
        if (btnOkay.getText().toString().equals("OKAY")) {

            RadioButton rbtn = getActivity().findViewById(checkedId);
            rbtn.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.checkedColor)));
        }
        btnOkay.setEnabled(true);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nbtnFree:
                totalDiamond--;
                setResult();

                if (totalDiamond <= 0)
                    btnFree.setEnabled(false);

                rgNormal.check(getRightAnswer());


                break;
            case R.id.nbtnOkay:
                doOkay();
                break;
        }
    }

    public int getRightAnswer() {

        rdButton1.setEnabled(false);
        rdButton2.setEnabled(false);
        rdButton3.setEnabled(false);

        if (rigthAnswer.equals(rdButton1.getText().toString()))
            return R.id.nRbtn1;
        else if (rigthAnswer.equals(rdButton2.getText().toString()))
            return R.id.nRbtn2;
        else return R.id.nRbtn3;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void doOkay() {
        switch (btnOkay.getText().toString()) {
            case "FINISH":

                Normal.setDiamonds(totalDiamond);
                Normal.setScores(totalScore);
                Normal.setGameCount(totalGameCount + 1);

                AppDatabase.getAppDatabase(getContext()).normalDAO().update(Normal);

                Intent i = new Intent(getActivity(), FinishActivity.class);
                i.putExtra("levelID", Normal.getLevelID());
                i.putExtra("isNormal", true);
                i.putExtra("score", score);
                i.putExtra("diamond", diamond);

                getActivity().finish();
                startActivity(i);

                break;

            case "NEXT":

                RadioButton rbtn = getActivity().findViewById(rgNormal.getCheckedRadioButtonId());
                rbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                rgNormal.clearCheck();
                getRandomData();


                btnOkay.setEnabled(false);
                btnOkay.setText("OKAY");


                break;

            case "OKAY":

                isRight();
                showRigthAnswer();

                if (gameCount < 4) {

                    btnOkay.setText("NEXT");
                    gameCount++;
                } else {
                    gameCount = 0;
                    btnOkay.setText("FINISH");
                }
                break;


        }
    }


    public void isRight() {

        RadioButton rbtn = getActivity().findViewById(rgNormal.getCheckedRadioButtonId());
        rbtn.setCompoundDrawablePadding(20);


        if (rbtn.getText().toString().equals(rigthAnswer)) {

            rbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_right), null);

            score++;
            totalScore++;

            setResult();

        } else
            rbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_wrong), null);


        if (score == 3) {
            diamond = 1;
            totalDiamond++;
            setResult();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void resetColor() {
        rdButton1.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
        rdButton2.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
        rdButton3.setButtonTintList(ColorStateList.valueOf(Color.GRAY));

    }


    //need to test which one is right answer
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showRigthAnswer() {

        if (rigthAnswer.equals(rdButton1.getText().toString())) {
            rdButton1.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.rightCheckedColor)));
            rdButton2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));
            rdButton3.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));

        } else if (rigthAnswer.equals(rdButton2.getText().toString())) {
            rdButton1.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));
            rdButton2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.rightCheckedColor)));
            rdButton3.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));

        } else if ((rigthAnswer.equals(rdButton3.getText().toString()))) {
            rdButton1.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));
            rdButton2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.wrongCheckedColor)));
            rdButton3.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.rightCheckedColor)));

        }

        rdButton1.setEnabled(false);
        rdButton2.setEnabled(false);
        rdButton3.setEnabled(false);
        btnFree.setEnabled(false);

    }

}

