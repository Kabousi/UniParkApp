package dragoncode.unipark;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_parking extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private Button btnRequest;
    private Button btnSendRequast;

    private ArrayList<parking_name_item> mParkingNameList;
    private ParkingAdapter mAdapter;

    private EditText txtParkingName;
    private EditText txtParkingAccess;
    private EditText txtLocation;

    private TextView lblAssignedParking;
    private TextView lblRequestParking;
    private Spinner spnParkingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking);

        btnSendRequast = (Button) findViewById(R.id.btnAddRequest);

        lblAssignedParking = (TextView) findViewById(R.id.lblAssignedParking);
        lblRequestParking = (TextView) findViewById(R.id.lblRequestParking);

        spnParkingSpinner = (Spinner) findViewById(R.id.spnParkingName);

        mdrawerlayout = (DrawerLayout) findViewById(R.id.view_parking);
        mToggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.Open, R.string.Close);

        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtParkingName = (EditText) findViewById(R.id.txtParkingName);
        txtParkingName.setEnabled(false);
        txtParkingAccess = (EditText) findViewById(R.id.txtAccessLevel);
        txtParkingAccess.setEnabled(false);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        txtLocation.setEnabled(false);

        btnRequest = (Button) findViewById(R.id.btnRequestParking);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtParkingName.setVisibility(View.INVISIBLE);

                btnRequest.setVisibility(View.INVISIBLE);
                btnSendRequast.setVisibility(View.VISIBLE);

                lblAssignedParking.setVisibility(View.INVISIBLE);
                lblRequestParking.setVisibility(View.VISIBLE);

                spnParkingSpinner.setVisibility(View.VISIBLE);
            }
        });

        InitList();

        mAdapter = new ParkingAdapter(this, mParkingNameList);
        spnParkingSpinner.setAdapter(mAdapter);

        spnParkingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parking_name_item clickeditem = (parking_name_item) parent.getItemAtPosition(position);
                String clickedParking = clickeditem.getparkingNameItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void InitList(){
        mParkingNameList = new ArrayList<>();
        mParkingNameList.add(new parking_name_item("AL001"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
