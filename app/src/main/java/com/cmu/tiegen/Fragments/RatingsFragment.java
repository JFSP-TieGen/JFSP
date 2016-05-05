package com.cmu.tiegen.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.CalendarDay;
import com.cmu.tiegen.entity.Rate;
import com.cmu.tiegen.entity.User;
import com.cmu.tiegen.remote.ServerConnector;
import com.cmu.tiegen.util.Constants;
import com.cmu.tiegen.views.DashboardActivity;
import com.cmu.tiegen.views.ServiceDetailActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by keerthanathangaraju on 5/5/16.
 */
public class RatingsFragment extends ListFragment {
    private List<Booking> bookings;
    private SearchTask mAuthTask = null;
    private RatingsTask rateTask;
    private ServerConnector serverConnector = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverConnector = new ServerConnector();
        sendRequest();
        //getActivity().setTitle(TieGenApplication.getInstance().getAppContext().getQueryInfo().getType()+"..");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((RatingsAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class RatingsAdapter extends ArrayAdapter<Booking> {
        public RatingsAdapter(List<Booking> scores) {
            super(getActivity(), android.R.layout.simple_list_item_1, scores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.content_ratings, null);
            }

            // configure the view for this Booking
            final Booking c = getItem(position);

            TextView serviceHeading =
                    (TextView) convertView.findViewById(R.id.serviceTitle);
            serviceHeading.setText(c.getServiceName());

            final EditText reviewText = (EditText) convertView.findViewById(R.id.review);

            final RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            final Button submit = (Button) convertView.findViewById(R.id.submit_button);
//            ImageView Ratings = (ImageView) convertView.findViewById(R.id.Ratings);

//            Ratings.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    TieGenApplication.getInstance().getAppContext().setService(c);
//                    if (bTask != null) {
//                        return;
//                    }
//                    Ratings bm = new Ratings(c.getServiceId(),TieGenApplication.getInstance().getAppContext().getUser().getUserId());
//
//                    // Show a progress spinner, and kick off a background task to
//                    // perform the user signup attempt.
////            showProgress(true);
//
//                    bTask = new RatingsTask(bm, getActivity());
//                    bTask.execute((Void) null);
//                }
//            });

            final View finalConvertView = convertView;
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String review = reviewText.getText().toString();
                    float rating = ratingBar.getRating();

                    if (rateTask != null) {
                        return;
                    }
                    Rate rate = new Rate();
                    rate.setReview(review);
                    rate.setRate(rating);
                    rate.setUserId(TieGenApplication.getInstance().getAppContext().getUser().getUserId());
                    rate.setOrderId(c.getOrderId());
                    submitRate(finalConvertView, v.getContext(),rate);
                }
            });



//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    TieGenApplication.getInstance().getAppContext().setService(c);
//                    Intent i = new Intent(v.getContext(), ServiceDetailActivity.class);
//                    startActivityForResult(i, 0);
//                }
//            });


//

            return convertView;
        }

    }

    public void submitRate(View v, Context c,Rate rate){

        if (rateTask != null) {
            return;
        }
        rateTask = new RatingsTask(rate, c, v);
        rateTask.execute((Void) null);

    }


    private void sendRequest() {
        User user = TieGenApplication.getInstance().getAppContext().getUser();
        Calendar cal = Calendar.getInstance();
        Date datenew =cal.getTime();
        CalendarDay calday = new CalendarDay(user.getUserId(),datenew);

        if (mAuthTask != null) {
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user signup attempt.
//            showProgress(true);

        mAuthTask = new SearchTask(calday, getActivity());
        mAuthTask.execute((Void) null);

    }

    /**
     * Represents an asynchronous signup/registration task used to authenticate
     * the user.
     */
    public class SearchTask extends AsyncTask<Void, Void, Boolean> {

        private final CalendarDay calendarDay;
        private Context mContext;
        private ArrayList<Booking> result = null;


        SearchTask(CalendarDay calendarDay, Context context) {
            this.calendarDay = calendarDay;
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                result =  (ArrayList<Booking>) serverConnector.sendRequest(Constants.URL_GET_TO_RATE, calendarDay);
                // Simulate network access.
//                Thread.sleep(2000);

            } catch (Exception e) {
                return false;
            }



            if (result.size() > 0) {
                // Account exists, return true if the password matches.
                return true;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
//            showProgress(false);

            if (success) {
                bookings = result;
                if(bookings.size()<1) {
                    Toast.makeText(mContext, "All done!!", Toast.LENGTH_LONG)
                            .show();
                    Intent i = new Intent(mContext, DashboardActivity.class);
                    startActivityForResult(i, 0);
                }
                RatingsAdapter adapter = new RatingsAdapter(bookings);
                setListAdapter(adapter);

            } else {
                Toast.makeText(mContext, "No results found!!", Toast.LENGTH_LONG)
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

    /**
     * Represents an asynchronous signup/registration task used to authenticate
     * the user.
     */
    public class RatingsTask extends AsyncTask<Void, Void, Boolean> {

        private final Rate rate;
        private Context mContext;
        String result;
        private View view;

        RatingsTask(Rate rate, Context context, View v) {
            this.rate = rate;
            this.mContext = context;
            this.view = v;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                result =  (String) serverConnector.sendRequest(Constants.URL_RATE_BOOKING, rate);
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
            rateTask = null;
//            showProgress(false);

            if (success) {
                view.setVisibility(View.GONE);
                Toast.makeText(mContext, "Thankyou!!", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(mContext, "Failed to rate!!", Toast.LENGTH_LONG)
                        .show();
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            rateTask = null;
//            showProgress(false);
        }
    }
}



