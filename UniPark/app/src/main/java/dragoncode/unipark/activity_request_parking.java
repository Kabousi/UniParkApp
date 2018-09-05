package dragoncode.unipark;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarEntry;

import javax.net.ssl.HttpsURLConnection;

public class activity_request_parking extends AppCompatActivity implements OnItemSelectedListener {

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nav;

    private Intent intent;

    private Button btnRequest;
    private Button btnMap;

    private Spinner spnParkingName;
    private Spinner spnLocation;

    private ArrayAdapter<String> mAdapter;

    private String id;
    private String[] pArea;
    private ArrayList<String> location;

    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_parking2);

        btnMap = (Button) findViewById(R.id.btnMaps);
        btnRequest = (Button) findViewById(R.id.btnAddRequest);

        spnParkingName = (Spinner) findViewById(R.id.spnParkingName);
        spnLocation = (Spinner) findViewById(R.id.spnLoaction);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.request_parking);

        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.Open, R.string.Open);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new FillAreaSpinner().execute();

        spnParkingName.setOnItemSelectedListener(this);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String personelID ="s" + getIntent().getStringExtra("ID");
                String parkingSpaceID = spnParkingName.getSelectedItem().toString();
                JSONObject data = new JSONObject();

                try{
                    data.put("PersonelID", personelID);
                    data.put("ParkingSpaceID", parkingSpaceID);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

                if(data.length() > 0){
                    new ParkingRequest().execute(String.valueOf(data));
                }
                else{
                    Toast.makeText(activity_request_parking.this, "Please select a parking space/parking location.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nav = (NavigationView) findViewById(R.id.nav_requestParking);
        id = getIntent().getStringExtra("ID");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_screen:
                        intent = new Intent(activity_request_parking.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_profile:
                        intent = new Intent(activity_request_parking.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_parking:
                        intent = new Intent(activity_request_parking.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.request_parking:
                        intent = new Intent(activity_request_parking.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.report_user:
                        intent = new Intent(activity_request_parking.this, dragoncode.unipark.ActivityReportUser.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.maps:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.apps.maps");
                        if(intent.resolveActivity(getPackageManager()) != null)
                            startActivity(intent);
                        break;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        new FillLocationSpinner().execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class FillAreaSpinner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            String url = getString(R.string.url) + "/parking/request/info/s" + getIntent().getStringExtra("ID");
            String jsonstr = sh.makeServiceCall(url);

            if (jsonstr != null) {
                try {
                    JSONArray jArray =(JSONArray) new JSONTokener(jsonstr).nextValue();

                    pArea = new String[jArray.length()];

                    for(int i =0; i < jArray.length(); i++){
                        JSONObject object = jArray.getJSONObject(i);
                        pArea[i] = object.getString("ParkingArea");
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            for(int i = 1; i < pArea.length; i++){
                if(!pArea[i].equals(pArea[i-1])){
                    count++;
                }
            }
            String[] fill = new String[count];

            int i = 1;

            fill[0] = pArea[0];

                for(int x = 0; x < pArea.length; x++) {
                    if (!fill[i-1].equals(pArea[x])) {
                        fill[i] = pArea[x];
                        i++;
                    }
                }

            mAdapter = new ArrayAdapter<String>(activity_request_parking.this, R.layout.spinner_list, fill);
            mAdapter.setDropDownViewResource(R.layout.spinner_list);
            spnParkingName.setAdapter(mAdapter);
        }
    }

    class FillLocationSpinner extends  AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            String url = getString(R.string.url) + "/parking/request/info/s" + getIntent().getStringExtra("ID");
            String jsonstr = sh.makeServiceCall(url);

            if (jsonstr != null) {
                try {
                    JSONArray jArray =(JSONArray) new JSONTokener(jsonstr).nextValue();
                    location = new ArrayList<String>();
                    location.clear();
                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject object = jArray.getJSONObject(i);
                        if(object.getString("ParkingArea").equals(spnParkingName.getSelectedItem().toString())){
                            location.add(object.getString("ParkingSpace"));
                        }
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
          mAdapter = new ArrayAdapter<String>(activity_request_parking.this, R.layout.spinner_list, location);
            mAdapter.setDropDownViewResource(R.layout.spinner_list);
            spnLocation.setAdapter(mAdapter);
        }
    }

    class ParkingRequest extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String urlstr = getString(R.string.url) + "/request-parking";
            String jsonResponse = null;
            String jsonData = params[0];
            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(urlstr);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(jsonData);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;

                while((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine);

                if (buffer.length() == 0){
                    return null;
                }
                jsonResponse = buffer.toString();
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }
                    catch (final IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse){
            String response = jsonResponse;
            if(response.equals("\" SUCCESS \"")) {
                Toast.makeText(activity_request_parking.this, "Request Successful!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(activity_request_parking.this, "Request Unsuccessful!", Toast.LENGTH_LONG).show();
            }
        }
    }

}