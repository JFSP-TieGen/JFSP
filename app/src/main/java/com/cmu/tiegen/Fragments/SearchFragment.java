package com.cmu.tiegen.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.QueryInfo;
import com.cmu.tiegen.entity.Service;
import com.cmu.tiegen.remote.ServerConnector;
import com.cmu.tiegen.util.Constants;
import com.cmu.tiegen.views.ServiceListingActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by keerthanathangaraju on 5/4/16.
 */
public class SearchFragment extends ListFragment {
    private List<Service> services;
    private SearchTask mAuthTask = null;
    private ServerConnector serverConnector = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverConnector = new ServerConnector();
        sendRequest();
//        getActivity().setTitle(getString(R.string.scoreTitle));

    }

//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // get the Service from the adapter
//        Service c = ((SearchAdapter)getListAdapter()).getItem(position);
//        // start an instance of StudentPagerActivity
//        Intent i = new Intent(getActivity(), StudentPagerActivity.class);
//        i.putExtra(StudentFragment.EXTRA_CRIME_ID, c.getId());
//        startActivityForResult(i, 0);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((SearchAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class SearchAdapter extends ArrayAdapter<Service> {
        public SearchAdapter(List<Service> scores) {
            super(getActivity(), android.R.layout.simple_list_item_1, scores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.content_service_listing, null);
            }

            // configure the view for this Service
            Service c = getItem(position);

            TextView serviceHeading =
                    (TextView)convertView.findViewById(R.id.service_heading);
            serviceHeading.setText(c.getName());
            TextView price =
                    (TextView)convertView.findViewById(R.id.price);
//            price.setText(c.getPrice());
            RatingBar rate = (RatingBar) convertView.findViewById(R.id.rating);
            rate.setRating(c.getAvgRate());

//            titleEditText.setText(c.getSID());
//            Iterator<Integer> it = c.getScores().getScores().iterator();
//
//            EditText scoreView =
//                    (EditText)convertView.findViewById(R.id.score1);
//            scoreView.setText(it.next()+"");
//            scoreView =
//                    (EditText)convertView.findViewById(R.id.score2);
//            scoreView.setText(it.next()+"");
//            scoreView =
//                    (EditText)convertView.findViewById(R.id.score3);
//            scoreView.setText(it.next()+"");
//            scoreView =
//                    (EditText)convertView.findViewById(R.id.score4);
//            scoreView.setText(it.next()+"");
//            scoreView =
//                    (EditText)convertView.findViewById(R.id.score5);
//            scoreView.setText(it.next()+"");

            return convertView;
        }
    }

    private void sendRequest() {
        QueryInfo query = TieGenApplication.getInstance().getAppContext().getQueryInfo();

        if (mAuthTask != null) {
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user signup attempt.
//            showProgress(true);

        mAuthTask = new SearchTask(query, getActivity());
        mAuthTask.execute((Void) null);

    }

    /**
     * Represents an asynchronous signup/registration task used to authenticate
     * the user.
     */
    public class SearchTask extends AsyncTask<Void, Void, Boolean> {

        private final QueryInfo query;
        private Context mContext;
        private ArrayList<Service> result = null;

        SearchTask(QueryInfo query, Context context) {
            this.query = query;
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                result =  (ArrayList<Service>) serverConnector.sendRequest(Constants.URL_SEARCH_SERVICE, query);
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
                        services = result;
        SearchAdapter adapter = new SearchAdapter(services);
        setListAdapter(adapter);

            } else {
                Toast.makeText(mContext, "No results found!!", Toast.LENGTH_SHORT)
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



