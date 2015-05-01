package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.ResultSet;

/**
 * Created by Avi on 30/04/2015.
 */
public interface IDAL {
    public boolean addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);

    public ResultSet getSystemUsers();
    public ResultSet getClient(String _UserName);
}
