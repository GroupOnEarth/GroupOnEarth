package com.grouponearth.avi.grouponearth.DataLayer;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



/**
 * Created by Avi on 29/04/2015.
 */


public class DBConnect {
    private static final String url = "jdbc:mysql://localhost:3306/group_on_earth_db";
    private static final String user = "root";
    private static final String password = "MCSG";
    private static Connection con;



    public DBConnect()
    {

        try
        {
            Connection conn = null;
            try {
                Class.forName ("com.mysql.jdbc.Driver").newInstance();
                con = DriverManager.getConnection(url, user, password);


                conn.close();
            } catch (Exception e)
            {
                Log.d("TXT", "Error");
            }


            //Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection(url, user, password);

        }
        catch(Exception e)
        {
            System.out.println("DBA ACCESS: Exception cought- Connection Error");
        }

    }
    public Connection getConnection(){
        return con;
    }
}
