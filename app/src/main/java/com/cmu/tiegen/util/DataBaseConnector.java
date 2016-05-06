package com.cmu.tiegen.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.cmu.tiegen.entity.Booking;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fiona on 3/22/16.
 */
public class DataBaseConnector {
    // database name
    private static final String DATABASE_NAME = "new.db";
    private SQLiteDatabase database; // database object
    private DatabaseOpenHelper databaseOpenHelper; // database helper
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // public constructor for DatabaseConnector
    public DataBaseConnector(Context context)
    {
        // create a new DatabaseOpenHelper
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);

    } // end DatabaseConnector constructor

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
        Log.d("database", database.toString());
    } // end method open

    // close the database connection
    public void close()
    {
        if (database != null)
            database.close(); // close the database connection
    } // end method close

    // inserts a new contact in the database
    public void insertBooking(Booking booking) {

        ContentValues b = new ContentValues();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        b.put("sid",booking.getServiceId());
        b.put("service_name",booking.getServiceName());
        b.put("date", df.format(booking.getDate()));
//        m.put("month_pay",mortgage.getMonth_pay());
//        m.put("total_pay",mortgage.getTotal_pay());
//        m.put("payoff_day",df.format(mortgage.getPayoff_date()));


        open(); // open the database
        database.insert("booking", null, b);
        close(); // close the database
    } // end method insertContact



    // return a Cursor with all contact information in the database
    public Cursor getAllBookings(Date date)
    {
        open();

//        return database.query("booking", new String[] {"_id", "sid","service_name","date"},
//                null, null, null, df.format(date), "_id");
        return database.query("booking", new String[]{"_id", "sid", "service_name", "date"}, "date = ?", new String[] {
                df.format(date)},
                null, null,"_id" );
    }

    public void insertImg(String userid , Bitmap img ) {


        byte[] data = getBitmapAsByteArray(img); // this is a function
        ContentValues b = new ContentValues();
        b.put("userid",userid);
        b.put("image", data);

        open(); // open the database
        database.insert("user", null, b);
        close(); // close the database

    }

    public Bitmap getImg(String userid){
        open();
        Cursor cursor = database.query("user", new String[]{"userid", "image"}, "userid = ?", new String[] {
                       userid },
                null, null,"userid" );
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            byte[] img = cursor.getBlob(1);
            return BitmapFactory.decodeByteArray(img, 0, img.length);
        }
        return null;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }




    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the contacts table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // query to create a new table named contacts
            String createQuery = "CREATE TABLE booking ( _id  Integer primary key autoincrement, "+
                    "sid  int (100),"+"service_name  varchar (100),"+"date varchar(100))";
           // Log.d("sql",createQuery);
            db.execSQL(createQuery); // execute the query
            createQuery = "CREATE TABLE user ("+
                    "userid  varchar (100) primary key,"+"image BLOB)";
            db.execSQL(createQuery);
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        } // end method onUpgrade
    } // end class DatabaseOpenHelper
} // end class DatabaseConnector

