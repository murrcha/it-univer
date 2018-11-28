package com.kkaysheva.ituniver;

import android.app.Application;
import android.content.Context;

/**
 * App
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
