package com.solution.pharmeasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LikelistActivity extends AppCompatActivity {
TextView heading, subheding, ownername;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likelist);

        img = (ImageView) findViewById(R.id.iv_img);
        heading = (TextView) findViewById(R.id.tv_heading);
        subheding = (TextView) findViewById(R.id.tv_subheding);
        ownername = (TextView) findViewById(R.id.tv_owner);
    }
}
