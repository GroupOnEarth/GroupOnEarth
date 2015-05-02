package com.grouponearth.avi.grouponearth.BusinessLayer;

/**
 * Created by Avi on 01/05/2015.
 */
import com.grouponearth.avi.grouponearth.DataLayer.IDAL;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        ResultSet rs = dal.getUser(userName);

        try {
            if(rs.next()){
                String dbUName = rs.getString(1);
                String dbPass = rs.getString(3);
                if(userName.equals(dbUName) & password.equals(dbPass)){
                    return rs.getString(8);
                }
                else return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public ResultSet getCouponByID(String couponID) {
        return null;
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