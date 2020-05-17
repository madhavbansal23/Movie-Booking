package com.maddy.moviebooking.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Seats {

    @PrimaryKey
    private int id;
    private String seatNo;
    private String status;
    private String family;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
