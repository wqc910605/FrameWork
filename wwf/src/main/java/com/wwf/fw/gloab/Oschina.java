package com.wwf.fw.gloab;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 作者: wwf
 * 描述:
 *
 **/

public class Oschina extends Application {
    public static Context context;
    public static Handler mainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mainHandler = new Handler();
    }
}
