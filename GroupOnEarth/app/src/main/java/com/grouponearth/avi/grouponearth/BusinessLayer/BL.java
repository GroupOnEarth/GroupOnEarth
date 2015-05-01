package com.grouponearth.avi.grouponearth.BusinessLayer;

import com.grouponearth.avi.grouponearth.DataLayer.IDAL;

/**
 * Created by Avi on 30/04/2015.
 */
public class BL implements IBL {
    private static IDAL dal;

    public BL()
    {
    }

    public BL(IDAL dal)
    {
        this.dal=dal;
    }

    public String confirmLogin(String userName, String password) {
        return "Admin";
    }


    public void addSystemUser(String _UserName, String _ID, String _Password, String _FirstName, String _LastName, String _Phone, String _EMail, String _UserType)
    {
        dal.addSystemUser( _UserName,_ID,_Password,_FirstName,_LastName,_Phone,_EMail,_UserType);
    }

    public String getPasswordByMail(String mailAddress)
    {
        //todo
        return "";
    }


    public boolean isMailExists(String mailAddress)
    {
    //todo
        return true;
    }



}
