package com.grouponearth.avi.grouponearth.DataLayer;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class DAL implements IDAL{
    private DBConnect dbConnect;
    private static Connection con;


    public DAL()
    {
        AsyncTask at= new DBConnect();
        at.execute();
        try
        {
            con= (Connection) at.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return dbConnect.getConnection();
    }

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType)
    {
        java.sql.CallableStatement addUser;
        try
        {
            addUser = con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
            addUser.setString(1, _UserName);
            addUser.setString(2, _ID);
            addUser.setString(3, _Password);
            addUser.setString(4, _FirstName);
            addUser.setString(5, _LastName);
            addUser.setString(6, _Phone);
            addUser.setString(7, _EMail);
            addUser.setString(8, _UserType);
            addUser.execute();
        }
        catch (SQLException e)
        {
        }
    }
}
