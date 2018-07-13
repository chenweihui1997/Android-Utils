package com.example.administrator.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import htmls.DownloadAndUploadHtmlActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        findViewById(R.id.toUrl_bt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toUrl_bt:
                DownloadAndUploadHtmlActivity.startDownloadAndUploadHtmlActivity(this,"https://chenweihui1997.github.io");
                break;
        }
    }
}
