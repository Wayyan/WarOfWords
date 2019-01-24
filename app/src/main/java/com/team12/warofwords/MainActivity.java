package com.team12.warofwords;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.evolve.backdroplibrary.BackdropContainer;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.team12.warofwords.Adapter.MyRecyclerAdapter;
import com.team12.warofwords.MyVibrator.MyVibrator;
import com.team12.warofwords.SharePreferences.SharePreferenceStorage;
import com.team12.warofwords.Speaker.Speaker;
import com.team12.warofwords.model.WordStoreModel;
import com.team12.warofwords.word.checking.CheckIsValidWord;
import com.team12.warofwords.word.checking.QuaryingData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String aiString = "", aiMmString, userString, userMmString;
    private RecyclerView recycler;

    private MyRecyclerAdapter mAdapter;
    private boolean isLeft;
    private EditText edInput;
    private ImageButton btnAttempt;
    private CheckIsValidWord checkIsValidWord;
    private QuaryingData quaryingData;
    private TextView txtTime, txtHighestScore, txtCurrentScore;
    private BackdropContainer mContainer;
    private RelativeLayout rlSingle, rlDual;

    private CountDownTimer mTimer;
    private MyVibrator myVibrator;

    private int time = 150;
    private int mark = 0;
    private int maxScore, currentScore;
    private SharePreferenceStorage sharePreferenceStorage;
    //private TextView tvL1, tvL2, tvR1, tvR2;
    private boolean isHard, isEasy, isChildren;
    private List<String> prepareWords = new ArrayList<>();
    private List<Integer> usedIndex = new ArrayList<>();
    public List<WordStoreModel> wordStoreList = new ArrayList<>();
    private boolean exit;

    Speaker speaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.single_tool_bar);

        Intent i = getIntent();
        // isHard = i.getBooleanExtra("Hard", true);
        int mode = i.getIntExtra("Mode", 1);
        if (mode == 1) {
            toolbar.setTitle("Hard");
            toolbar.setTitleTextColor(Color.WHITE);
            isHard = true;
        } else if (mode == 0) {
            toolbar.setTitle("Normal");
            toolbar.setTitleTextColor(Color.WHITE);
            isEasy = true;
        } else if (mode == 2) {
            toolbar.setTitle("Easy");
            toolbar.setTitleTextColor(Color.WHITE);
            isChildren = true;
        }
        setSupportActionBar(toolbar);
        mContainer = findViewById(R.id.single_backdrop_container);
        mContainer.attachToolbar(toolbar)
                .dropInterpolator(new LinearInterpolator())
                .dropHeight(getResources().getDimensionPixelSize(R.dimen.backdrop_playground))
                .build();

        rlSingle = findViewById(R.id.rl_single);
        rlDual = findViewById(R.id.rl_dual);

        rlDual.setVisibility(View.GONE);
        rlSingle.setVisibility(View.VISIBLE);
        sharePreferenceStorage = new SharePreferenceStorage(this);
        if (isEasy)
            maxScore = sharePreferenceStorage.getHighestScore();
        else if (isHard)
            maxScore = sharePreferenceStorage.getHighestHard();
        else if (isChildren)
            maxScore = sharePreferenceStorage.getHighestKid();

        sharePreferenceStorage.setPartaner(sharePreferenceStorage.getProfilePicture());

        if (isChildren) {
            String getWordString = i.getStringExtra("Data");
            //   Log.d("kids", getWordString);
            String[] chapter = getWordString.split("&&");
            for (String s : chapter) {
                //  Log.d("kids", s);
                for (String st : s.split("//")) {
                    // Log.d("kids", st);
                    prepareWords.add(st);
                }
            }
            // Log.d("kids5", prepareWords.size() + "");
        }

        speaker = new Speaker(this);
        myVibrator=new MyVibrator(this);

        checkIsValidWord = new CheckIsValidWord(MainActivity.this);
        checkIsValidWord.deleteWords();

        recycler = findViewById(R.id.recycler);
        txtTime = findViewById(R.id.tv_time);
        txtTime.setText("150");
        txtHighestScore = findViewById(R.id.tv_highest_score);
        txtCurrentScore = findViewById(R.id.tv_current_score);

        txtHighestScore.setText(maxScore + "");

        mAdapter = new MyRecyclerAdapter(this, wordStoreList);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(mAdapter);


        quaryingData = new QuaryingData(MainActivity.this, true);
        edInput = findViewById(R.id.ed_message);
        btnAttempt = findViewById(R.id.btn_attempt);

        Timers();

        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
             if(charSequence.charAt(charSequence.length()-1)=='\'')
                {
                    myVibrator.startVibrate(2);
                    Toast.makeText(getApplicationContext(),"This Character Is Restricted!!",Toast.LENGTH_SHORT).show();
                    edInput.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = edInput.getText() + "";
                if (!userString.equals("")) {
                    if (checkIsValidWord.startIsEnd(aiString, userString)) {
                        //Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
                        if (checkIsValidWord.checkUserEnglishWord(edInput.getText().toString())) {
                            // tvR1.setText(edInput.getText());
                            userMmString = quaryingData.normalQuarying(userString);
                            WordStoreModel item = new WordStoreModel(userString, false, userMmString);
                            wordStoreList.add(item);
                            mAdapter.notifyDataSetChanged();
                            recycler.scrollToPosition(mAdapter.getItemCount() - 1);
                            edInput.setText("");
                            // speaker.speak(userString);
                            do {
                                if (isHard) {
                                    String temp = quaryingData.getHardRandomWord(userString.charAt(userString.length() - 1));
                                    aiString = temp.split("//")[0];
                                    aiMmString = temp.split("//")[1];
                                } else if (isEasy) {
                                    String temp = quaryingData.getRandomWord(userString.charAt(userString.length() - 1));
                                    aiString = temp.split("//")[0];
                                    aiMmString = temp.split("//")[1];
                                    Log.d("myanmar", aiMmString);
                                } else {
                                    if (prepareWords.size() > usedIndex.size()) {
                                        String temp = getChildWord(userString.toString());
                                        aiString = temp.split("//")[0];
                                        aiMmString = temp.split("//")[1];
                                    } else {
                                        showCompleteDialog();
                                        mTimer.cancel();
                                    }
                                }
                            } while (!checkIsValidWord.checkAiEnglishWord(aiString));

                            mark += 20;
                            txtCurrentScore.setText(mark + "");
                            new CountDownTimer(1000, 500) {
                                @Override
                                public void onTick(long l) {
                                    btnAttempt.setClickable(false);
                                }

                                @Override
                                public void onFinish() {
                                    btnAttempt.setClickable(true);
                                    WordStoreModel item1 = new WordStoreModel(aiString, true, aiMmString);
                                    wordStoreList.add(item1);
                                    mAdapter.notifyDataSetChanged();
                                    speaker.speak(aiString);
                                    recycler.scrollToPosition(mAdapter.getItemCount() - 1);
                                    edInput.setText("");
                                }
                            }.start();

                        }
                        else {
                            myVibrator.startVibrate(2);
                        }
                    }
                    else {
                        myVibrator.startVibrate(2);
                        Toast.makeText(getApplicationContext(),"Your Start Character Must Be Enemy's End Character..",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void Timers() {

        mTimer = new CountDownTimer(150000, 1000) {
            @Override
            public void onTick(long l) {
                txtTime.setText((l / 1000) + "");
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), mark + "", Toast.LENGTH_SHORT).show();
                btnAttempt.setClickable(false);
                showCompleteDialog();
                if (maxScore < mark) {
                    if (isEasy)
                        sharePreferenceStorage.setHighestScore(mark);
                    else if (isHard)
                        sharePreferenceStorage.setHighestHard(mark);
                    else if (isChildren)
                        sharePreferenceStorage.setHighestKid(mark);
                }
                if (sharePreferenceStorage.getSingleMatch() < 1001) {
                    sharePreferenceStorage.setSingleMatch(sharePreferenceStorage.getSingleMatch() + 1);
                }
            }
        };

        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                while (true) {
                    if (QuaryingData.Z != null) {
                        if (isHard) {
                            String temp = quaryingData.getHardRandomWord('a');
                            aiString = temp.split("//")[0];
                            aiMmString = temp.split("//")[1];
                        } else if (isEasy) {
                            String temp = quaryingData.getRandomWord('a');
                            aiString = temp.split("//")[0];
                            aiMmString = temp.split("//")[1];
                        } else {
                            String temp = getChildWord("a");
                            aiString = temp.split("//")[0];
                            aiMmString = temp.split("//")[1];
                        }
                        speaker.speak(aiString);
                        WordStoreModel item = new WordStoreModel(aiString, true, aiMmString);
                        wordStoreList.add(item);
                        mAdapter.notifyDataSetChanged();

                        mTimer.start();
                        break;
                    }
                }
            }
        }.start();

    }

    private void exitTimer() {
        new CountDownTimer(2500, 500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                exit = false;
            }
        }.start();
    }

    private void showCompleteDialog() {
        final Dialog completeDialog = new Dialog(this);
        completeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        completeDialog.setContentView(R.layout.dialog_complete_single_player);
        //  completeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        completeDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        completeDialog.setCancelable(false);
        completeDialog.show();
        completeDialog.findViewById(R.id.btn_review_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeDialog.dismiss();
                recycler.scrollToPosition(0);
            }
        });
        completeDialog.findViewById(R.id.close_complete_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeDialog.dismiss();
            }
        });
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(completeDialog.findViewById(R.id.circle_single_crown));
        TextView score = completeDialog.findViewById(R.id.complete_score);
        score.setText(mark + "");
        TextView con = completeDialog.findViewById(R.id.single_tv_congratulation);
        con.setText("Good Job " + sharePreferenceStorage.getUserName() + "...");
    }

    private String getChildWord(String userWord) {
        int randomIndex;
        String word = "";
        while (true) {
            {
                int max = prepareWords.size();
                Log.d("Kids4", max + "");
                randomIndex = (int) (Math.random() * (max));
                Log.d("kids3", randomIndex + "");
                if (!usedIndex.contains(randomIndex)) {
                    usedIndex.add(randomIndex);
                    return quaryingData.getDesiredWords(userWord, prepareWords.get(randomIndex));
                }
            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        speaker.destroySpeaker();
        checkIsValidWord.deleteWords();
        checkIsValidWord.closeRealm();
    }

    @Override
    public void onBackPressed() {
        if (exit)
            super.onBackPressed();
        else {
            Toast.makeText(getApplicationContext(), "Press Again To Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            exitTimer();
        }
    }
}
