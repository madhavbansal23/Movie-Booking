package com.maddy.moviebooking.di;

import androidx.room.Room;

import com.maddy.moviebooking.AppController;
import com.maddy.moviebooking.room.MovieDB;
import com.maddy.moviebooking.room.MovieDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class RoomDbModule {
    private MovieDB movieDB;

    public RoomDbModule(AppController mApplication) {
        movieDB = Room.databaseBuilder(mApplication, MovieDB.class, MovieDB.DB_NAME).build();
    }

    @Singleton
    @Provides
    MovieDB providesRoomDatabase() {
        return movieDB;
    }

    @Singleton
    @Provides
    MovieDao providesProductDao(MovieDB movieDB) {
        return movieDB.getMovieDao();
    }
}