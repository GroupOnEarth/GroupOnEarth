package com.grouponearth.avi.grouponearth.BusinessLayer;

import android.util.Log;

/**
 * Created by Avi on 30/04/2015.
 */
public class BL implements IBL {
    @Override
    public String confirmLogin(String userName, String password) {
        return "BusinessManager";
    }

    public void test()
    {
        Log.d("gggg", "Hello");
    }
}
