package com.maddy.moviebooking.di;

import android.content.Context;

import com.maddy.moviebooking.AppController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule  {
    AppController mApplication;

    public AppModule(AppController application) {
        mApplication = application;
    }

    @Provides
    public Context getAppContext(){
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    AppController providesApplication() {
        return mApplication;
    }
}