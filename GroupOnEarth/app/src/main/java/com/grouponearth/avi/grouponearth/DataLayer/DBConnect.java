package com.grouponearth.avi.grouponearth.DataLayer;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;


class DBConnect extends AsyncTask {
    private static final String url = "jdbc:mysql://grouponearth.cokwpobid1ly.us-west-2.rds.amazonaws.com:3306/GroupOnEarth";
    private static final String user = "shahaf";
    private static final String password = "grouponearth";
    public static Connection con;

    protected Object doInBackground(Object[] params) {
        try
        {
            try {
                System.out.println("Hello");
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                Connection tmp = con;
                Log.d("TXT", "*******here");

            } catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        catch(Exception e)
        {
            System.out.println("DBA ACCESS: Exception cought- Connection Error");
        }
        return con;
    }

    public Connection getConnection()
    {
        return con;
    }



}
