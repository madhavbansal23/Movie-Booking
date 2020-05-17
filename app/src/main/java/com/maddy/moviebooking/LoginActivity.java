package com.maddy.moviebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.maddy.moviebooking.model.Seats;
import com.maddy.moviebooking.room.MovieDao;
import com.maddy.moviebooking.util.Async;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements Async.AsyncResponseGet {

    @Inject
    MovieDao movieDao;
    private List<Seats> seatList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppController.getMyApplication().getAppComponent().inject(this);
        setupUI();
        getBookings();
    }

    private void getBookings() {
        Async.GetSeatsTask task = new Async.GetSeatsTask(movieDao, this);
        task.execute();
    }

    private void setupUI() {
        Button bA = findViewById(R.id.b_family_a);
        Button bB = findViewById(R.id.b_family_b);
        Button bC = findViewById(R.id.b_family_c);
        Button bD = findViewById(R.id.b_family_d);
        Button bStatus = findViewById(R.id.b_status);
        Button bDelete = findViewById(R.id.b_delete);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select family");

        bA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBooking("A", 4);
            }
        });

        bB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBooking("B", 7);
            }
        });

        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBooking("C", 3);
            }
        });

        bD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBooking("D", 9);
            }
        });

        bStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBooking("", 0);
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Async.DeleteSeatsTask task = new Async.DeleteSeatsTask(movieDao);
                task.execute();
                seatList = new ArrayList<>();
                Toast.makeText(LoginActivity.this, "Deleted All Bookings", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void startBooking(String family, int size) {
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).getFamily() != null && seatList.get(i).getFamily().equals(family)) {
                Toast.makeText(this, "Already Booked for family " + family, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("family", family);
        i.putExtra("size", size);
        startActivity(i);
        finish();
    }

    @Override
    public void processFinishGet(int status, List<Seats> result) {
        if (status == 1)
            seatList.addAll(result);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
