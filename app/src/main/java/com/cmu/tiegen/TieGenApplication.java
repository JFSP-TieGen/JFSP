package com.cmu.tiegen;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by keerthanathangaraju on 5/4/16.
 */
public class TieGenApplication extends Application {

    private static TieGenApplication singleton;

    private AppContext appContext = null;

    public static TieGenApplication getInstance() {
        return singleton;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        appContext = new AppContext(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
