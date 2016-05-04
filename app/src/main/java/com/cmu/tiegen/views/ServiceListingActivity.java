package com.cmu.tiegen.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.cmu.tiegen.R;
import com.cmu.tiegen.exceptions.ExceptionHandler;

public class ServiceListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_service_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button schedule = (Button)findViewById(R.id.schedule_button);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ViewCalendarActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }

    public void onClick(View v){
        Intent i = new Intent(v.getContext(), ServiceDetailActivity.class);
        startActivityForResult(i, 0);
    }

}
