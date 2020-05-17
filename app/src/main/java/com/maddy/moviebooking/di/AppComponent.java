package com.maddy.moviebooking.di;

import com.maddy.moviebooking.LoginActivity;
import com.maddy.moviebooking.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RoomDbModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
}
