package com.cmu.tiegen;

import android.content.Context;

import com.cmu.tiegen.entity.BookMark;
import com.cmu.tiegen.entity.Booking;
import com.cmu.tiegen.entity.QueryInfo;
import com.cmu.tiegen.entity.Service;
import com.cmu.tiegen.entity.User;

/**
 * Created by keerthanathangaraju on 5/4/16.
 */
public class AppContext {
    private Context mContext;
    private User user;
    private Service service;
    private QueryInfo queryInfo;
    private Booking booking;
    private BookMark bookmark;

    AppContext(Context context){
        mContext = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public QueryInfo getQueryInfo() {
        return queryInfo;
    }

    public void setQueryInfo(QueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BookMark getBookmark() {
        return bookmark;
    }

    public void setBookmark(BookMark bookmark) {
        this.bookmark = bookmark;
    }
}
