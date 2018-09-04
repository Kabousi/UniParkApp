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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ActivityReportUser extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nav;

    private Button btnReport;
    private Button btnCamera;

    private EditText txtRegNum;
    private EditText txtDesc;

    private String id;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);

        btnReport = (Button) findViewById(R.id.btnSubmitReport);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        txtRegNum = (EditText) findViewById(R.id.txtRegistration);
        txtDesc = (EditText) findViewById(R.id.txtReportDesc);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.report_user_activity);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.Open, R.string.Close);

        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regNum = txtRegNum.getText().toString();
                JSONObject data = new JSONObject();

                try{
                    data.put("LicensePlate", regNum);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

                if(data.length() > 0){
                    new GetPerson().execute(String.valueOf(data));
                }
                else{
                    Toast.makeText(ActivityReportUser.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                }
                //async vir add report
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //camera code
            }
        });

        nav = (NavigationView) findViewById(R.id.nav_reportUser);
        id = getIntent().getStringExtra("ID");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_screen:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_profile:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_parking:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.request_parking:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.report_user:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.ActivityReportUser.class);
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

        if (mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    class GetPerson extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String urlstr = getString(R.string.url) + "/personnel/login";     /////////////////////////////////////////Change URL
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
            String regNumber = txtRegNum.getText().toString();
            String desc = txtDesc.getText().toString();
            String Type = "1";
            JSONObject data = new JSONObject();

            try{
                data.put("PersonnelID", response);
                data.put("LicensePlate", regNumber);
                data.put("Description", desc);
                data.put("Type", Type);
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            if(data.length() > 0){
                new AddReport().execute(String.valueOf(data));
            }
            }
        }

    class AddReport extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String urlstr = getString(R.string.url) + "/personnel/login";          //////////////////////////////Change URL
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
            if(jsonResponse.equals("SUCCESS")){
                txtDesc.setText("");
                txtRegNum.setText("");
                Toast.makeText(ActivityReportUser.this, "Report Successful!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ActivityReportUser.this, "Report not successful!", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

