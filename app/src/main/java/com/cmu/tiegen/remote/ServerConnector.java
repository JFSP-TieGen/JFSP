package com.cmu.tiegen.remote;


import com.cmu.tiegen.entity.User;
import com.cmu.tiegen.util.Constants;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnector{
    public Object sendRequest(String urlS, User input){
        HttpURLConnection urlConnection = null;
        try {
            User newUser = new User(input.getUserName(), input.getPassword());
            URL url = new URL(Constants.URL.concat(urlS));

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/octet-stream");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept","*/*");
//            urlConnection.setRe
            ObjectOutputStream out = new ObjectOutputStream(urlConnection.getOutputStream());
            out.writeObject(newUser);
            out.close();
            ObjectInputStream in = new ObjectInputStream(urlConnection.getInputStream());
            Object output = in.readObject();
            in.close();
            return  output;

        }catch(Exception e){
            System.out.println("Exception:"+e.getMessage());
        }
        finally {
            urlConnection.disconnect();
        }
        return null;
    }

    public static void main(String args[]){
        ServerConnector conn = new ServerConnector();
        User user = new User("k@yahoo.com","pwd");
        user = (User) conn.sendRequest(Constants.URL_SIGN_UP,user);
    }
}