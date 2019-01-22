package prochain.com.tronbox.utils;

import android.content.Context;

import android.support.multidex.MultiDexApplication;

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
    }

    public static fancyApplication getInstance() {
        return singleton;
    }

}

