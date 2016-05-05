package com.cmu.tiegen.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.CalendarDay;
import com.cmu.tiegen.entity.User;
import com.cmu.tiegen.exceptions.ExceptionHandler;
import com.cmu.tiegen.remote.ServerConnector;
import com.cmu.tiegen.util.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class ViewCalendarActivity extends AppCompatActivity {
    CalendarView calendar ;
    Calendar c = Calendar.getInstance();
    int prevDay = c.get(Calendar.DAY_OF_MONTH);
    int prevMonth = c.get(Calendar.MONTH)+1;
    int prevYear = c.get(Calendar.YEAR);

    private CalendarTask mAuthTask = null;
    private ServerConnector serverConnector = null;
    TextView event ;
    TextView schedule ;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverConnector = new ServerConnector();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_view_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        event = (TextView) findViewById(R.id.events) ;

        schedule = (TextView) findViewById(R.id.calendar_schedule) ;

        calendar = (CalendarView) findViewById(R.id.calendarView);
        if(TieGenApplication.getInstance().getAppContext().getBooking() != null){
            calendar.setDate(TieGenApplication.getInstance().getAppContext().getBooking().getDate().getTime());
            TieGenApplication.getInstance().getAppContext().setBooking(null);
        }
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                boolean changed = changeUpdate(year, month, dayOfMonth);
                month++;
                if (changed) {
                    Toast.makeText(getApplicationContext(),
                            "New Y: " + year + " M: " + month + " D: " + dayOfMonth, Toast.LENGTH_LONG).show();
                    schedule.setText("Schedule for "+ month+"/"+dayOfMonth+"/"+year);
                    sendRequest(month+"/"+dayOfMonth+"/"+year);
                }
            }
        });
        Date calDate = new Date(calendar.getDate());
        sendRequest((calDate.getMonth()+1)+"/"+calDate.getDate()+"/"+prevYear);
    }


        private boolean changeUpdate(int curYear, int curMonth, int curDay) {
            boolean changed = false;
            if (curDay != prevDay || curMonth != prevMonth || curYear != prevYear) {
                changed = true;
            }
            return changed;
        }

    private void sendRequest(String d) {
        User user= TieGenApplication.getInstance().getAppContext().getUser();
        Date selectedDate = new Date(calendar.getDate());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            selectedDate = dateFormat.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            if (mAuthTask != null) {
                return;
            }

            // Show a progress spinner, and kick off a background task to
            // perform the user signup attempt.
//            showProgress(true);
            CalendarDay calendarDay = new CalendarDay();
            calendarDay.setUserId(user.getUserId());
            calendarDay.setDate(selectedDate);

//            Booking booking = new Booking(user.getUserId(),service.getServiceId(),parsedDate);
            mAuthTask = new CalendarTask(calendarDay, this);
            mAuthTask.execute((Void) null);

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

        private final CalendarDay calendarDay;
        private Context mContext;
        CalendarDay result;

        CalendarTask(CalendarDay calendarDay, Context context) {
            this.calendarDay = calendarDay;
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                result =  (CalendarDay) serverConnector.sendRequest(Constants.URL_VIEW_CALENDAR_DAY, calendarDay);
                return true;
                // Simulate network access.
//                Thread.sleep(2000);

            } catch (Exception e) {
                return false;
            }



//            if (result.equals("success")) {
//                // Account exists, return true if the password matches.
//                return true;
//            }else{
//                return false;
//            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
//            showProgress(false);

            if (success) {
                String schedules = "";
                Iterator<Booking> iterator = result.getOrders().iterator();

                while(iterator.hasNext()){
                    Booking booking = iterator.next();
                    schedules += booking.getTime()+" "+booking.getServiceName()+"\n";

                }
                event.setText(schedules);

            } else {
                Toast.makeText(mContext, "Failed to fetch!!", Toast.LENGTH_LONG)
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
