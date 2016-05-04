package com.cmu.tiegen.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cmu.tiegen.R;
import com.cmu.tiegen.exceptions.ExceptionHandler;

public class BookMarkActivity extends AppCompatActivity {
//    private CursorAdapter mortgageAdapter;
    private ListView lw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_book_mark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My BookMark");

        Button schedule=(Button)findViewById(R.id.schedule_from_bookmark);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ViewCalendarActivity.class);
                startActivityForResult(i, 0);
            }
        });

    }
}
