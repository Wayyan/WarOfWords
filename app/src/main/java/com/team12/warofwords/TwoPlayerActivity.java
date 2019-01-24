package com.team12.warofwords;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.evolve.backdroplibrary.BackdropContainer;
import com.team12.warofwords.Adapter.MyRecyclerAdapter;
import com.team12.warofwords.MyVibrator.MyVibrator;
import com.team12.warofwords.SharePreferences.SharePreferenceStorage;
import com.team12.warofwords.Speaker.Speaker;
import com.team12.warofwords.broadcast_receiver.WifiDirectBroadcastReceiver;
import com.team12.warofwords.model.WordStoreModel;
import com.team12.warofwords.word.checking.CheckIsValidWord;
import com.team12.warofwords.word.checking.CheckUsedWord;
import com.team12.warofwords.word.checking.QuaryingData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Way yan on 10/24/2018.
 */

public class TwoPlayerActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {
    private Toolbar toolbar;
    private BackdropContainer mBackDrop;
    private RelativeLayout rlSingle, rlDual;
    private Button btnSearch;
    private ImageButton btnAttempt;
    private ListView listDevice;
    public TextView txtNoDevice;
    private View viewInnerCircle1;
    private TextView txtEnemyTime;

    private Speaker mSpeaker;
    private MyVibrator mVibrator;

    public EditText edMessage;
    private TextView txtTime;
    public RecyclerView mRecyclerView;
    private TextView txtServerText;

    private Button sbtnA, sbtnE, sbtnI, sbtnO, sbtnU, sbtn1, sbtn2, sbtn3, sbtn4, sbtn5, sbtn6;
    private ImageButton sbtnBackSpace, sbtnAttempt;
    private TextView stxtEdit;
    private TextView stxtTimeLeft;

    private Button sbtnQ, sbtnW, sbtnR, sbtnT, sbtnY, sbtnP, sbtnS, sbtnD, sbtnF, sbtnG, sbtnH, sbtnJ, sbtnK, sbtnL, sbtnZ, sbtnX, sbtnC, sbtnV, sbtnB, sbtnN, sbtnM;
    private TextView stxtWarning;

    public Dialog showDevicesDialog;
    public Dialog dialog;
    private CountDownTimer mTimer;

    private WifiManager wifiManager;

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;

    private BroadcastReceiver mReceiver;
    private final IntentFilter mIntentFilter = new IntentFilter();

    private List<WifiP2pDevice> peerList = new ArrayList<WifiP2pDevice>();
    private String[] deviceNames;
    private WifiP2pDevice[] devices;

    private ServerClass serverClass;
    private ClientClass clientClass;
    private SendReceive sendReceive;
    private MyRecyclerAdapter mRecyclerAdapter;
    private CheckIsValidWord checkIsValidWord;
    private CheckUsedWord checkUsedWord;
    private QuaryingData quaryingData;

    public List<WordStoreModel> allWordList = new ArrayList<>();

    public static final int MESSAGE_READ = 1;
    public static final String CONFIRM_MESSAGE = "****";
    public static final String RESTART_MESSAGE = "**restart";
    public static final String LOSE_MESSAGE = "0000";

    public String connectKey;
    public int messageCount;
    private String SixKey;
    private boolean notStartTimer;
    public boolean firstConnect = true;
    private int timeLimit = 75;
    private long totalTime = 75000;
    public boolean isStartConnect = true;//toknow disconnect
    private long onceStart;
    private long onceEnd;
    private String firstString = "", secondString = "";
    private String mode;
    private boolean isNormal;
    private boolean permitClick = true;
    private boolean isClient = true;
    private boolean exit;
    private Animation zoomIn, blink;
    private long enemyTotal = 75000;
    CountDownTimer enemyTimer;
    private boolean isStartedEnemy = false;
    Dialog winnerDialog;
    private SharePreferenceStorage mSharepreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        mode = i.getStringExtra("mode");
        if (mode.equals("two_player")) {
            isNormal = true;
            setContentView(R.layout.activity_main);
            connectKey = CONFIRM_MESSAGE + "normal";
            mBackDrop = findViewById(R.id.single_backdrop_container);
            toolbar = findViewById(R.id.single_tool_bar);
            toolbar.setTitle("Dual");
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
            findViewById(R.id.rl_single).setVisibility(View.GONE);
            findViewById(R.id.rl_dual).setVisibility(View.VISIBLE);
            txtEnemyTime = findViewById(R.id.tv_enemy_time);

        } else if (mode.equals("six_key")) {
            isNormal = false;
            setContentView(R.layout.activity_six_key_challenge);
            connectKey = CONFIRM_MESSAGE + "challenge";
            mBackDrop = findViewById(R.id.challenge_backdrop);
            toolbar = findViewById(R.id.six_tool_bar);
            toolbar.setTitle("Challenge");
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
            txtEnemyTime = findViewById(R.id.tv_challenge_enemy_time);
        }
        mSharepreference=new SharePreferenceStorage(this);
        mSpeaker=new Speaker(this);
        mVibrator=new MyVibrator(this);

