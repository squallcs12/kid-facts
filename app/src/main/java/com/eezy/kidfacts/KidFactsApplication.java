package com.eezy.kidfacts;

import android.app.Application;
import android.content.Context;

import java.util.Random;

public class KidFactsApplication extends Application {

    public static Context context;
    public static Random random;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        random = new Random(System.currentTimeMillis());
    }
}
