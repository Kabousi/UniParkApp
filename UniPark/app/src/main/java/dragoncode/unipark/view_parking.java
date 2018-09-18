package dragoncode.unipark;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class view_parking extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nav;

    private Intent intent;

    private Button btnRequest;
    private Button btnMaps;
    private ImageButton ibtnParkingHelp;

    private TextView txtParkingMapsHelp;
    private TextView txtRequestHelp;

    private EditText txtParkingName;
    private EditText txtParkingAccess;
    private EditText txtLocation;

    private String id;
    private String[] details = new String[5];
    private String[] location;

    private int view = 0;

    private Uri gmmIntentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking);

        txtLocation = (EditText)findViewById(R.id.txtReqLocation);
        txtParkingAccess = (EditText)findViewById(R.id.txtAccessLevel);
        txtParkingName = (EditText)findViewById(R.id.txtParkingName);

        txtParkingMapsHelp = (TextView) findViewById(R.id.txtParkingMapsHelp);
        txtRequestHelp = (TextView) findViewById(R.id.txtRequestHelp);

        ibtnParkingHelp = (ImageButton) findViewById(R.id.ibtnParkingHelp);

        txtLocation.setEnabled(false);
        txtParkingName.setEnabled(false);
        txtParkingAccess.setEnabled(false);

        mdrawerlayout = (DrawerLayout) findViewById(R.id.view_parking);
        mToggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.Open, R.string.Close);

        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new ParkingInfo().execute();

        ibtnParkingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view == 0){
                    txtParkingMapsHelp.setVisibility(View.VISIBLE);
                    txtRequestHelp.setVisibility(View.VISIBLE);

                    view = 1;
                }
                else if(view == 1){
                    txtParkingMapsHelp.setVisibility(View.GONE);
                    txtRequestHelp.setVisibility(View.GONE);

                    view = 0;
                }
            }
        });

        btnMaps = (Button) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmmIntentUri = Uri.parse("geo:0,0?q=" + location[0] + "," +location[1] + "(" + txtLocation.getText().toString() + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        btnRequest = (Button) findViewById(R.id.btnRequestParking);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
                txtParkingMapsHelp.setVisibility(View.GONE);
                txtRequestHelp.setVisibility(View.GONE);

                view = 0;
            }
        });

        nav = (NavigationView) findViewById(R.id.nav_ViewParking);
        id  =getIntent().getStringExtra("ID");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_screen:
                        intent = new Intent(view_parking.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.view_profile:
                        intent = new Intent(view_parking.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.view_parking:
                        intent = new Intent(view_parking.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.request_parking:
                        intent = new Intent(view_parking.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.report_user:
                        intent = new Intent(view_parking.this, dragoncode.unipark.ActivityReportUser.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.maps:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.apps.maps");
                        if(intent.resolveActivity(getPackageManager()) != null)
                            startActivity(intent);
                        break;

                    case R.id.logout:
                        intent = new Intent(view_parking.this, Login.class);
                        startActivity(intent);
                        finish();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent = new Intent(view_parking.this, dragoncode.unipark.LandingPageActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        finish();

    }

    class ParkingInfo extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler sh = new HttpHandler();
            String url = getString(R.string.url) + "/parking/assigned/s" + getIntent().getStringExtra("ID");
            String jsonstr = sh.makeServiceCall(url);

                if(jsonstr != null){
                    try{
                        JSONObject object = new JSONObject(jsonstr);
                        details[0] = object.getString("PersonelID");
                        details[1] = object.getString("ParkingName");
                        details[2] = object.getString("ParkingAccessLevel");
                        details[3] = object.getString("Location");
                        details[4] = object.getString("AreaCoordinate");

                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if(result != null){
                txtParkingName.setText(details[1]);
                txtParkingAccess.setText(details[2]);
                txtLocation.setText(details[3]);
                location = details[4].split(",");
            }
            else{
                Toast.makeText(view_parking.this, "No parking assigned to user", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void openActivity(){
        Intent intent = new Intent(this, activity_request_parking.class);
        intent.putExtra("ID", getIntent().getStringExtra("ID"));
        startActivity(intent);
        finish();
    }
}
