package com.cmu.tiegen.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.BookMark;
import com.cmu.tiegen.entity.QueryInfo;
import com.cmu.tiegen.entity.Service;
import com.cmu.tiegen.entity.User;
import com.cmu.tiegen.remote.ServerConnector;
import com.cmu.tiegen.util.Constants;
import com.cmu.tiegen.views.ServiceDetailActivity;
import com.cmu.tiegen.views.ServiceListingActivity;
import com.cmu.tiegen.views.ViewCalendarActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by keerthanathangaraju on 5/5/16.
 */
public class BookmarkFragment extends ListFragment {
    private List<Service> services;
    private SearchTask mAuthTask = null;
    private BookmarkTask bTask;
    private ServerConnector serverConnector = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverConnector = new ServerConnector();
        sendRequest();
        getActivity().setTitle(TieGenApplication.getInstance().getAppContext().getQueryInfo().getType()+"..");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((BookmarkAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class BookmarkAdapter extends ArrayAdapter<Service> {
        public BookmarkAdapter(List<Service> scores) {
            super(getActivity(), android.R.layout.simple_list_item_1, scores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.content_bookmark_listing, null);
            }

            // configure the view for this Service
            final Service c = getItem(position);

            TextView serviceHeading =
                    (TextView) convertView.findViewById(R.id.service_heading);
            serviceHeading.setText(c.getName());
            TextView price =
                    (TextView) convertView.findViewById(R.id.price);
            price.setText("$"+c.getPrice());
            TextView location =
                    (TextView) convertView.findViewById(R.id.location);
            location.setText(c.getLocation());
            RatingBar rate = (RatingBar) convertView.findViewById(R.id.rating);
            rate.setRating(c.getAvgRate());
            Button schedule = (Button) convertView.findViewById(R.id.schedule_button);
//            ImageView bookmark = (ImageView) convertView.findViewById(R.id.bookmark);

//            bookmark.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    TieGenApplication.getInstance().getAppContext().setService(c);
//                    if (bTask != null) {
//                        return;
//                    }
//                    BookMark bm = new BookMark(c.getServiceId(),TieGenApplication.getInstance().getAppContext().getUser().getUserId());
//
//                    // Show a progress spinner, and kick off a background task to
//                    // perform the user signup attempt.
////            showProgress(true);
//
//                    bTask = new BookmarkTask(bm, getActivity());
//                    bTask.execute((Void) null);
//                }
//            });

            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ServiceDetailActivity.class);
                    TieGenApplication.getInstance().getAppContext().setService(c);

                    startActivityForResult(i, 0);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TieGenApplication.getInstance().getAppContext().setService(c);
                    Intent i = new Intent(v.getContext(), ServiceDetailActivity.class);
                    startActivityForResult(i, 0);
                }
            });


//


            return convertView;
        }

    }

    private void sendRequest() {
       User user = TieGenApplication.getInstance().getAppContext().getUser();

        if (mAuthTask != null) {
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user signup attempt.
//            showProgress(true);

        mAuthTask = new SearchTask(user, getActivity());
        mAuthTask.execute((Void) null);

    }

    /**
     * Represents an asynchronous signup/registration task used to authenticate
     * the user.
     */
    public class SearchTask extends AsyncTask<Void, Void, Boolean> {

        private final User user;
        private Context mContext;
        private ArrayList<Service> result = null;

        SearchTask(User user, Context context) {
            this.user = user;
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                result =  (ArrayList<Service>) serverConnector.sendRequest(Constants.URL_DISPLAY_BOOKMARKS, user);
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
                BookmarkAdapter adapter = new BookmarkAdapter(services);
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
    public class BookmarkTask extends AsyncTask<Void, Void, Boolean> {

        private final BookMark bookMark;
        private Context mContext;
        private String result = null;

        BookmarkTask(BookMark bookMark, Context context) {
            this.bookMark = bookMark;
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                result =  (String) serverConnector.sendRequest(Constants.URL_ADD_BOOKMARK, bookMark);
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
                Toast.makeText(mContext, "Added to your bookmarks!", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(mContext, "Failed to bookmark!!", Toast.LENGTH_LONG)
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



