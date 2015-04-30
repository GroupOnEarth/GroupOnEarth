package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Avi on 29/04/2015.
 */
public class DBConnect {
    private static final String url = "jdbc:mysql://localhost:3306/group_on_earth_db";
    private static final String user = "root";
    private static final String password = "groupOnEarth";
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
