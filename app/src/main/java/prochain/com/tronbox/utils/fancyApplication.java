package prochain.com.tronbox.utils;

import android.content.Context;

import android.support.multidex.MultiDexApplication;

import prochain.com.tronbox.utils.loader.GlideImageLoader;
import prochain.com.tronbox.utils.loader.ImageLoaderUtils;

/**
 * Created by alex on 2019/1/22.
 */

public class fancyApplication extends MultiDexApplication {
    private static fancyApplication singleton;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化图片加载
        singleton = this;
        ImageLoaderUtils.initImageLoader(new GlideImageLoader());

    }

    public static fancyApplication getInstance() {
        return singleton;
    }

}

