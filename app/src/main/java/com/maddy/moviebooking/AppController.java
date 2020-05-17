package com.maddy.moviebooking;

import android.app.Application;
import android.content.Context;

import com.maddy.moviebooking.di.AppComponent;
import com.maddy.moviebooking.di.AppModule;
import com.maddy.moviebooking.di.DaggerAppComponent;
import com.maddy.moviebooking.di.RoomDbModule;

public class AppController extends Application {

    private AppComponent app;
    private static Context context;
    private static AppController myApplication;

    public void onCreate() {
        super.onCreate();
        myApplication = this;
        AppController.context = getApplicationContext();
        initDagger();
    }

    private void initDagger() {
        app = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomDbModule(new RoomDbModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return app;
    }

    public static AppController getMyApplication() {
        return myApplication;
    }

    public static Context getAppContext() {
        return AppController.context;
    }
}