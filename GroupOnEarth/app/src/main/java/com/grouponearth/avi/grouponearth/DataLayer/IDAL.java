package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.ResultSet;

/**
 * Created by Avi on 30/04/2015.
 */
public interface IDAL {
<<<<<<< HEAD

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);

=======
    public boolean addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);

    public ResultSet getSystemUsers();
    public ResultSet getClient(String _UserName);
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
}
