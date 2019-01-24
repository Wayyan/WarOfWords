package com.team12.warofwords;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.evolve.backdroplibrary.BackdropContainer;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.marcoscg.easylicensesdialog.EasyLicensesDialogCompat;
import com.team12.warofwords.SharePreferences.SharePreferenceStorage;
import com.team12.warofwords.model.FireDB;
import com.team12.warofwords.word.checking.QuaryingData;

import de.hdodenhof.circleimageview.CircleImageView;

public class InitActivity extends AppCompatActivity {
    //private RelativeLayout rlBtnWow,rlBtnSix,rlBtnMeaning,rlBtnKids;
    private ImageView leftImage, rightImage, profileImage;
    private TextView leftTxt, rightTxt, profileName, txtTitle, txtPercentage;
    private MaterialRippleLayout rplSingle, rplDual, rplChallenge;
    private ArcProgress progressGraduated, progressGodPlayer, progressGodKiller;
    private Dialog loadingDialog;

    private BottomSheetBehavior mBottomSheetBehavior;
    private QuaryingData quaryingData, quaryingDataUpdate;
    private BackdropContainer mBackDropContainer;
    private Dialog loginDialog;
    private SharePreferenceStorage mSharePreference;
    private DatabaseReference patchData, wordData;

    private String currentPatch;
    private String updateString;
    private int clickedButton;
    private boolean exit, login;
    private CountDownTimer mTimer;
    int currentImage;
    private int percent = 1;
    //  private String[] updateWordsArray;
    private int[] avatarIcon = {R.drawable.user_lite, R.drawable.iron_man_avatar, R.drawable.spider_man_avatar, R.drawable.hulk_avatar, R.drawable.ant_man, R.drawable.dontknow_avata};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        final Toolbar toolbar = findViewById(R.id.init_tool_bar);
        toolbar.setTitle("War Of Words");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        mBackDropContainer = findViewById(R.id.mbackdropcontainer);
        mBackDropContainer.attachToolbar(toolbar)
                .dropInterpolator(new LinearInterpolator())
                .dropHeight(getResources().getDimensionPixelSize(R.dimen.backdrop_height))
                .build();

        mSharePreference = new SharePreferenceStorage(this);

        login = mSharePreference.getIsLogin();

        leftImage = findViewById(R.id.img_left_sheet);
        rightImage = findViewById(R.id.img_right_sheet);
        profileImage = findViewById(R.id.img_profile);
        leftTxt = findViewById(R.id.tv_left_sheet);
        rightTxt = findViewById(R.id.tv_right_sheet);
        profileName = findViewById(R.id.tv_profile_name);
        txtTitle = findViewById(R.id.tv_title);
        progressGraduated = findViewById(R.id.graduated_arc_progress);
        progressGodPlayer = findViewById(R.id.godplayer_arc_progress);
        progressGodKiller = findViewById(R.id.godkiller_arc_progress);
        rplSingle = findViewById(R.id.rpl_single_score);
        rplDual = findViewById(R.id.rpl_dual_score);
        rplChallenge = findViewById(R.id.rpl_challenge_score);
        scoreCardsListener();

        progressGraduated.setMax(100);
        progressGodPlayer.setMax(100);
        progressGodKiller.setMax(100);
        profileName.setText(mSharePreference.getUserName());
        txtTitle.setText(mSharePreference.getTitle());
        profileImage.setImageResource(avatarIcon[mSharePreference.getProfilePicture()]);


        progressGraduated.setProgress(mSharePreference.getSingleMatch() / 10);

        currentPatch = mSharePreference.getUpdateLevel() + "";
        showLoadingDialog();
        quaryingDataUpdate = new QuaryingData(this);
        // quaryingDataUpdate.insertWord("small", "(adj) blah blah");

