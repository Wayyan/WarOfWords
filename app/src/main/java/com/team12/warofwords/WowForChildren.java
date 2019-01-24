package com.team12.warofwords;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.team12.warofwords.DownloadBook.FileDownloadClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WowForChildren extends AppCompatActivity implements View.OnClickListener {

    private final String GREADE_2 = "https://drive.google.com/uc?export=download&id=1fcpnGPSg3EeqLARTjDoR0K8ye416TkgE", GRADE_3 = "https://drive.google.com/uc?export=download&id=1deUPxU3Db3GbVbctj7ANdac94zOpk1dN", GRADE_4 = "https://drive.google.com/uc?export=download&id=1EP-7B18-XbhAwD1l_TDRwwY5-eBuEP_Y", GRADE_5 = "https://drive.google.com/uc?export=download&id=1VpeBNERJg-6pRX8CWB2tvz3vxOsng3Jq", GRADE_6 = "https://drive.google.com/uc?export=download&id=1Ql5zS2yqVYF97AuAqJC09iEbMnJlDj8P", GRADE_7 = "https://drive.google.com/uc?export=download&id=11IVjH35oYomg_H_NgiJ5DhNYD7AoArSL", GRADE_8 = "https://drive.google.com/uc?export=download&id=1Sww1BGwXK9fQi1yBM7dwPiUslpHlRGNN", GRADE_9 = "https://drive.google.com/uc?export=download&id=1V1xcHFbZdqBGIZRggQnvFZEIL7IMZbVb";
    private final String FILE_NAME_GREADE_2 = "GRADE_2.txt", FILE_NAME_GREADE_3 = "GRADE_3.txt", FILE_NAME_GREADE_4 = "GRADE_4.txt", FILE_NAME_GREADE_5 = "GRADE_5.txt", FILE_NAME_GREADE_6 = "GRADE_6.txt", FILE_NAME_GREADE_7 = "GRADE_7.txt", FILE_NAME_GREADE_8 = "GRADE_8.txt", FILE_NAME_GREADE_9 = "GRADE_9.txt";
    private boolean downGrade2,downGrade3,downGrade4,downGrade5,downGrade6,downGrade7,downGrade8,downGrade9;
    private Button btnGrade2,btnGrade3,btnGrade4,btnGrade5,btnGrade6,btnGrade7,btnGrade8,btnGrade9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wow_for_children);

        Toolbar toolbar = findViewById(R.id.children_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        btnGrade2 = findViewById(R.id.btn_grade2);
        btnGrade3 = findViewById(R.id.btn_grade3);
        btnGrade4 = findViewById(R.id.btn_grade4);
        btnGrade5 = findViewById(R.id.btn_grade5);
        btnGrade6 = findViewById(R.id.btn_grade6);
        btnGrade7 = findViewById(R.id.btn_grade7);
        btnGrade8 = findViewById(R.id.btn_grade8);
        btnGrade9 = findViewById(R.id.btn_grade9);

        checkFiles();

        btnGrade2.setOnClickListener(this);
        btnGrade3.setOnClickListener(this);
        btnGrade4.setOnClickListener(this);
        btnGrade5.setOnClickListener(this);
        btnGrade6.setOnClickListener(this);
        btnGrade7.setOnClickListener(this);
        btnGrade8.setOnClickListener(this);
        btnGrade9.setOnClickListener(this);
    }
    private void checkFiles(){

        if(isFileExist(FILE_NAME_GREADE_2)){
            btnGrade2.setText("Play");
            downGrade2=true;
        }
        else {
            btnGrade2.setText("Download");
            downGrade2=false;
        }


        if(isFileExist(FILE_NAME_GREADE_3)){
            btnGrade3.setText("Play");
            downGrade3=true;
        }
        else {
            btnGrade3.setText("Download");
            downGrade3=false;
        }


        if(isFileExist(FILE_NAME_GREADE_4)){
            btnGrade4.setText("Play");
            downGrade4=true;
        }
        else {
            btnGrade4.setText("Download");
            downGrade4=false;
        }


        if(isFileExist(FILE_NAME_GREADE_5)){
            btnGrade5.setText("Play");
            downGrade5=true;
        }
        else {
            btnGrade5.setText("Download");
            downGrade5=false;
        }


        if(isFileExist(FILE_NAME_GREADE_6)){
            btnGrade6.setText("Play");
            downGrade6=true;
        }
        else {
            btnGrade6.setText("Download");
            downGrade6=false;
        }


        if(isFileExist(FILE_NAME_GREADE_7)){
            btnGrade7.setText("Play");
            downGrade7=true;
        }
        else {
            btnGrade7.setText("Download");
            downGrade7=false;
        }


        if(isFileExist(FILE_NAME_GREADE_8)){
            btnGrade8.setText("Play");
            downGrade8=true;
        }
        else {
            btnGrade8.setText("Download");
            downGrade8=false;
        }


        if(isFileExist(FILE_NAME_GREADE_9)){
            btnGrade9.setText("Play");
            downGrade9=true;
        }
        else {
            btnGrade9.setText("Download");
            downGrade9=false;
        }



//        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                downloadFile(GREADE_2,FILE_NAME_GREADE_2);
//            }
//        });
//
//        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//        if (isFileExist(FILE_NAME_GREADE_2)) {
//            Toast.makeText(getApplicationContext(), readFile(FILE_NAME_GREADE_2), Toast.LENGTH_SHORT).show();
//            Log.d("*****",readFile(FILE_NAME_GREADE_2));
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"File Not Found",Toast.LENGTH_SHORT).show();
//        }
//
//            }
//        });

    }


    private void downloadFile(String url, final String fileName) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://drive.google.com/");
        Retrofit retrofit = builder.build();

        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);

        Call<ResponseBody> call = fileDownloadClient.downloadFile(url);
        Toast.makeText(getApplicationContext(),"Download Start",Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            writeToDisk(response.body(), fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(getApplicationContext(),"Successfully Downloaded",Toast.LENGTH_LONG).show();
                        checkFiles();
                    }
                }.execute();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private boolean writeToDisk(ResponseBody body, String fileName) throws IOException {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/WAR_OF_WORDS");
            file.mkdir();
            File outputFile=new File(file,fileName);


            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
//                Toast.makeText(getApplicationContext(), "Downloading", Toast.LENGTH_LONG).show();
                byte[] readByteFile = new byte[4048];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(outputFile);


                while (true) {
                    int read = inputStream.read(readByteFile);
                    if (read == -1) {
                      //  Toast.makeText(getApplicationContext(), "Download Complete", Toast.LENGTH_LONG).show();
                        break;
                    }
                    outputStream.write(readByteFile, 0, read);
                    fileSizeDownloaded += read;
                   // Log.d("down", fileSizeDownloaded + "   " + fileSize);
                }
                outputStream.flush();

                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private String readFile(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "WAR_OF_WORDS/" + fileName);
        if (file.exists() && !file.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }

            return sb.toString();
        }
        return "error";

    }

    private boolean isFileExist(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "WAR_OF_WORDS/" + fileName);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_grade2:
                if(downGrade2){
                   String data=readFile(FILE_NAME_GREADE_2);
                   goToNextActivity(data);
                }else{
                    downloadFile(GREADE_2,FILE_NAME_GREADE_2);
                }
                break;

            case R.id.btn_grade3:
                if(downGrade3){
                    String data=readFile(FILE_NAME_GREADE_3);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_3,FILE_NAME_GREADE_3);
                }
                break;

            case R.id.btn_grade4:
                if(downGrade4){
                    String data=readFile(FILE_NAME_GREADE_4);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_4,FILE_NAME_GREADE_4);
                }
                break;

            case R.id.btn_grade5:
                if(downGrade5){
                    String data=readFile(FILE_NAME_GREADE_5);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_5,FILE_NAME_GREADE_5);
                }
                break;

            case R.id.btn_grade6:
                if(downGrade6){
                    String data=readFile(FILE_NAME_GREADE_6);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_6,FILE_NAME_GREADE_6);
                }
                break;

            case R.id.btn_grade7:
                if(downGrade7){
                    String data=readFile(FILE_NAME_GREADE_7);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_7,FILE_NAME_GREADE_7);
                }
                break;

            case R.id.btn_grade8:
                if(downGrade8){
                    String data=readFile(FILE_NAME_GREADE_8);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_8,FILE_NAME_GREADE_8);
                }
                break;

            case R.id.btn_grade9:
                if(downGrade9){
                    String data=readFile(FILE_NAME_GREADE_9);
                    goToNextActivity(data);
                }else{
                    downloadFile(GRADE_9,FILE_NAME_GREADE_9);
                }
                break;
        }
    }
    private void goToNextActivity(String data){
        Intent i=new Intent(WowForChildren.this,MainActivity.class);
        i.putExtra("Mode",2);
        i.putExtra("Data",data);
        startActivity(i);

    }
}


