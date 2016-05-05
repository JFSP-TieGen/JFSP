package com.cmu.tiegen.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmu.tiegen.R;
import com.cmu.tiegen.TieGenApplication;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.QueryInfo;
import com.cmu.tiegen.exceptions.ExceptionHandler;
import com.cmu.tiegen.util.DataBaseConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DataBaseConnector connector = new DataBaseConnector(this);
    private Intent intent;
    NotificationManager manager;
    Notification myNotication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_side_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

         // notification functions fron here
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Date date = new Date();
        int day =date.getDay()+1;
        int month = date.getMonth();
        int year = date.getYear();
        String d = year+"-"+month+"-"+day;
        Date datenew = null;
        try {
            datenew = new SimpleDateFormat("yyyy-MM-dd").parse(d);
        }catch (ParseException e) {
           e.printStackTrace();
        }
         // 
        //connector.insertBooking(new Booking(1, 2, "test service", datenew));
     
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



    public void sendRequest(String type){
        QueryInfo query = new QueryInfo();
        query.setType(type);
        query.setServiceName("%%");
        query.setLocation("%%");
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