        findViewById(R.id.rpl_check_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForUpdate();
                // showLoadingDialog();
            }
        });
        findViewById(R.id.rpl_send_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent _Intent = new Intent(Intent.ACTION_SEND);
                _Intent.setType("plain/text");
                _Intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"warofwords.wow.application@gmail.com"});
                _Intent.putExtra(Intent.EXTRA_SUBJECT, "War Of Words Version" + getResources().getString(R.string.version));
                _Intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(_Intent, "Feedback"));
            }
        });
        findViewById(R.id.rpl_edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
        findViewById(R.id.rpl_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAboutDialog();
            }
        });
        findViewById(R.id.rpl_acknowledgement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EasyLicensesDialogCompat(InitActivity.this)
                        .setTitle("Credit")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
        findViewById(R.id.rpl_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getOpenFacebookIntent(InitActivity.this, "fb://profile/100008567746225", "https://www.facebook.com/wayyan"));
            }
        });


        requestPermission();

        final Thread thread = new Thread(new MyThread());
        thread.start();

        View bottomSheet = findViewById(R.id.bottom_sheet_choose);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    findViewById(R.id.dim).setVisibility(View.GONE);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    findViewById(R.id.init_ln_back).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                    // toolbar.setAlpha((float)1);
                }
            }


            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                findViewById(R.id.dim).setVisibility(View.VISIBLE);
                findViewById(R.id.dim).setAlpha(slideOffset);
                // toolbar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorDimPrimary));
                findViewById(R.id.init_ln_back).setBackgroundColor(getResources().getColor(R.color.colorDimPrimary));
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                //  toolbar.setAlpha(slideOffset);
            }
        });

        mBottomSheetBehavior.setPeekHeight(0);
        activityInBottomSheet();


        findViewById(R.id.dim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closebottomSheet();
            }
        });
        findViewById(R.id.rpl_btn_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (QuaryingData.Z != null) {
                    clickedButton = 1;
                    leftImage.setImageResource(R.drawable.normal_blue);
                    rightImage.setImageResource(R.drawable.normal_red);
                    leftTxt.setText("Normal");
                    rightTxt.setTextColor(getResources().getColor(R.color.colorAttractive));
                    rightTxt.setText("Hard");
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else
                    Toast.makeText(getApplicationContext(), "Preparing please wait", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.rpl_btn_dual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (QuaryingData.Z != null) {
                    clickedButton = 2;
                    leftImage.setImageResource(R.drawable.dual_blue);
                    rightImage.setImageResource(R.drawable.dual_red);
                    leftTxt.setText("Simple Dual");
                    rightTxt.setTextColor(getResources().getColor(R.color.colorAttractive));
                    rightTxt.setText("Challenge");
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else
                    Toast.makeText(getApplicationContext(), "Preparing please wait", Toast.LENGTH_SHORT).show();
            }
        });


//        findViewById(R.id.rl_btn_meaning_book).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //  Log.d("hard","Clicked");
//                startActivity((new Intent(InitActivity.this, MainActivity.class).putExtra("Mode", 1)));
//                //finish();
//            }
//        });
        findViewById(R.id.rpl_btn_kid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (QuaryingData.Z != null) {
                    clickedButton = 3;
                    leftImage.setImageResource(R.drawable.child_icon);
                    rightImage.setImageResource(R.drawable.kids_icon);
                    leftTxt.setText("For Childs");
                    rightTxt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rightTxt.setText("Kids' Puzzle");
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else
                    Toast.makeText(getApplicationContext(), "Preparing please wait", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closebottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public static Intent getOpenFacebookIntent(Context context, String id, String link) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(id));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        }
    }

    private void activityInBottomSheet() {


        findViewById(R.id.rpl_btn_single_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedButton == 1) {
                    startActivity((new Intent(InitActivity.this, MainActivity.class).putExtra("Mode", 0)));
                } else if (clickedButton == 2) {
                    startActivity(new Intent(InitActivity.this, TwoPlayerActivity.class).putExtra("mode", "two_player"));
                } else if (clickedButton == 3) {
                    startActivity(new Intent(InitActivity.this, WowForChildren.class));
                }
                closebottomSheet();
                // finish();
            }
        });
        findViewById(R.id.rpl_btn_single_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedButton == 1) {
                    startActivity((new Intent(InitActivity.this, MainActivity.class).putExtra("Mode", 1)));
                } else if (clickedButton == 2) {
                    startActivity((new Intent(InitActivity.this, TwoPlayerActivity.class).putExtra("mode", "six_key")));
                } else if (clickedButton == 3) {
                    startActivity(new Intent(InitActivity.this, KidPuzzleActivity.class));
                }
                closebottomSheet();
                // finish();
            }
        });