        mBackDrop.attachToolbar(toolbar)
                .dropInterpolator(new LinearInterpolator())
                .dropHeight(getResources().getDimensionPixelSize(R.dimen.backdrop_playground))
                .build();

        quaryingData = new QuaryingData(this);

        mSharepreference.setPartaner(mSharepreference.getProfilePicture());

        checkIsValidWord = new CheckIsValidWord(this);
        checkUsedWord = new CheckUsedWord(this);

        zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        zoomIn.setAnimationListener(this);
        blink.setAnimationListener(this);


        showDialog();

        initIntentFilter();
        if (isNormal) {
            initViewNormalTwoPlayer();
        } else {
            initViewSixKeyChallenge();

        }

        zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        zoomIn.setAnimationListener(this);
        blink.setAnimationListener(this);

        notStartTimer = true;

        mRecyclerAdapter = new MyRecyclerAdapter(this, allWordList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        if (isNormal) {
            btnAttempt.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    if ((edMessage.getText() + "").equals("")) {
                        edMessage.setError("Enter Word First...");
                        return;
                    }
                    String myString = edMessage.getText().toString();
                    secondString = myString;
                    boolean isStartEnd = true;
                    if (!firstString.equals("")) {
                        isStartEnd = checkIsValidWord.startIsEnd(firstString, secondString);
                    }
                    boolean isValidWord = checkIsValidWord.checkUserEnglishWord(myString);
                    if (isStartEnd && isValidWord) {
                        if (!notStartTimer)
                            stopTimer();

                        notStartTimer = false;
                        //onceEnd=totalTime;
                        WordStoreModel model = new WordStoreModel(myString, false, quaryingData.normalQuarying(myString));
                        allWordList.add(model);
                        mRecyclerAdapter.notifyDataSetChanged();
                        sendReceive.write(myString.getBytes());
                        startEnemtyCountDownTimer();
                        isStartedEnemy = true;
                        //mRecyclerAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(mRecyclerAdapter.getItemCount() - 1);
                        edMessage.setText("");
                    } else {
                        edMessage.setText("");
                        edMessage.setError("Try With Other Word");
                    }
                }
            });
        }


    }

    private void initViewSixKeyChallenge() {
        mRecyclerView = findViewById(R.id.six_recycler);
        stxtEdit = findViewById(R.id.six_tv_message);
        btnAttempt = findViewById(R.id.six_btn_attempt);
        sbtnBackSpace = findViewById(R.id.six_btn_backspace);
        sbtnAttempt = findViewById(R.id.six_btn_attempt);
        sbtnA = findViewById(R.id.six_btn_a);
        sbtnE = findViewById(R.id.six_btn_e);
        sbtnI = findViewById(R.id.six_btn_i);
        sbtnO = findViewById(R.id.six_btn_o);
        sbtnU = findViewById(R.id.six_btn_u);
        sbtn1 = findViewById(R.id.six_btn_one);
        sbtn2 = findViewById(R.id.six_btn_two);
        sbtn3 = findViewById(R.id.six_btn_three);
        sbtn4 = findViewById(R.id.six_btn_four);
        sbtn5 = findViewById(R.id.six_btn_five);
        sbtn6 = findViewById(R.id.six_btn_six);
        stxtTimeLeft = findViewById(R.id.six_tv_left_time);

        sbtnA.setOnClickListener(this);
        sbtnE.setOnClickListener(this);
        sbtnI.setOnClickListener(this);
        sbtnO.setOnClickListener(this);
        sbtnU.setOnClickListener(this);
        sbtn1.setOnClickListener(this);
        sbtn2.setOnClickListener(this);
        sbtn3.setOnClickListener(this);
        sbtn4.setOnClickListener(this);
        sbtn5.setOnClickListener(this);
        sbtn6.setOnClickListener(this);
        sbtnBackSpace.setOnClickListener(this);

        sbtnAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnAttemptButtonSix();
            }
        });
    }

    private void clickOnAttemptButtonSix() {
        if ((stxtEdit.getText() + "").equals("")) {
            stxtEdit.setError("Enter A Word First");
        } else {
            String myString = stxtEdit.getText().toString();
            secondString = myString;
            boolean isStartEnd = true;
            if (!firstString.equals("")) {
                isStartEnd = checkIsValidWord.startIsEnd(firstString, secondString);
            }
            boolean isValidWord = checkIsValidWord.checkUserEnglishWord(myString);
            if (isStartEnd && isValidWord) {
                if (!notStartTimer)
                    stopTimer();

                notStartTimer = false;

                sendReceive.write(myString.getBytes());
                startEnemtyCountDownTimer();
                isStartedEnemy = true;

                setUnClickableToButton();
                WordStoreModel model = new WordStoreModel(myString, false, quaryingData.normalQuarying(myString));
                allWordList.add(model);
                mRecyclerAdapter.notifyDataSetChanged();

                mRecyclerAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mRecyclerAdapter.getItemCount() - 1);
                stxtEdit.setText("");
            } else {
                stxtEdit.setText("");
                stxtEdit.setError("Try With Other Word");
            }
        }
    }

    private void initViewNormalTwoPlayer() {
        mRecyclerView = findViewById(R.id.recycler);
        btnAttempt = findViewById(R.id.btn_attempt);
        edMessage = findViewById(R.id.ed_message);
        txtTime = findViewById(R.id.tv_time);

        edMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                    if(charSequence.charAt(charSequence.length()-1)=='\'')
                    {
                        mVibrator.startVibrate(2);
                        Toast.makeText(getApplicationContext(),"This Character Is Restricted!!",Toast.LENGTH_SHORT).show();
                        edMessage.setText("");
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void startCountDownTimer() {
        mTimer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long l) {
                totalTime = l;
                if (isNormal) {
                    txtTime.setText((l / 1000) + "");
                } else
                    stxtTimeLeft.setText((l / 1000) + "");
                // Log.d("time", l + "   ");
            }

            @Override
            public void onFinish() {
                if (isNormal)
                    txtTime.setText("0");
                else
                    stxtTimeLeft.setText("0");
                // Toast.makeText(getApplicationContext(), mark + "", Toast.LENGTH_SHORT).show();
                sendReceive.write(LOSE_MESSAGE.getBytes());
                setUnClickableToButton();
                checkUsedWord.clearAllWords();
                if (isNormal)
                  //  Snackbar.make(findViewById(R.id.main), "You Lose...", Snackbar.LENGTH_LONG).show();
                    showLoserDialog();
                btnAttempt.setClickable(false);
                isStartConnect = true;
            }
        };
        mTimer.start();
    }

    private void startEnemtyCountDownTimer() {
        enemyTimer = new CountDownTimer(enemyTotal, 1000) {
            @Override
            public void onTick(long l) {
                enemyTotal = l;
                // if (isNormal) {
                txtEnemyTime.setText((l / 1000) + "");
                // } else {
                // }
                // stxtTimeLeft.setText((l / 1000) + "");
                Log.d("time", l + "   ");
            }

            @Override
            public void onFinish() {
                if (isNormal)
                    txtEnemyTime.setText("0");
                else {
                }
                //  stxtTimeLeft.setText("0");

            }
        };
        enemyTimer.start();
    }

    private void stopTimer() {
        mTimer.cancel();
    }

    private void tempTimer() {
        CountDownTimer tTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                dialog.dismiss();
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

    private void initIntentFilter() {
        // initialize intentfilter
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    private void showWinnerDialog() {
        winnerDialog = new Dialog(this);
        winnerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        winnerDialog.setContentView(R.layout.dialog_winner);
        winnerDialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        winnerDialog.setCancelable(false);
        winnerDialog.show();
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(winnerDialog.findViewById(R.id.cir_dual_medal));
        winnerDialog.findViewById(R.id.btn_winner_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winnerDialog.dismiss();
                mRecyclerView.scrollToPosition(0);
            }
        });

        winnerDialog.findViewById(R.id.btn_rematch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReceive.write(RESTART_MESSAGE.getBytes());
                notStartTimer = true;
                firstConnect = false;
                firstString = "";
                secondString = "";
                winnerDialog.dismiss();
                checkUsedWord.clearAllWords();
                enemyTotal = 75000;
                allWordList.clear();
                mRecyclerAdapter.notifyDataSetChanged();
                setClickableToButton();
                if (!isNormal) {
                    SixKey = null;
                    messageCount = 1;
                    showSixKeyChallengeDialog();
                    if (isClient) {
                        permitClick = false;
                        stxtWarning.setBackgroundColor(getResources().getColor(R.color.colorAttractive));
                        stxtWarning.setText("Tip:  Wait..Your Friend Is Picking.");
                    } else {
                        permitClick = true;
                        stxtWarning.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        stxtWarning.setText("Tip:  You Get First Chance To Pick...");
                    }
                } else {
                    messageCount = 2;
                }
                setClickableToButton();
                totalTime = 75000;
                enemyTotal = 75000;

            }
        });
    }

    private void showDialog() {
        showDevicesDialog = new Dialog(this);
        showDevicesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDevicesDialog.setContentView(R.layout.search_device_dialog);
        showDevicesDialog.setCancelable(false);
        showDevicesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showDevicesDialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;

        btnSearch = showDevicesDialog.findViewById(R.id.btn_discover);
        listDevice = showDevicesDialog.findViewById(R.id.list_devices);
        txtNoDevice = showDevicesDialog.findViewById(R.id.tv_no_device);
        // viewOuterCircle=showDevicesDialog.findViewById(R.id.view_outer_circle);
        viewInnerCircle1 = showDevicesDialog.findViewById(R.id.view_inner_circle_1);
        //viewInnerCircle2=showDevicesDialog.findViewById(R.id.view_inner_circle_2);


        showDevicesDialog.show();
        // btnSearch.setAnimation(blink);
         YoYo.with(Techniques.Flash).duration(1000).repeat(2).playOn(btnSearch);
         mVibrator.startVibrate(5);
         mSpeaker.speak("Please click on search button");
        //txtNoDevice.setAnimation(blink);
        showDevicesDialog.findViewById(R.id.btn_search_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                //startActivity(new Intent(TwoPlayerActivity.this, InitActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNoDevice.setText("Searching Other Devices...");
                txtNoDevice.setBackground(getResources().getDrawable(R.drawable.corner_blue_16dp));
                btnSearch.setBackgroundResource(R.drawable.discover_btn_background);
              //  txtNoDevice.clearAnimation();
                //       viewOuterCircle.setVisibility(View.VISIBLE);
                viewInnerCircle1.setVisibility(View.VISIBLE);
                //   viewInnerCircle2.setVisibility(View.VISIBLE);

                viewInnerCircle1.setAnimation(zoomIn);
                btnSearch.clearAnimation();

                setDeviceName(mSharepreference.getUserName());

                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(getApplicationContext(), "Discovery error", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //to connect
//                for (int i = 0; i < listDevice.getChildCount(); i++) {
//                    if(position == i ){
//                        listDevice.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorAttractive));
//                    }else{
//                        listDevice.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                    }
//                }

                listDevice.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mVibrator.startVibrate(1);
                listDevice.setClickable(false);
                final WifiP2pDevice mDevice = devices[i];//mDevice.isGroupOwner();
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = mDevice.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                //   Random r = new Random();


                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                      //  Toast.makeText(getApplicationContext(), "Connected to " + mDevice.deviceName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i) {

                    }
                });
            }
        });
    }

    private void showSixKeyChallengeDialog() {
        // Log.d("zzzz", "in here");
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_server_side_choose);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;

        dialog.show();

        sbtnQ = dialog.findViewById(R.id.q);
        sbtnW = dialog.findViewById(R.id.w);
        sbtnR = dialog.findViewById(R.id.r);
        sbtnT = dialog.findViewById(R.id.t);
        sbtnY = dialog.findViewById(R.id.y);
        sbtnP = dialog.findViewById(R.id.p);
        sbtnS = dialog.findViewById(R.id.s);
        sbtnD = dialog.findViewById(R.id.d);
        sbtnF = dialog.findViewById(R.id.f);
        sbtnG = dialog.findViewById(R.id.g);
        sbtnH = dialog.findViewById(R.id.h);
        sbtnJ = dialog.findViewById(R.id.j);
        sbtnK = dialog.findViewById(R.id.k);
        sbtnL = dialog.findViewById(R.id.l);
        sbtnZ = dialog.findViewById(R.id.z);
        sbtnX = dialog.findViewById(R.id.x);
        sbtnC = dialog.findViewById(R.id.c);
        sbtnV = dialog.findViewById(R.id.v);
        sbtnB = dialog.findViewById(R.id.b);
        sbtnN = dialog.findViewById(R.id.n);
        sbtnM = dialog.findViewById(R.id.m);
        stxtWarning = dialog.findViewById(R.id.six_txt_warning);

        YoYo.with(Techniques.Flash).duration(1000).repeat(2).playOn(stxtWarning);
        mVibrator.startVibrate(1);

        sbtnQ.setOnClickListener(this);
        sbtnW.setOnClickListener(this);
        sbtnR.setOnClickListener(this);
        sbtnT.setOnClickListener(this);
        sbtnY.setOnClickListener(this);
        sbtnP.setOnClickListener(this);
        sbtnS.setOnClickListener(this);
        sbtnD.setOnClickListener(this);
        sbtnF.setOnClickListener(this);
        sbtnG.setOnClickListener(this);
        sbtnH.setOnClickListener(this);
        sbtnJ.setOnClickListener(this);
        sbtnK.setOnClickListener(this);
        sbtnL.setOnClickListener(this);
        sbtnZ.setOnClickListener(this);
        sbtnX.setOnClickListener(this);
        sbtnC.setOnClickListener(this);
        sbtnV.setOnClickListener(this);
        sbtnB.setOnClickListener(this);
        sbtnN.setOnClickListener(this);
        sbtnM.setOnClickListener(this);
        dialog.findViewById(R.id.btn_close_picking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
            }
        });


    }
    private void showLoserDialog(){
        final Dialog loserDialog=new Dialog(this);
        loserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loserDialog.setContentView(R.layout.looser_dialog);
        loserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loserDialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        loserDialog.setCancelable(false);
        loserDialog.show();
        loserDialog.findViewById(R.id.lose_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loserDialog.dismiss();
            }
        });
    }

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            //  Log.d("bugss",wifiP2pDeviceList.toString());
            Toast.makeText(getApplicationContext(), "Enter peer listener", Toast.LENGTH_SHORT).show();
            //  Log.d("bugss",wifiP2pDeviceList.getDeviceList().size()+"");
            if (!wifiP2pDeviceList.getDeviceList().equals(peerList)) {

                Toast.makeText(getApplicationContext(), "still good", Toast.LENGTH_SHORT).show();

                peerList.clear();
                peerList.addAll(wifiP2pDeviceList.getDeviceList());
                // Log.d("bugss",peerList.size()+"");
                deviceNames = new String[wifiP2pDeviceList.getDeviceList().size()];
                devices = new WifiP2pDevice[wifiP2pDeviceList.getDeviceList().size()];

                viewInnerCircle1.clearAnimation();
                viewInnerCircle1.setVisibility(View.INVISIBLE);

                int index = 0;
                for (WifiP2pDevice device : wifiP2pDeviceList.getDeviceList()) {

                    // Toast.makeText(getApplicationContext(), device.deviceName, Toast.LENGTH_SHORT).show();

                    deviceNames[index] = device.deviceName;
                    devices[index] = device;
                    index++;

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, deviceNames);
                listDevice.setAdapter(adapter);
                txtNoDevice.setVisibility(View.GONE);
                listDevice.setVisibility(View.VISIBLE);

                if (peerList.size() == 0) {
                    if (isStartConnect) {

                    } else {
                        isStartConnect = true;
                        Toast.makeText(getApplicationContext(), "No Device is found", Toast.LENGTH_SHORT).show();
                        listDevice.setVisibility(View.INVISIBLE);
                        txtNoDevice.setText("Something Went Wrong. Try Again...");
                        txtNoDevice.setVisibility(View.VISIBLE);
                        try {
                            winnerDialog.dismiss();
                        } catch (Exception e) {
                        }
                        TwoPlayerActivity.this.startActivity(new Intent(TwoPlayerActivity.this, DisconnectActivity.class));
                        onPause();
                    }
                }
            }
        }
    };

    //detect host or client
    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;

            if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                isStartConnect = false;
                serverClass = new ServerClass();
                serverClass.start();
                //   sendReceive.write(connectKey.getBytes());
                mManager.clearServiceRequests(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i) {

                    }
                });
                Toast.makeText(getApplicationContext(), "It is host", Toast.LENGTH_SHORT).show();
                permitClick = true;
                isClient = false;
                txtNoDevice.clearAnimation();
                showDevicesDialog.dismiss();

            } else if (wifiP2pInfo.groupFormed) {
                isStartConnect = false;
                clientClass = new ClientClass(groupOwnerAddress);
                clientClass.start();
                //    sendReceive.write(connectKey.getBytes());
                Toast.makeText(getApplicationContext(), "It is client", Toast.LENGTH_SHORT).show();
                permitClick = false;
                isClient = true;
                txtNoDevice.clearAnimation();
                showDevicesDialog.dismiss();
            }
        }
    };


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) message.obj;
                    String msg = new String(readBuff, 0, message.arg1);//get message
                    if (firstConnect && (!(msg.split("//")[0]).equals(connectKey))) {
                        onPause();//***
                    }
                    if (!isNormal && messageCount == 1) {

                        SixKey = msg;
                        //Log.d("sixkey", SixKey);
                        if (SixKey.length() == 3) {
                            disableThreeChars(SixKey);
                            permitClick = true;
                            stxtWarning.setBackgroundColor(Color.parseColor("#2196f3"));
                            stxtWarning.setText("Tip:  Now...Your Time.");
                        } else if (SixKey.length() == 6) {
                            disableSixChars(SixKey);
                            setTextToButtons();
                        }
                        messageCount++;
                    } else if (firstConnect && (msg.split("//")[0]).equals(connectKey)) {
                        firstConnect = false;
                        messageCount++;
                        mSharepreference.setPartaner(Integer.parseInt(msg.split("//")[1]));
                        Toast.makeText(getApplicationContext(), "Everythings OK", Toast.LENGTH_SHORT).show();
                        showDevicesDialog.dismiss();
                        if (isClient) {
                            if (!isNormal) {
                                showSixKeyChallengeDialog();
                                stxtWarning.setBackgroundColor(Color.parseColor("#f44336"));
                            }
                        } else {
                            // showDevicesDialog.dismiss();

                            if (!isNormal) {
                                // Log.d("OKKK","nicer");
                                showSixKeyChallengeDialog();
                                stxtWarning.setBackgroundColor(Color.parseColor("#2196f3"));
                                stxtWarning.setText("Tip:  You Get First Pick...");
                            }
                        }
                    } else if (msg.equals(LOSE_MESSAGE)) {
                        try {
                            isStartConnect = true;
                            setUnClickableToButton();
                            mTimer.cancel();

                        } catch (Exception e) {
                        }
                        checkUsedWord.clearAllWords();
                        showWinnerDialog();
                        if (isNormal)
                            //Snackbar.make(findViewById(R.id.main), "You Win", Snackbar.LENGTH_LONG).show();
                            mSharepreference.setDualMatch(mSharepreference.getDualMatch()+1);
                        else
                            mSharepreference.setChallengeMatch(mSharepreference.getChallengeMatch()+1);
                    } else if (isStartConnect && msg.equals(RESTART_MESSAGE)) {
                        firstConnect = false;
                        notStartTimer = true;
                        firstString = "";
                        secondString = "";
                        setClickableToButton();
                        checkUsedWord.clearAllWords();
                        allWordList.clear();
                        mRecyclerAdapter.notifyDataSetChanged();
                        if (!isNormal) {
                            SixKey = null;
                            messageCount = 1;
                            showSixKeyChallengeDialog();
                            if (isClient) {
                                permitClick = false;
                                stxtWarning.setBackgroundColor(getResources().getColor(R.color.colorAttractive));
                                stxtWarning.setText("Tip:  Wait..Your Friend Is Picking.");
                            } else {
                                permitClick = true;
                                stxtWarning.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                stxtWarning.setText("Tip:  You Get First Chance To Pick...");
                            }
                        } else {
                            messageCount = 2;
                        }
                        setClickableToButton();
                        totalTime = 75000;
                        enemyTotal = 75000;
                        if (isNormal) {
                            txtTime.setText("75");
                        } else
                            stxtTimeLeft.setText("75");
                    } else {
                        setClickableToButton();
                        if (isNormal) {
                            // onceStart=totalTime;
                            checkUsedWord.insertWord(msg);
                            firstString = msg;
                            notStartTimer = false;
                            startCountDownTimer();
                            if (isStartedEnemy) {
                                enemyTimer.cancel();
                                isStartedEnemy = false;
                            }
                            mSpeaker.speak(msg);
                            WordStoreModel model = new WordStoreModel(msg, true, quaryingData.normalQuarying(msg));
                            allWordList.add(model);
                            mRecyclerAdapter.notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(mRecyclerAdapter.getItemCount() - 1);
                            edMessage.setText("");
                        } else {
                            checkUsedWord.insertWord(msg);
                            firstString = msg;
                            notStartTimer = false;
                            startCountDownTimer();
                            if (isStartedEnemy) {
                                enemyTimer.cancel();
                                isStartedEnemy = false;
                            }
                            mSpeaker.speak(msg);
                            WordStoreModel model = new WordStoreModel(msg, true, quaryingData.normalQuarying(msg));
                            allWordList.add(model);
                            mRecyclerAdapter.notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(mRecyclerAdapter.getItemCount() - 1);
                            stxtEdit.setText("");
                        }
                    }
                    break;
            }
            return true;
        }
    });

    private void disableThreeChars(String word) {
        setDisableButton(word.charAt(0));
        setDisableButton(word.charAt(1));
        setDisableButton(word.charAt(2));
    }

    private void disableSixChars(String word) {
        setDisableButton(word.charAt(0));
        setDisableButton(word.charAt(1));
        setDisableButton(word.charAt(2));
        setDisableButton(word.charAt(3));
        setDisableButton(word.charAt(4));
        setDisableButton(word.charAt(5));
    }

    private void setDisableButton(char c) {
        switch (c) {
            case 'q':
                setButtonClicked(sbtnQ);
                break;
            case 'w':
                setButtonClicked(sbtnW);
                break;
            case 'r':
                setButtonClicked(sbtnR);
                break;
            case 't':
                setButtonClicked(sbtnT);
                break;
            case 'y':
                setButtonClicked(sbtnY);
                break;
            case 'p':
                setButtonClicked(sbtnP);
                break;
            case 's':
                setButtonClicked(sbtnS);
                break;
            case 'd':
                setButtonClicked(sbtnD);
                break;
            case 'f':
                setButtonClicked(sbtnF);
                break;
            case 'g':
                setButtonClicked(sbtnG);
                break;
            case 'h':
                setButtonClicked(sbtnH);
                break;
            case 'j':
                setButtonClicked(sbtnJ);
                break;
            case 'k':
                setButtonClicked(sbtnK);
                break;
            case 'l':
                setButtonClicked(sbtnL);
                break;
            case 'z':
                setButtonClicked(sbtnZ);
                break;
            case 'x':
                setButtonClicked(sbtnX);
                break;
            case 'c':
                setButtonClicked(sbtnC);
                break;
            case 'v':
                setButtonClicked(sbtnV);
                break;
            case 'b':
                setButtonClicked(sbtnB);
                break;
            case 'n':
                setButtonClicked(sbtnN);
                break;
            case 'm':
                setButtonClicked(sbtnM);
                break;
        }
    }

    private void setTextToButtons() {
        sbtn1.setText(SixKey.charAt(0) + "");
        sbtn2.setText(SixKey.charAt(1) + "");
        sbtn3.setText(SixKey.charAt(2) + "");
        sbtn4.setText(SixKey.charAt(3) + "");
        sbtn5.setText(SixKey.charAt(4) + "");
        sbtn6.setText(SixKey.charAt(5) + "");
        stxtWarning.setBackgroundColor(Color.parseColor("#81c784"));
        stxtWarning.setText("Tip:  Battle Will Be Start In 3s");
        tempTimer();
    }

    private void openWifi() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    private void closeWifi() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public void disconnect() {
        if (mManager != null && mChannel != null) {
            mManager.requestGroupInfo(mChannel, new WifiP2pManager.GroupInfoListener() {
                @Override
                public void onGroupInfoAvailable(WifiP2pGroup group) {
                    if (group != null && mManager != null && mChannel != null
                            && group.isGroupOwner()) {
                        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {

                            @Override
                            public void onSuccess() {
                                Log.d("disconnect", "removed");
                            }

                            @Override
                            public void onFailure(int reason) {

                            }
                        });
                    }
                }
            });
            mManager.cancelConnect(mChannel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d("disconnect", "success");
                }

                @Override
                public void onFailure(int i) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "resume");
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), new WifiP2pManager.ChannelListener() {
            @Override
            public void onChannelDisconnected() {
                mManager=(WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
            }
        });
        openWifi();
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);
        registerReceiver(mReceiver, mIntentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        disconnect();
        mManager = null;
        mChannel = null;
        sendReceive = null;
        serverClass = null;
        clientClass = null;
        closeWifi();
        registerReceiver(mReceiver, mIntentFilter);
        unregisterReceiver(mReceiver);

        checkUsedWord.clearAllWords();
        checkUsedWord.closeRealmUsedWord();
        //Toast.makeText(getApplicationContext(), "On Pause", Toast.LENGTH_SHORT).show();
        System.exit(0);
        // startActivity(new Intent(TwoPlayerActivity.this, InitActivity.class));
        //onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.six_btn_a:
                stxtEdit.append("a");
                break;
            case R.id.six_btn_e:
                stxtEdit.append("e");
                break;
            case R.id.six_btn_i:
                stxtEdit.append("i");
                break;
            case R.id.six_btn_o:
                stxtEdit.append("o");
                break;
            case R.id.six_btn_u:
                stxtEdit.append("u");
                break;
            case R.id.six_btn_one:
                stxtEdit.append(sbtn1.getText().toString());
                break;
            case R.id.six_btn_two:
                stxtEdit.append(sbtn2.getText().toString());
                break;
            case R.id.six_btn_three:
                stxtEdit.append(sbtn3.getText().toString());
                break;
            case R.id.six_btn_four:
                stxtEdit.append(sbtn4.getText().toString());
                break;
            case R.id.six_btn_five:
                stxtEdit.append(sbtn5.getText().toString());
                break;
            case R.id.six_btn_six:
                stxtEdit.append(sbtn6.getText().toString());
                break;
            case R.id.six_btn_backspace:
                String tamp = stxtEdit.getText().toString();
                if (tamp.length() > 0)
                    tamp = tamp.substring(0, tamp.length() - 1);
                stxtEdit.setText(tamp);
                break;
            case R.id.q:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "q";
                    else
                        SixKey += "q";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnQ);
                }
                break;
            case R.id.w:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "w";
                    else
                        SixKey += "w";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnW);
                }
                break;
            case R.id.r:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "r";
                    else
                        SixKey += "r";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnR);
                }
                break;
            case R.id.t:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "t";
                    else
                        SixKey += "t";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnT);
                }
                break;
            case R.id.y:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "y";
                    else
                        SixKey += "y";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnY);
                }
                break;
            case R.id.p:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "p";
                    else
                        SixKey += "p";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnP);
                }
                break;
            case R.id.s:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "s";
                    else
                        SixKey += "s";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnS);
                }
                break;
            case R.id.d:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "d";
                    else
                        SixKey += "d";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnD);
                }
                break;
            case R.id.f:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "f";
                    else
                        SixKey += "f";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnF);
                }
                break;
            case R.id.g:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "g";
                    else
                        SixKey += "g";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnG);
                }
                break;
            case R.id.h:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "h";
                    else
                        SixKey += "h";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnH);
                }
                break;
            case R.id.j:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "j";
                    else
                        SixKey += "j";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnJ);
                }
                break;
            case R.id.k:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "k";
                    else
                        SixKey += "k";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnK);
                }
                break;
            case R.id.l:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "l";
                    else
                        SixKey += "l";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnL);
                }
                break;
            case R.id.z:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "z";
                    else
                        SixKey += "z";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnZ);
                }
                break;
            case R.id.x:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "x";
                    else
                        SixKey += "x";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnX);
                }
                break;
            case R.id.c:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "c";
                    else
                        SixKey += "c";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnC);
                }
                break;
            case R.id.v:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "v";
                    else
                        SixKey += "v";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                    }
                    setButtonClicked(sbtnV);
                    if (SixKey.length() == 6)
                        setTextToButtons();
                }
                break;
            case R.id.b:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "b";
                    else
                        SixKey += "b";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                    }
                    setButtonClicked(sbtnB);
                    if (SixKey.length() == 6)
                        setTextToButtons();
                }
                break;
            case R.id.n:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "n";
                    else
                        SixKey += "Q";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnN);
                }
                break;
            case R.id.m:
                if (permitClick) {
                    if (SixKey == null)
                        SixKey = "m";
                    else
                        SixKey += "m";
                    if (SixKey.length() == 3 || SixKey.length() == 6) {
                        permitClick = false;
                        sendReceive.write(SixKey.getBytes());
                        if (SixKey.length() == 6)
                            setTextToButtons();
                    }
                    setButtonClicked(sbtnM);
                }
                break;


        }
    }

    private void setButtonClicked(Button btn) {
        btn.setBackgroundResource(R.drawable.btn_selected_design);
        btn.setTextColor(Color.BLACK);
        btn.setClickable(false);
    }

    private void setClickableToButton() {
        if (isNormal) {
            btnAttempt.setClickable(true);
        } else {
            sbtnAttempt.setClickable(true);
        }
    }

    private void setUnClickableToButton() {
        if (isNormal) {
            btnAttempt.setClickable(false);
        } else {
            sbtnAttempt.setClickable(false);
        }
    }
    public void setDeviceName(String devName) {
        try {
            Method m = mManager.getClass().getMethod(
                    "setDeviceName",
                    new Class[] { WifiP2pManager.Channel.class, String.class,
                            WifiP2pManager.ActionListener.class });

            m.invoke(mManager,mChannel, devName, new WifiP2pManager.ActionListener() {
                public void onSuccess() {
                    //Code for Success in changing name
                    Log.d("device","successs");
                }

                public void onFailure(int reason) {
                    //Code to be done while name change Fails
                    Log.d("device","fail"+reason);
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    private class ServerClass extends Thread {
        private Socket socket;
        private ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                Log.d("Server", "Running");
                serverSocket = new ServerSocket(8888);
                this.socket = serverSocket.accept();
                sendReceive = new SendReceive(this.socket);
                sendReceive.start();
                sendReceive.write((connectKey+"//"+mSharepreference.getProfilePicture()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientClass extends Thread {
        private Socket socket;
        private String hostAddress;

        public ClientClass(InetAddress hostAddress) {
            this.hostAddress = hostAddress.getHostAddress();
            socket = new Socket();
        }

        @Override
        public void run() {
            try {
                Log.d("Client", "Running");
                socket.connect(new InetSocketAddress(hostAddress, 8888), 500);
                sendReceive = new SendReceive(socket);
                sendReceive.start();
                sendReceive.write((connectKey+"//"+mSharepreference.getProfilePicture()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private class SendReceive extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt) {
            socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (socket != null) {
                try {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        handler.obtainMessage(TwoPlayerActivity.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public void write(final byte[] bytes) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream.write(bytes);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Failed to sent", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            t.start();

        }
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

//
//<action android:name="WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION"/>
//<action android:name="WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION"/>
//<action android:name="WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION"/>
//<action android:name="WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION"/>