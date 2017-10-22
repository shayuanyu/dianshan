package com.bawei.dianshang.utils;

import android.app.Application;

/**
 * Created by Administrator on 2017/10/9.
 */

public class MyApp extends Application {

   private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
         app=this;
    }
    public static MyApp getInstance(){
        return app;
    }
}
