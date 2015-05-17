package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UpdateClientListPage extends ActionBarActivity {
    private IBL bl;
    private ResultSet _clientsUsers;
    private ListView _listClients;
    private String[] _clientsDetails;
    private String[] _clientsUserNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_client_list_page);
        bl = new BL();
        initialize();
    }
    private void initialize() {
        _listClients = (ListView) findViewById(R.id.listClients);
        _clientsUsers = bl.getClientUsers();
        setListView();
        couponOnClick();
    }
    private void setListView() {
        try
        {
            _clientsUsers.last();
            int numOfClients;
            if((numOfClients=_clientsUsers.getRow())>0) {
                _clientsDetails = new String[numOfClients];
                _clientsUserNames = new String[numOfClients];
                _clientsUsers.beforeFirst();
                int i=0;
                while (_clientsUsers.next()) {
                    _clientsDetails[i] = _clientsUsers.getString(1) + "\n" + _clientsUsers.getString(2) + " " + _clientsUsers.getString(3);
                    _clientsUserNames[i] = _clientsUsers.getString(1);
                    i++;
                }
            }
            else{
                _clientsDetails = new String[0];
                _clientsUserNames = new String[0];
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_item, _clientsDetails);
            _listClients.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void couponOnClick(){
        _listClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UpdateClientListPage.this, UpdateClientPage.class);
                intent.putExtra("userName", _clientsUserNames[position]);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_client_list_page, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onResume(){
        super.onResume();
        initialize();
    }
}

