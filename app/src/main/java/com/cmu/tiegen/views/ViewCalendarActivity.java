package com.cmu.tiegen.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CalendarView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.CalendarDay;
import com.cmu.tiegen.entity.User;
import com.cmu.tiegen.exceptions.ExceptionHandler;
import com.cmu.tiegen.remote.ServerConnector;
import com.cmu.tiegen.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewCalendarActivity extends AppCompatActivity {
    CalendarView calendar ;
    Calendar c = Calendar.getInstance();
    int prevDay = c.get(Calendar.DAY_OF_MONTH);
    int prevMonth = c.get(Calendar.MONTH);
    int prevYear = c.get(Calendar.YEAR);

    private CalendarTask mAuthTask = null;
    private ServerConnector serverConnector = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverConnector = new ServerConnector();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_view_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                boolean changed = changeUpdate(year, month, dayOfMonth);
                if (changed) {
                    Toast.makeText(getApplicationContext(),
                            "New Y: " + year + " M: " + month + " D: " + dayOfMonth, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


        private boolean changeUpdate(int curYear, int curMonth, int curDay) {
            boolean changed = false;
            if (curDay != prevDay || curMonth != prevMonth || curYear != prevYear) {
                changed = true;
            }
            return changed;
        }

    private void sendRequest() {
        User user= TieGenApplication.getInstance().getAppContext().getUser();
        Date selectedDate = new Date(calendar.getDate());

        try {
            if (mAuthTask != null) {
                return;
            }

            // Show a progress spinner, and kick off a background task to
            // perform the user signup attempt.
//            showProgress(true);
            CalendarDay calendarDay = new CalendarDay();
            calendarDay.setUserId(1);
            calendarDay.setDate(selectedDate);

//            Booking booking = new Booking(user.getUserId(),service.getServiceId(),parsedDate);
//            mAuthTask = new CalendarTask(booking, this);
//            mAuthTask.execute((Void) null);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


    }

    /**
     * Represents an asynchronous signup/registration task used to authenticate
     * the user.
     */
    public class CalendarTask extends AsyncTask<Void, Void, Boolean> {

        private final Booking booking;
        private Context mContext;
        String result;

        CalendarTask(Booking booking, Context context) {
            this.booking = booking;
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                result =  (String) serverConnector.sendRequest(Constants.URL_BOOK_SERVICE, booking);
                // Simulate network access.
//                Thread.sleep(2000);

            } catch (Exception e) {
                return false;
            }



            if (result.equals("success")) {
                // Account exists, return true if the password matches.
                return true;
            }else{
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
//            showProgress(false);

            if (success) {
                Intent i = new Intent(mContext, ViewCalendarActivity.class);
                startActivityForResult(i, 0);
            } else {
                Toast.makeText(mContext, "Failed to book!!", Toast.LENGTH_LONG)
                        .show();
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//            showProgress(false);
        }
    }




}
