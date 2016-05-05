package com.cmu.tiegen.views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.Service;
import com.cmu.tiegen.entity.User;
import com.cmu.tiegen.exceptions.ExceptionHandler;
import com.cmu.tiegen.remote.ServerConnector;
import com.cmu.tiegen.util.Constants;
import com.cmu.tiegen.util.DataBaseConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServiceDetailActivity extends AppCompatActivity {



    Spinner timing_spinner ;
    Spinner frequency_spinner;
    DatePicker datePicker;

    Service service;
    private BookTask mAuthTask = null;
    private ServerConnector serverConnector = null;
    private DataBaseConnector connector = new DataBaseConnector(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverConnector = new ServerConnector();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        service = TieGenApplication.getInstance().getAppContext().getService();
        setContentView(R.layout.activity_service_detail);
        TextView serviceHeading =
                (TextView) findViewById(R.id.service_name);
        serviceHeading.setText(service.getName());
        TextView price =
                (TextView) findViewById(R.id.price);
            price.setText("$"+service.getPrice());
//        TextView location =
//                (TextView) findViewById(R.id.location);
//        location.setText(service.getLocation());
        RatingBar rate = (RatingBar) findViewById(R.id.rating);
        rate.setRating(service.getAvgRate());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        timing_spinner = (Spinner) findViewById(R.id.timing_spinner);
        frequency_spinner = (Spinner) findViewById(R.id.frequency_spinner);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.timing_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        timing_spinner.setAdapter(adapter);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        frequency_spinner.setAdapter(adapter2);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DAY_OF_MONTH,1);

        datePicker.setMinDate(c.getTimeInMillis());
        c.add(Calendar.MONTH,6);
        datePicker.setMaxDate(c.getTimeInMillis());
        Button schedule = (Button) findViewById(R.id.schedule_button);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

    }
    private void sendRequest() {
        User user= TieGenApplication.getInstance().getAppContext().getUser();



        String time = timing_spinner.getSelectedItem().toString();
        String freq = frequency_spinner.getSelectedItem().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        Date parsedDate = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            parsedDate = formatter.parse(day+"/"+month+"/"+year+" "+time);
            if (mAuthTask != null) {
                return;
            }

            // Show a progress spinner, and kick off a background task to
            // perform the user signup attempt.
//            showProgress(true);


            Booking booking = new Booking(user.getUserId(),service.getServiceId(),service.getName(),parsedDate);
            booking.setTime(time);
            connector.insertBooking(booking);
            mAuthTask = new BookTask(booking, this);
            mAuthTask.execute((Void) null);
            
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        

    }

    /**
     * Represents an asynchronous signup/registration task used to authenticate
     * the user.
     */
    public class BookTask extends AsyncTask<Void, Void, Boolean> {

        private final Booking booking;
        private Context mContext;
        String result;

        BookTask(Booking booking, Context context) {
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
                TieGenApplication.getInstance().getAppContext().setBooking(booking);
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
