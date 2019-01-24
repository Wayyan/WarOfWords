package com.team12.warofwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team12.warofwords.DB.AppDatabase;
import com.team12.warofwords.entity.Intermediate;
import com.team12.warofwords.entity.Normal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishActivity extends AppCompatActivity {


    @BindView(R.id.tResult_Mission)
    TextView tMission;

    @BindView(R.id.tResult_Level)
    TextView tLevel;

    @BindView(R.id.tResult_Question)
    TextView tQuestion;

    @BindView(R.id.tResult_Score)
    TextView tScore;

    @BindView(R.id.tResult_Diamond)
    TextView tDiamond;

    @BindView(R.id.btnNext)
    Button btnNext;

    private int score, diamond, levelID;
    private Boolean isNormal;

    private Normal normal;
    private Intermediate intermediate;


    AlertDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        ButterKnife.bind(this, this);

        Bundle bundle = getIntent().getExtras();
        isNormal = bundle.getBoolean("isNormal");
        levelID = bundle.getInt("levelID");

        score = bundle.getInt("score");
        diamond = bundle.getInt("diamond");

        if (isNormal)
            normal = AppDatabase.getAppDatabase(this).normalDAO().findByID(levelID);
        else intermediate = AppDatabase.getAppDatabase(this).intermediateDAO().findByID(levelID);

        setResult();


    }

    public void setResult() {
        if (isNormal) {
            tMission.setText("Mission " + levelID);
            tLevel.setText("Level " + normal.getGameCount() + " has completed!");
            tQuestion.setText("Total question : " + (normal.getQuestions() / 5));

        } else {
            tMission.setText("Mission " + levelID);
            tLevel.setText("Level " + intermediate.getGameCount() + " has completed!");
            tQuestion.setText("Total question : " + (intermediate.getQuestions() / 5));
        }
        tScore.setText(" : " + score);
        tDiamond.setText(" : " + diamond);
    }

    @OnClick(R.id.btnNext)
    public void goToNext() {

        if (isNormal) {
            switch (normal.getGameCount()) {
                case 5:
                    showAlertDialog();
                    break;
                default:
                    goToNormal();
                    break;
            }
        } else {

            switch (intermediate.getGameCount()) {
                case 5:
                    showAlertDialog();
                    break;
                default:
                    goToIntermediate();
                    break;
            }
        }

    }

    public void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LayoutInflater inflater = this.getLayoutInflater();
        final View alert_box = inflater.inflate(R.layout.alert_box, null);

        builder.setView(alert_box);

        ImageView alert_star1 = alert_box.findViewById(R.id.alert_star1);
        ImageView alert_star2 = alert_box.findViewById(R.id.alert_star2);
        ImageView alert_star3 = alert_box.findViewById(R.id.alert_star3);
        ImageView close = alert_box.findViewById(R.id.close);


        if (isNormal) {

            if (normal.getLevelID() < 3) {
                if (normal.getScores() >= (normal.getQuestions() / 5) * 4) {
                    alert_star1.setImageResource(R.drawable.ic_star_unlock);
                    alert_star2.setImageResource(R.drawable.ic_star_unlock);
                    alert_star3.setImageResource(R.drawable.ic_star_unlock);

                    completeMission(alert_box);


                } else if (normal.getScores() >= normal.getQuestions() / 2) {
                    alert_star1.setImageResource(R.drawable.ic_star_unlock);
                    alert_star2.setImageResource(R.drawable.ic_star_unlock);
                    alert_star3.setImageResource(R.drawable.ic_star_lock);

                    completeMission(alert_box);


                } else if (normal.getScores() >= normal.getQuestions() / 3) {
                    alert_star1.setImageResource(R.drawable.ic_star_unlock);
                    alert_star2.setImageResource(R.drawable.ic_star_lock);
                    alert_star3.setImageResource(R.drawable.ic_star_lock);

                    uncompleteMission(alert_box);

                } else {
                    alert_star1.setImageResource(R.drawable.ic_star_lock);
                    alert_star2.setImageResource(R.drawable.ic_star_lock);
                    alert_star3.setImageResource(R.drawable.ic_star_lock);

                    uncompleteMission(alert_box);
                }
            } else finishMission(alert_box);
        } else {

            if (intermediate.getLevelID() < 3) {
                if (intermediate.getScores() >= (intermediate.getQuestions() / 5) * 4) {
                    alert_star1.setImageResource(R.drawable.ic_star_unlock);
                    alert_star2.setImageResource(R.drawable.ic_star_unlock);
                    alert_star3.setImageResource(R.drawable.ic_star_unlock);

                    completeMission(alert_box);


                } else if (intermediate.getScores() >= intermediate.getQuestions() / 2) {
                    alert_star1.setImageResource(R.drawable.ic_star_unlock);
                    alert_star2.setImageResource(R.drawable.ic_star_unlock);
                    alert_star3.setImageResource(R.drawable.ic_star_lock);

                    completeMission(alert_box);


                } else if (intermediate.getScores() >= intermediate.getQuestions() / 3) {
                    alert_star1.setImageResource(R.drawable.ic_star_unlock);
                    alert_star2.setImageResource(R.drawable.ic_star_lock);
                    alert_star3.setImageResource(R.drawable.ic_star_lock);

                    uncompleteMission(alert_box);

                } else {
                    alert_star1.setImageResource(R.drawable.ic_star_lock);
                    alert_star2.setImageResource(R.drawable.ic_star_lock);
                    alert_star3.setImageResource(R.drawable.ic_star_lock);

                    uncompleteMission(alert_box);
                }
            } else finishMission(alert_box);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNormal) {
                    normal.setGameCount(0);

                    AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().update(normal);
                    dialog.dismiss();

                    if (normal.getScores() >= (normal.getQuestions() / 5) * 4 || normal.getScores() >= normal.getQuestions() / 2)

                    {
                        levelID += 1;

                        Normal nextNormal = AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().findByID(levelID);
                        nextNormal.setOn(true);
                        AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().update(nextNormal);
                    }

                } else {
                    intermediate.setGameCount(0);

                    AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().update(intermediate);
                    dialog.dismiss();

                    if (intermediate.getScores() >= (intermediate.getQuestions() / 5) * 4 || intermediate.getScores() >= intermediate.getQuestions() / 2)

                    {
                        levelID += 1;

                        Intermediate nextIntermediate = AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().findByID(levelID);
                        nextIntermediate.setOn(true);
                        AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().update(nextIntermediate);
                    }


                }
                goToMain();
            }

        });


        dialog = builder.show();

    }

    public void completeMission(View alert_box) {
        TextView alert_text1 = alert_box.findViewById(R.id.alter_text1);
        TextView alert_text2 = alert_box.findViewById(R.id.alter_text2);

        Button restart = alert_box.findViewById(R.id.restart);
        Button replay = alert_box.findViewById(R.id.replay);
        Button okay = alert_box.findViewById(R.id.okay);


        alert_text1.setText("You has completed Mission!");
        alert_text2.setText("Unlock next mission and enjoy with more exciting Level. Press \"Okay\" , otherwise \"Restart\"");

        replay.setVisibility(View.GONE);
        restart.setVisibility(View.VISIBLE);
        okay.setVisibility(View.VISIBLE);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNormal) {
                    normal.setGameCount(0);
                    normal.setDiamonds(1);
                    normal.setScores(0);
                    AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().update(normal);

                    dialog.dismiss();
                    goToNormal();
                } else {
                    intermediate.setGameCount(0);
                    intermediate.setDiamonds(1);
                    intermediate.setScores(0);
                    AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().update(intermediate);


                    dialog.dismiss();
                    goToIntermediate();
                }
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                levelID += 1;

                if (isNormal) {
                    Normal nextNormal = AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().findByID(levelID);
                    nextNormal.setOn(true);

                    AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().update(nextNormal);
                    goToNormal();
                } else {
                    Intermediate nextIntermediate = AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().findByID(levelID);
                    nextIntermediate.setOn(true);

                    AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().update(nextIntermediate);
                    goToIntermediate();
                }
            }
        });


    }

    public void uncompleteMission(View alert_box) {
        TextView alert_text1 = alert_box.findViewById(R.id.alter_text1);
        TextView alert_text2 = alert_box.findViewById(R.id.alter_text2);


        Button replay = alert_box.findViewById(R.id.replay);
        Button restart = alert_box.findViewById(R.id.restart);
        Button okay = alert_box.findViewById(R.id.okay);


        alert_text1.setText("You hasn\'t completed Mission!");
        alert_text2.setText("Can\'t unlock next mission ,Pleas replay current misssion again ,Press \"Replay\" ");

        replay.setVisibility(View.VISIBLE);
        restart.setVisibility(View.GONE);
        okay.setVisibility(View.GONE);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNormal) {
                    normal.setGameCount(0);
                    normal.setDiamonds(1);
                    normal.setScores(0);
                    AppDatabase.getAppDatabase(getApplicationContext()).normalDAO().update(normal);
                    dialog.dismiss();
                    goToNormal();
                } else {
                    intermediate.setGameCount(0);
                    intermediate.setDiamonds(1);
                    intermediate.setScores(0);
                    AppDatabase.getAppDatabase(getApplicationContext()).intermediateDAO().update(intermediate);
                    dialog.dismiss();
                    goToIntermediate();
                }
            }
        });
    }


    public void finishMission(View alert_box) {

        LinearLayout star_layout = alert_box.findViewById(R.id.startLayout);
        star_layout.setVisibility(View.GONE);

        TextView alert_text1 = alert_box.findViewById(R.id.alter_text1);
        TextView alert_text2 = alert_box.findViewById(R.id.alter_text2);


        Button replay = alert_box.findViewById(R.id.replay);
        Button restart = alert_box.findViewById(R.id.restart);
        Button okay = alert_box.findViewById(R.id.okay);
        alert_text1.setText("You has finished all Mission!");
        alert_text2.setText("Congratulate, please download next missions to enjoy more.");


        replay.setVisibility(View.GONE);
        restart.setVisibility(View.GONE);
        okay.setVisibility(View.VISIBLE);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

    }

    public void goToNormal() {
        Intent i = new Intent(this, AlterActivity.class);
        i.putExtra("isNormal", true);
        i.putExtra("levelID", levelID);
        finish();
        startActivity(i);
    }

    public void goToIntermediate() {
        Intent i = new Intent(this, AlterActivity.class);
        i.putExtra("isNormal", false);
        i.putExtra("levelID", levelID);
        finish();
        startActivity(i);
    }

    public void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }

}