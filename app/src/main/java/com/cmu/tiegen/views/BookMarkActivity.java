package com.cmu.tiegen.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import com.cmu.tiegen.Fragments.BookmarkFragment;
import com.cmu.tiegen.R;
import com.cmu.tiegen.exceptions.ExceptionHandler;

public class BookMarkActivity extends FragmentActivity {
    protected Fragment createFragment(){
        return new BookmarkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.fragment_bookmark_listing);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentBookMarkContainer);


        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentBookMarkContainer, fragment)
                    .commit();
        }
    }
}