//        findViewById(R.id.cd_btn_multi).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(InitActivity.this, TwoPlayerActivity.class).putExtra("mode", "two_player"));
//                closebottomSheet();
//                //finish();
//            }
//        });
    }

    public void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.alertdialog_about, null);
        alertDialog.setView(view);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;


        alertDialog.show();
    }

    private void showLoginDialog() {
        final CircleImageView imgMain, img1, img2, img3, img4, img5, img6;
        final EditText edUsername;
        Button btnSubmit, cancelLogin;
        currentImage = mSharePreference.getProfilePicture();
        loginDialog = new Dialog(this);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.setContentView(R.layout.log_in_dialog);
        loginDialog.setCancelable(false);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        imgMain = loginDialog.findViewById(R.id.imgv_temp_profile);
        img1 = loginDialog.findViewById(R.id.avatar1);
        img2 = loginDialog.findViewById(R.id.avatar2);
        img3 = loginDialog.findViewById(R.id.avatar3);
        img4 = loginDialog.findViewById(R.id.avatar4);
        img5 = loginDialog.findViewById(R.id.avatar5);
        img6 = loginDialog.findViewById(R.id.avatar6);
        edUsername = loginDialog.findViewById(R.id.ed_username);
        btnSubmit = loginDialog.findViewById(R.id.btn_submit_profile);
        cancelLogin = loginDialog.findViewById(R.id.btn_close_login);

        imgMain.setImageResource(avatarIcon[currentImage]);

        cancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSharePreference.getIsLogin()) {
                    loginDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Fill The Blanks", Toast.LENGTH_SHORT).show();
                    edUsername.setError("Fill It...");
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edUsername.getText().toString().equals("")) {
                    edUsername.setError("Fill It");
                } else {
                    mSharePreference.setUserName(edUsername.getText().toString());
                    mSharePreference.setProfilePicture(currentImage);
                    mSharePreference.setIsLogIn(true);
                    profileImage.setImageResource(avatarIcon[currentImage]);
                    profileName.setText(mSharePreference.getUserName());
                    loginDialog.dismiss();
                }
            }
        });

        edUsername.setText(mSharePreference.getUserName());

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImage = 0;
                imgMain.setImageResource(avatarIcon[currentImage]);
                img1.setBorderColor(getResources().getColor(R.color.colorAttractive));
                img2.setBorderColor(Color.WHITE);
                img3.setBorderColor(Color.WHITE);
                img4.setBorderColor(Color.WHITE);
                img5.setBorderColor(Color.WHITE);
                img6.setBorderColor(Color.WHITE);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImage = 1;
                imgMain.setImageResource(avatarIcon[currentImage]);
                img2.setBorderColor(getResources().getColor(R.color.colorAttractive));
                img1.setBorderColor(Color.WHITE);
                img3.setBorderColor(Color.WHITE);
                img4.setBorderColor(Color.WHITE);
                img5.setBorderColor(Color.WHITE);
                img6.setBorderColor(Color.WHITE);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImage = 2;
                imgMain.setImageResource(avatarIcon[currentImage]);
                img3.setBorderColor(getResources().getColor(R.color.colorAttractive));
                img2.setBorderColor(Color.WHITE);
                img1.setBorderColor(Color.WHITE);
                img4.setBorderColor(Color.WHITE);
                img5.setBorderColor(Color.WHITE);
                img6.setBorderColor(Color.WHITE);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImage = 3;
                imgMain.setImageResource(avatarIcon[currentImage]);
                img4.setBorderColor(getResources().getColor(R.color.colorAttractive));
                img2.setBorderColor(Color.WHITE);
                img3.setBorderColor(Color.WHITE);
                img1.setBorderColor(Color.WHITE);
                img5.setBorderColor(Color.WHITE);
                img6.setBorderColor(Color.WHITE);
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImage = 4;
                imgMain.setImageResource(avatarIcon[currentImage]);
                img5.setBorderColor(getResources().getColor(R.color.colorAttractive));
                img2.setBorderColor(Color.WHITE);
                img3.setBorderColor(Color.WHITE);
                img4.setBorderColor(Color.WHITE);
                img1.setBorderColor(Color.WHITE);
                img6.setBorderColor(Color.WHITE);
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImage = 5;
                imgMain.setImageResource(avatarIcon[currentImage]);
                img6.setBorderColor(getResources().getColor(R.color.colorAttractive));
                img2.setBorderColor(Color.WHITE);
                img3.setBorderColor(Color.WHITE);
                img4.setBorderColor(Color.WHITE);
                img5.setBorderColor(Color.WHITE);
                img1.setBorderColor(Color.WHITE);
            }
        });
        loginDialog.show();
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


    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        } else {
            //  getScanningResults();
            //do something, permission was previously granted; or legacy device
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

        }

    }

    public void checkForUpdate() {
        FireDB.getFirebaseDB().child("PATCH").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("message", snapshot.getValue().toString());
                    currentPatch = snapshot.getValue().toString();
                    if (!mSharePreference.getUpdateLevel().equals(currentPatch)) {
                        mSharePreference.setUpdateLavel(currentPatch);
//                       quaryingData.deleteFromRealm();
                        doUpdate();
                    } else
                        Toast.makeText(getApplicationContext(), "Databases are alreeady updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.d("debugg", mSharePreference.getUpdateLevel() + "    " + currentPatch);


    }

    private void scoreCardsListener() {
        rplSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempScore = mSharePreference.getSingleMatch();
                if (tempScore > 999) {
                    txtTitle.setText("(Graduated)");
                    mSharePreference.setTitle("(Graduated)");
                } else {
                    Toast.makeText(getApplicationContext(), "Play 1000 Single Matches To Get This Title.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rplDual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempScore = mSharePreference.getDualMatch();
                if (tempScore > 999) {
                    txtTitle.setText("(God Player)");
                    mSharePreference.setTitle("(God Player)");
                } else {
                    Toast.makeText(getApplicationContext(), "Win 1000 Dual Matches To Get This Title.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rplChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempScore = mSharePreference.getChallengeMatch();
                if (tempScore > 999) {
                    txtTitle.setText("(God Killer)");
                    mSharePreference.setTitle("(God Killer)");
                } else {
                    Toast.makeText(getApplicationContext(), "Win 1000 Challenge Matches To Get This Title.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showLoadingDialog() {
        loadingDialog = new Dialog(this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        txtPercentage = loadingDialog.findViewById(R.id.tv_percentage);
        mTimer = new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long l) {
                if (percent < 98) {
                    percent += 2;
                }
                if (QuaryingData.Z != null) {
                    loadingDialog.dismiss();
                    mTimer.cancel();
                    if (login) {

                    } else
                        showLoginDialog();
                }
                // setPercentage();
                txtPercentage.setText(percent + "% Completed...");
            }

            @Override
            public void onFinish() {
                loadingDialog.dismiss();
            }
        };
        mTimer.start();
    }


    public void doUpdate() {
        Toast.makeText(getApplicationContext(), "Updating", Toast.LENGTH_SHORT).show();
        FireDB.getFirebaseDB().child("WORDS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Log.d("second",snapshot.getValue().toString());
                    updateString = snapshot.getValue().toString();
                    String[] updateWordsArray = updateString.split("&&");
                    Log.d("second", updateWordsArray.length + "");

                    for (int i = 0; i < updateWordsArray.length; i++) {
                        quaryingDataUpdate.insertWord(updateWordsArray[i].split("//")[0], updateWordsArray[i].split("//")[1]);
                        mSharePreference.setAppTime(0);
                    }
                    quaryingData = null;
                    // quaryingData=new QuaryingData(InitActivity.this,true);
                    Thread t = new Thread(new MyThread());
                    t.start();
                    showLoadingDialog();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            closebottomSheet();
        } else if (exit)
            super.onBackPressed();
        else {
            Toast.makeText(getApplicationContext(), "Press Again To Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            exitTimer();
        }
    }

    public class MyThread implements Runnable {

        @Override
        public void run() {
            quaryingData = new QuaryingData(getApplicationContext(), true);
//            showLoadingDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharePreference.getSingleMatch() < 1001)
            progressGraduated.setProgress(mSharePreference.getSingleMatch() / 10);
        if (mSharePreference.getDualMatch() < 1001)
            progressGodPlayer.setProgress(mSharePreference.getDualMatch() / 10);
        if (mSharePreference.getChallengeMatch() < 1001)
            progressGodKiller.setProgress(mSharePreference.getChallengeMatch() / 10);
        //setProgressToArcs();
    }
}
