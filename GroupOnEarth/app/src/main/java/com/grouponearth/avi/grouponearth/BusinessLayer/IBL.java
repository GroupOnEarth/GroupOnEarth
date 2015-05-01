package com.grouponearth.avi.grouponearth.BusinessLayer;


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

    //Add Methods
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);


    // Get Methods
    public String getPasswordByMail(String mailAddress);

    // Is Exists Methods
    public boolean isMailExists(String mailAddress);
}
