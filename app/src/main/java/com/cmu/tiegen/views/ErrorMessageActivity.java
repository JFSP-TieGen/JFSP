package com.cmu.tiegen.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmu.tiegen.R;
import com.cmu.tiegen.exceptions.ExceptionHandler;

public class ErrorMessageActivity extends AppCompatActivity {
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_error_message);
        Button goBack = (Button) findViewById(R.id.goBack) ;
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivityForResult(i, 0);
                finish();
            }
        });
        error = (TextView) findViewById(R.id.error);
        error.setText(getIntent().getStringExtra("error"));
    }

}
