package com.maddy.moviebooking.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.maddy.moviebooking.R;
import com.maddy.moviebooking.model.Seats;
import com.maddy.moviebooking.util.Constants;

import java.util.List;

public class SeatAdapter extends
        RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    private List<Seats> seatList;
    private final Context context;
    private MyClickListener myClickListener;

    public SeatAdapter(Context context, List<Seats> seatList) {
        this.context = context;
        this.seatList = seatList;
    }

    @NonNull
    @Override
    public SeatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                     int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_seat, parent, false);
        return new ViewHolder(itemLayoutView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        final Seats seat = seatList.get(position);
        viewHolder.tvSeat.setText(seat.getSeatNo());

        switch (seat.getStatus()) {
            case Constants.BOOKINGSTATUS.VACANT:
                viewHolder.tvSeat.setBackgroundColor(ContextCompat.getColor(context, R.color.vacant));
                break;
            case Constants.BOOKINGSTATUS.BOOKED:
                viewHolder.tvSeat.setBackgroundColor(ContextCompat.getColor(context, R.color.booked));
                break;
            case Constants.BOOKINGSTATUS.SELECTED:
                viewHolder.tvSeat.setBackgroundColor(ContextCompat.getColor(context, R.color.selected));
                break;
        }

        viewHolder.tvSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myClickListener != null) {
                    myClickListener.onItemClick(viewHolder.getAdapterPosition(), v);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvSeat;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvSeat = itemLayoutView.findViewById(R.id.tv_seat);
        }

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

}