package com.maddy.moviebooking.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.maddy.moviebooking.model.Seats;

@Database(entities = {Seats.class}, version = MovieDB.VERSION)
public abstract class MovieDB extends RoomDatabase {

    static final int VERSION = 1;
    public static final String DB_NAME = "movie_db";

    public abstract MovieDao getMovieDao();

}