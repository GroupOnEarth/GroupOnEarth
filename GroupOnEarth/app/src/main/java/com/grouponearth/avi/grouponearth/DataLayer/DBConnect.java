package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Avi on 29/04/2015.
 */


public class DBConnect {
    private static final String url = "jdbc:mysql://grouponearth.cokwpobid1ly.us-west-2.rds.amazonaws.com:3306/GroupOnEarth";
    private static final String user = "shahaf";
    private static final String password = "grouponearth";
    private static Connection con;



    public DBConnect()
    {

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

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
