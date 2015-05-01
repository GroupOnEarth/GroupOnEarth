package com.grouponearth.avi.grouponearth.DataLayer;
<<<<<<< HEAD

=======
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
import android.os.AsyncTask;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
<<<<<<< HEAD


=======
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
class DBConnect extends AsyncTask {
    private static final String url = "jdbc:mysql://grouponearth.cokwpobid1ly.us-west-2.rds.amazonaws.com:3306/GroupOnEarth";
    private static final String user = "shahaf";
    private static final String password = "grouponearth";
    public static Connection con;
<<<<<<< HEAD

=======
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
    protected Object doInBackground(Object[] params) {
        try
        {
            try {
                System.out.println("Hello");
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                Connection tmp = con;
<<<<<<< HEAD
                Log.d("TXT", "*******here");

=======

                Log.d("TXT", "*******here");
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
            } catch (Exception e)
            {
                e.printStackTrace();
            }
<<<<<<< HEAD


=======
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
        }
        catch(Exception e)
        {
            System.out.println("DBA ACCESS: Exception cought- Connection Error");
        }
        return con;
    }
<<<<<<< HEAD

=======
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
    public Connection getConnection()
    {
        return con;
    }
<<<<<<< HEAD



}
=======
}
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
