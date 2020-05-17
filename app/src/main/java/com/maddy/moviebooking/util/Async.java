package com.maddy.moviebooking.util;

import android.os.AsyncTask;

import com.maddy.moviebooking.model.Seats;
import com.maddy.moviebooking.room.MovieDao;

import java.util.List;


public class Async {

    public static class BookSeatTask extends AsyncTask<Void, Void, Boolean> {

        AsyncResponsePost delegate;
        private MovieDao dao;
        private List<Seats> seatList;

        public BookSeatTask(MovieDao dao, List<Seats> seatList, AsyncResponsePost asyncResponse) {
            delegate = asyncResponse;
            this.dao = dao;
            this.seatList = seatList;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean needsUpdate = false;
            for (Seats item : seatList) {
                long inserted = dao.insertEntry(item);
                if (inserted == -1) {
                    long updated = dao.insertEntry(item);
                    if (updated > 0) {
                        needsUpdate = true;
                    }
                } else {
                    needsUpdate = true;
                }
            }
            return needsUpdate;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                delegate.processFinishPost(0, null);
            } else {
                delegate.processFinishPost(1, null);
            }
        }
    }

    public static class GetSeatsTask extends AsyncTask<Void, Void, List<Seats>> {

        AsyncResponseGet delegate;
        private MovieDao dao;

        public GetSeatsTask(MovieDao dao, AsyncResponseGet asyncResponse) {
            delegate = asyncResponse;
            this.dao = dao;
        }

        @Override
        protected List<Seats> doInBackground(Void... params) {
            return dao.getAllSeats();
        }

        @Override
        protected void onPostExecute(List<Seats> result) {
            if (result == null) {
                delegate.processFinishGet(0, null);
            } else if (result.size() == 0) {
                delegate.processFinishGet(0, null);
            } else {
                delegate.processFinishGet(1, result);
            }
        }
    }

    public static class DeleteSeatsTask extends AsyncTask<Void, Void, Boolean> {
        private MovieDao dao;
        public DeleteSeatsTask(MovieDao dao) {
            this.dao = dao;
        }
        @Override
        protected Boolean doInBackground(Void... a) {
            dao.deleteTable();
            return true;
        }
    }


    public interface AsyncResponseGet {
        void processFinishGet(int status, List<Seats> result);
    }

    public interface AsyncResponsePost {
        void processFinishPost(int status, String output);
    }

}
