package com.grouponearth.avi.grouponearth.BusinessLayer;

import android.util.Log;

import java.sql.ResultSet;

/**
 * Created by Avi on 30/04/2015.
 */
public class BL implements IBL {
    @Override
    public String confirmLogin(String userName, String password) {

        return "Client";
    }

    @Override
    public ResultSet getCouponByID(String couponID) {
        return null;
    }

    public void test()
    {
        Log.d("gggg", "Hello");
    }
}
