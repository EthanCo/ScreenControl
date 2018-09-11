package com.sharenew.screenoff;

import android.app.Application;
import android.content.Context;

/**
 * App
 *
 * @author EthanCo
 * @since 2018/9/11
 */

public class App extends Application {
    private static App instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
