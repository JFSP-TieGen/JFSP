package com.cmu.tiegen.views;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.QueryInfo;
import com.cmu.tiegen.exceptions.ExceptionHandler;
import com.cmu.tiegen.util.DataBaseConnector;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DataBaseConnector connector = new DataBaseConnector(this);
    private Intent intent;
    NotificationManager manager;
    Notification myNotication;
    SearchView search;
    LocationManager locationManager;
    Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_side_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search = (SearchView) findViewById(R.id.searchView);

        ImageView findByLocation = (ImageView) findViewById(R.id.findByLoc);
        findByLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });




        Button food = (Button) findViewById(R.id.food_button);
        Button health = (Button) findViewById(R.id.health_button);
        Button house_keeping = (Button) findViewById(R.id.housekeeping_button);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("food");
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("health");
            }
        });
        house_keeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("housekeeping");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem item = navigationView.getMenu().findItem(R.id.nav_user);
        item.setTitle(TieGenApplication.getInstance().getAppContext().getUser().getUserName());

        Bitmap bm = TieGenApplication.getInstance().getAppContext().getImg();
             bm =   bm == null? connector.getImg(TieGenApplication.getInstance().getAppContext().getUser().getUserName()):bm;
        bm =  bm == null?BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_menu_user):bm;
        ImageView pp = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profilepic);
        pp.setImageBitmap(bm);
//        item.setTitle(TieGenApplication.getInstance().getAppContext().getUser().getUserName());


        // notification functions fron here
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Calendar cal = Calendar.getInstance();
        Date datenew =cal.getTime();

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();
                sendRequestSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

//                Toast.makeText(getBaseContext(), newText,
//                Toast.LENGTH_SHORT).show();
//                sendRequestSearch(newText);

                return false;
            }

        });


         //
//        connector.insertBooking(new Booking(1, 2, "test service", datenew));

        Cursor c = connector.getAllBookings(datenew);

        if(c.getCount()!=0) {

            Intent intent = new Intent(this,ViewCalendarActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(DashboardActivity.this, 1, intent, 0);

            Notification.Builder builder = new Notification.Builder(DashboardActivity.this);

            builder.setAutoCancel(false);
            builder.setTicker("this is ticker text");
            builder.setContentTitle("Tiegen Notification");
            builder.setContentText("You have incoming booking! ");
            builder.setSmallIcon(R.drawable.tiegen_logo);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            c.moveToFirst();
            builder.setSubText(c.getString(c.getColumnIndex("service_name")));   //API level 16
            builder.setNumber(c.getCount());

            builder.build();

            myNotication = builder.getNotification();
            manager.notify(11, myNotication);

        }

    }

    public Location getLocation() {
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationManager = (LocationManager)getSystemService(
                    Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(criteria, true);

            LocationListener myLocationListener= new LocationListener() {
                @Override
                public void onLocationChanged(Location loca) {
                    location = loca;
                    dispLoc();
                    Log.d("dj","on location changed: "+location.getLatitude()+" & "+location.getLongitude());
//                    toastLocation(location);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(provider, 1000, (float) 0.0, myLocationListener);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
//            location  = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//                System.out.println(addresses.get(0).getLocality());
//            if (location == null) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L,
//                        1.0, this );

                Log.d("GPS Enabled", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

//                    if (location != null) {
//                        latitude = location.getLatitude();
//                        longitude = location.getLongitude();
//                        sendSMSMessage("Latitude:" + latitude + ", Longitude:" + longitude);
//                    }
                dispLoc();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        txtLat = (TextView) findViewById(R.id.textview1);
//        txtLat = (TextView) findViewById(R.id.textview1);
//        txtLat.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        //sendSMSMessage("Latitude:" + latitude + ", Longitude:" + longitude);
        return location;
    }

    public void dispLoc(){
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            sendRequestSearch(addresses.get(0).getLocality());
            if (addresses.size() > 0)
                Toast.makeText(getBaseContext(), addresses.get(0).getLocality(),
                        Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendRequest(String type){
        QueryInfo query = new QueryInfo();
        query.setType(type);
        query.setServiceName("");
        query.setLocation("");
        TieGenApplication.getInstance().getAppContext().setQueryInfo(query);

        Intent i = new Intent(this, ServiceListingActivity.class);
        startActivityForResult(i, 0);

    }

    public void sendRequestSearch(String str){
        QueryInfo query = new QueryInfo();
        query.setType("%"+str+"%");
        query.setServiceName("%"+str+"%");
        query.setLocation("%"+str+"%");
        TieGenApplication.getInstance().getAppContext().setQueryInfo(query);

        Intent i = new Intent(this, ServiceListingActivity.class);
        startActivityForResult(i, 0);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmark) {
            Toast.makeText(DashboardActivity.this, "" + "BookMark", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, BookMarkActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        } else if (id == R.id.nav_notifications) {
            Toast.makeText(DashboardActivity.this, "" + "RateAndReview", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, RateAndReviewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else if (id == R.id.nav_calendar) {
            Toast.makeText(DashboardActivity.this, "" + "ViewCalendar", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, ViewCalendarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Toast.makeText(DashboardActivity.this, "" + "LogOut", Toast.LENGTH_SHORT).show();
            TieGenApplication.getInstance().getAppContext().setUser(null);
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}





//private Intent intent;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        Button food = (Button)findViewById(R.id.food_button);
//        Button health = (Button)findViewById(R.id.health_button);
//        Button house_keeping=(Button)findViewById(R.id.housekeeping_button);
//        food.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), ServiceListingActivity.class);
//                startActivityForResult(i, 0);
//            }
//        });
//        health.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), ServiceListingActivity.class);
//                startActivityForResult(i, 0);
//            }
//        });
//        house_keeping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), ServiceListingActivity.class);
//                startActivityForResult(i, 0);
//            }
//        });
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu., menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.bookmark:
//                Toast.makeText(DashboardActivity.this, "" + "BookMark", Toast.LENGTH_SHORT).show();
//                intent = new Intent(this,BookMarkActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//
//                break;
//
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
