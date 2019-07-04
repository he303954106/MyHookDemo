package com.hk.hook;

import android.app.Application;
import android.content.Context;

import com.hk.library.SDK;

/**
 * Created by hk on 2019/7/4.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SDK.init();
    }
}
