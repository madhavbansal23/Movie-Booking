package com.maddy.moviebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maddy.moviebooking.adapter.SeatAdapter;
import com.maddy.moviebooking.model.Seats;
import com.maddy.moviebooking.room.MovieDao;
import com.maddy.moviebooking.util.Async;
import com.maddy.moviebooking.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements Async.AsyncResponseGet, Async.AsyncResponsePost {

    private SeatAdapter adapter;
    private List<Seats> seatList = new ArrayList<>();
    private int familySize = 0;
    private int selected = 0;
    private String familyName;

    @Inject
    MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Select family");
        }

        AppController.getMyApplication().getAppComponent().inject(this);
        setupList();
        setupUI();
    }

    private void setupList() {
        Async.GetSeatsTask task = new Async.GetSeatsTask(movieDao, this);
        task.execute();
    }

    private void setRecyclerView() {
        RecyclerView rvSeats = findViewById(R.id.rv_seats);
        GridLayoutManager gm = new GridLayoutManager(this, 5);
        rvSeats.setLayoutManager(gm);
        adapter = new SeatAdapter(this, seatList);
        rvSeats.setAdapter(adapter);
        adapter.setOnItemClickListener(new SeatAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                seatLogic(position);
            }
        });
    }

    private void setupUI() {

        familySize = getIntent().getIntExtra("size", 0);
        familyName = getIntent().getStringExtra("family");

        Button bAdd = findViewById(R.id.b_add);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected != familySize) {
                    Toast.makeText(MainActivity.this, "Less seats selected", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < seatList.size(); i++) {
                        if (seatList.get(i).getStatus().equals(Constants.BOOKINGSTATUS.SELECTED)) {
                            seatList.get(i).setStatus(Constants.BOOKINGSTATUS.BOOKED);
                            seatList.get(i).setFamily(familyName);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    Async.BookSeatTask task = new Async.BookSeatTask(movieDao, seatList, MainActivity.this);
                    task.execute();
                }
            }
        });
    }

    private void seatLogic(int pos) {
        if (seatList.get(pos).getStatus().equals(Constants.BOOKINGSTATUS.VACANT)) {
            selectAllSeats(pos);
            adapter.notifyDataSetChanged();
        } else if (seatList.get(pos).getStatus().equals(Constants.BOOKINGSTATUS.SELECTED)) {
            unselectSeat(pos);
            adapter.notifyItemChanged(pos);
        } else {
            Toast.makeText(this, "Seat is booked", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectAllSeats(int pos) {
        int row = pos / 5;
        int rowpos = pos % 5;

        for (int j = row; j < 5; j++) {
            if (checkSeatsinFollowingRow(rowpos, j) == familySize) {
                break;
            }
            rowpos = 0;
        }

        for (int j = row - 1; j >= 0; j--) {
            if (checkSeatsinFollowingRow(rowpos, j) == familySize) {
                break;
            }
            rowpos = 0;
        }
    }

    private int checkSeatsinFollowingRow(int rowpos, int j) {
        for (int i = rowpos; i < 5; i++) {
            selected = selectSeat(selected, seatList.get((5 * j) + i));
        }
        for (int i = rowpos - 1; i >= 0; i--) {
            selected = selectSeat(selected, seatList.get((5 * j) + i));
        }
        return selected;
    }

    private int selectSeat(int selected, Seats s) {
        if (s.getStatus().equals(Constants.BOOKINGSTATUS.VACANT) && selected < familySize) {
            s.setStatus(Constants.BOOKINGSTATUS.SELECTED);
            selected++;
        }
        return selected;
    }

    private void unselectSeat(int pos) {
        Seats s = seatList.get(pos);
        s.setStatus(Constants.BOOKINGSTATUS.VACANT);
        selected--;
    }

    @Override
    public void processFinishGet(int status, List<Seats> result) {
        if (status == 0) {
            for (int i = 0; i < 25; i++) {
                Seats s = new Seats();
                s.setId(i);
                s.setSeatNo("S" + i);
                s.setStatus(Constants.BOOKINGSTATUS.VACANT);
                seatList.add(s);
            }
        } else {
            seatList.addAll(result);
        }
        setRecyclerView();
    }

    @Override
    public void processFinishPost(int status, String output) {
        if (status == 0) {
            Toast.makeText(this, "Not updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
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
