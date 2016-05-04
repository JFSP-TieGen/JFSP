package com.cmu.tiegen.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.cmu.tiegen.R;
import com.cmu.tiegen.exceptions.ExceptionHandler;

/**
 * Created by keerthanathangaraju on 4/5/16.
 */
public class RateAndReviewActivity extends AppCompatActivity {
    private CursorAdapter mortgageAdapter;
    private ListView lw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.rate_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rate and Review");

//        Button schedule=(Button)findViewById(R.id.schedule_from_bookmark);
//        schedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), ViewCalendarActivity.class);
//                startActivityForResult(i, 0);
//            }
//        });

    }
}