package com.grouponearth.avi.grouponearth.DataLayer;

import android.os.AsyncTask;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

=======
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d

public class DAL implements IDAL{
    private DBConnect dbConnect;
    private static Connection con;
<<<<<<< HEAD


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
=======

    public DAL()
    {
        AsyncTask at = new DBConnect();
        at.execute();
        Connection contmp = DBConnect.con;
        con = DBConnect.con;
    }

    public boolean addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType)
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
            return addUser.execute();
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    @Override
    public ResultSet getSystemUsers() {
        return null;
    }
    /* The function getClient retrieves information about a given Client.
    *
            * @param _UserName
    *
            * @pre: @param _UserName exists in database.
            * @post: none.
    */
    public ResultSet getClient(String _UserName)
    {
        CallableStatement getC;
        try
        {
            getC = (CallableStatement) con.prepareCall("call get_client (?)");
            getC.setString(1, _UserName);
            return getC.executeQuery();

        }
        catch (SQLException e)
        {
            return null;
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
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
