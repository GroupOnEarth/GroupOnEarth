package com.grouponearth.avi.grouponearth.BusinessLayer;

<<<<<<< HEAD
=======
import java.io.Serializable;
import java.sql.ResultSet;
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d

/**
 * Created by WIN8 on 30/04/2015.
 */
public interface IBL {

    /**
     * @param userName is the user name to be confirmed
     * @param password is the user's password that is to be confirmed
     * @return the USERTYPE of the confirmed user or an empty string if user does not exist
     */
    public String confirmLogin(String userName, String password);

<<<<<<< HEAD
    //Add Methods
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);


    // Get Methods
    public String getPasswordByMail(String mailAddress);

    // Is Exists Methods
    public boolean isMailExists(String mailAddress);
=======
    public ResultSet getCouponByID(String couponID);
    public void test();
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
}
