package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.Connection;

/**
 * Created by Avi on 29/04/2015.
 */
public class DAL {
    private DBConnect dbConnect;

    public DAL()
    {
        dbConnect = new DBConnect();
    }

    public Connection getConnection(){
        return dbConnect.getConnection();
    }
}
