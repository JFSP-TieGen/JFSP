package com.cmu.tiegen.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import com.cmu.tiegen.Fragments.RatingsFragment;
import com.cmu.tiegen.Fragments.SearchFragment;
import com.cmu.tiegen.R;
import com.cmu.tiegen.exceptions.ExceptionHandler;

public class RateAndReviewActivity extends FragmentActivity {
    protected Fragment createFragment(){
        return new RatingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.fragment_ratings);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentRatingsContainer);


        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentRatingsContainer, fragment)
                    .commit();
        }
    }
}


