package com.example.homeambalaunce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class showFirstAds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_first_ads);
        TextView title=(TextView) findViewById(R.id.title_Fi);
        title.setText(getIntent().getStringExtra("problem"));
        TextView type=(TextView) findViewById(R.id.type);
        type.setText(getIntent().getStringExtra("type"));
        TextView risk=(TextView) findViewById(R.id.risk);
        risk.setText(getIntent().getStringExtra("isrisked")+"\n"+getIntent().getStringExtra("callIfRisk"));
        TextView firstaid=(TextView) findViewById(R.id.firstaid);
        firstaid.setText(getIntent().getStringExtra("FirstAid"));
    }
}