package com.maddy.moviebooking.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maddy.moviebooking.model.Seats;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEntry(Seats seat);

    @Query("SELECT * FROM `Seats`")
    List<Seats> getAllSeats();

    @Query("DELETE FROM `Seats`")
    void deleteTable();
}
