package com.diandianguanjia.workspace_dd_3.app;

import android.app.Application;
import android.content.Context;


import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.mode.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


public class MyApplication extends Application {

    public static MyApplication context;
    private static MyApplication instance;
    private UserInfo userInfo = null;
    public static MyApplication getInstance() {
        if (context == null) {
            return new MyApplication();
        } else {
            return context;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;

        //ShareSDK.initSDK(this);
        // 图片加载工具初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_public_nophoto)
                .showImageOnFail(R.drawable.ic_public_nophoto)
                .cacheInMemory(true).cacheOnDisc(true).build();
        // 图片加载工具配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        //初始化
        Beta.autoCheckUpgrade = true;//设置自动检查

        // Bugly.init(context, "493d4b6558", false);
        Bugly.init(context, "aa39ee4695", false);
    }

    public static MyApplication getInstance(Context appContext) {
        return instance;
    }

}