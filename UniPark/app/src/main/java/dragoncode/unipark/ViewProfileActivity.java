package dragoncode.unipark;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class ViewProfileActivity extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private Intent intent;

    private String id;
    private String name, email, phoneNummber, employeeNum;

    private EditText EmpNum;
    private EditText Name;
    private EditText Email;
    private EditText PhoneNum;

    private TextView lblShowEmail, lblEmailRequired;
    private TextView lblShowPhone, lblPhoneRequired;
    private TextView lblShowRequired;

    private Button btnEditProfile;

    private NavigationView nav;

    private String[] details = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        new GetPersonDetails().execute();

        lblShowPhone = (TextView) findViewById(R.id.lblShowPhone);
        lblShowEmail = (TextView) findViewById(R.id.lblShowEmail);
        lblEmailRequired = (TextView) findViewById(R.id.lblEmailRequired);
        lblPhoneRequired = (TextView) findViewById(R.id.lblPhoneRequired);
        lblShowRequired = (TextView) findViewById(R.id.lblShowRequired);

        lblEmailRequired.setVisibility(View.INVISIBLE);
        lblPhoneRequired.setVisibility(View.INVISIBLE);
        lblShowRequired.setVisibility(View.INVISIBLE);
        lblShowPhone.setVisibility(View.INVISIBLE);
        lblShowEmail.setVisibility(View.INVISIBLE);
        lblShowPhone.setVisibility(View.INVISIBLE);

        mdrawerlayout = (DrawerLayout) findViewById(R.id.view_profile);
        mToggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.Open, R.string.Close);

        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEditProfile = (Button) findViewById(R.id.btn_editProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lblShowEmail.setText("Invalid Format*");

                if(btnEditProfile.getText().equals("Edit Profile")){
                    Email = (EditText) findViewById(R.id.txtEmail);
                    Email.setEnabled(true);
                    PhoneNum = (EditText) findViewById(R.id.txtPhoneNum);
                    PhoneNum.setEnabled(true);

                    lblEmailRequired.setVisibility(View.VISIBLE);
                    lblPhoneRequired.setVisibility(View.VISIBLE);
                    lblShowRequired.setVisibility(View.VISIBLE);

                    btnEditProfile.setText("Update");
                }
                else if(btnEditProfile.getText().equals("Update")){
                    Email = (EditText) findViewById(R.id.txtEmail);
                    PhoneNum = (EditText) findViewById(R.id.txtPhoneNum);
                    PhoneNum.setEnabled(false);
                    Email.setEnabled(false);

                    lblShowPhone.setVisibility(View.INVISIBLE);
                    lblShowEmail.setVisibility(View.INVISIBLE);

                    name = Name.getText().toString();
                    email = Email.getText().toString();
                    phoneNummber = PhoneNum.getText().toString();
                    employeeNum = "s" + getIntent().getStringExtra("ID");
                    JSONObject data = new JSONObject();

                    String[] parts = email.split("@");

                    Pattern p;
                    Matcher m;

                    p = Pattern.compile("[^0-9]");
                    m = p.matcher(PhoneNum.getText().toString());
                    boolean ppart = m.find();

                    if(parts.length > 1){
                        p = Pattern.compile("[^A-Za-z0-9.]");
                        m = p.matcher(parts[0]);
                        boolean fpart = m.find();
                        m = p.matcher(parts[1]);
                        boolean spart = m.find();

                        if(fpart == true || spart == true){
                            lblShowEmail.setVisibility(View.VISIBLE);
                            Toast.makeText(ViewProfileActivity.this, "Invalid email format!", Toast.LENGTH_LONG).show();
                            PhoneNum.setEnabled(true);
                            Email.setEnabled(true);
                            lblShowEmail.setText("Invalid Format*");
                        }
                        else if(email.length() == 0){
                            Toast.makeText(ViewProfileActivity.this, "Please enter Email.", Toast.LENGTH_LONG).show();
                            lblEmailRequired.setVisibility(View.VISIBLE);
                            PhoneNum.setEnabled(true);
                            Email.setEnabled(true);
                            lblShowEmail.setText("Enter Email*");
                            lblShowEmail.setVisibility(View.VISIBLE);
                        }
                        else if(fpart != true && spart != true && email.length() > 0){
                            lblEmailRequired.setVisibility(View.INVISIBLE);
                            lblShowEmail.setVisibility(View.INVISIBLE);
                            lblShowEmail.setText("Invalid Format*");
                        }

                        if(ppart == true){
                                lblShowPhone.setVisibility(View.VISIBLE);
                                Toast.makeText(ViewProfileActivity.this, "Invalid Phone Number Format!", Toast.LENGTH_LONG).show();
                                PhoneNum.setEnabled(true);
                                Email.setEnabled(true);
                            }
                            else if(phoneNummber.length() == 0){
                                Toast.makeText(ViewProfileActivity.this, "Please enter Contact number.", Toast.LENGTH_LONG).show();
                                lblPhoneRequired.setVisibility(View.VISIBLE);
                                PhoneNum.setEnabled(true);
                                Email.setEnabled(true);
                            }
                            else if(phoneNummber.length() > 10){
                                Toast.makeText(ViewProfileActivity.this, "Contact number too Long. Please Enter Contact number.", Toast.LENGTH_LONG).show();
                                lblShowPhone.setVisibility(View.VISIBLE);
                                PhoneNum.setEnabled(true);
                                Email.setEnabled(true);
                            }
                            else if(phoneNummber.length() < 10){
                                Toast.makeText(ViewProfileActivity.this, "Contact number too Short. Please Enter Contact number.", Toast.LENGTH_LONG).show();
                                lblShowPhone.setVisibility(View.VISIBLE);
                                PhoneNum.setEnabled(true);
                                Email.setEnabled(true);
                            }
                            else{
                                lblPhoneRequired.setVisibility(View.INVISIBLE);
                                lblShowPhone.setVisibility(View.INVISIBLE);
                                try{
                                    data.put("PersonnelID", employeeNum);
                                    data.put("PersonnelPhoneNumber", phoneNummber);
                                    data.put("PersonnelEmail", email);
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }

                                if(data.length() > 0){
                                    new UpdateUserInfo().execute(String.valueOf(data));
                                }
                                else{
                                    Toast.makeText(ViewProfileActivity.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                                }
                            }
                    }
                    else{
                        Toast.makeText(ViewProfileActivity.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                        lblShowEmail.setVisibility(View.VISIBLE);
                        PhoneNum.setEnabled(true);
                        Email.setEnabled(true);
                    }
                }
            }
        });

        EmpNum = (EditText) findViewById(R.id.txtEmpNum);
        EmpNum.setEnabled(false);
        Name = (EditText) findViewById(R.id.txtName);
        Name.setEnabled(false);
        Email = (EditText) findViewById(R.id.txtEmail);
        Email.setEnabled(false);
        PhoneNum = (EditText) findViewById(R.id.txtPhoneNum);
        PhoneNum.setEnabled(false);

        nav = (NavigationView) findViewById(R.id.nav_ViewProfile);
        id = getIntent().getStringExtra("ID");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_screen:
                        intent = new Intent(ViewProfileActivity.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_profile:
                        intent = new Intent(ViewProfileActivity.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_parking:
                        intent = new Intent(ViewProfileActivity.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.request_parking:
                        intent = new Intent(ViewProfileActivity.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.report_user:
                        intent = new Intent(ViewProfileActivity.this, dragoncode.unipark.ActivityReportUser.class);
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

    class GetPersonDetails extends AsyncTask<Void, Void, Void> {
        private Exception exception;

        @Override
        protected Void doInBackground(Void... arg0) {

                HttpHandler sh = new HttpHandler();
                String url = getString(R.string.url) + "/personnel/specified/s" + getIntent().getStringExtra("ID");
                String jsonstr = sh.makeServiceCall(url);

                if (jsonstr != null) {
                    try {
                        JSONObject object = new JSONObject(jsonstr);
                        details[0] = object.getString("PersonelName");
                        details[1] = object.getString("PhoneNumber");
                        details[2] = object.getString("Email");
                        details[3] = object.getString("Type");
                        details[4] = object.getString("PersonelLevel");
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
            Name.setText(details[0]);
            PhoneNum.setText(details[1]);
            Email.setText(details[2]);
            EmpNum.setText(getIntent().getStringExtra("ID"));
        }
    }

    class UpdateUserInfo extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String urlstr = getString(R.string.url) + "/personnel/update";
            String jsonResponse = null;
            String jsonData = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(urlstr);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);

                urlConnection.setRequestMethod("PUT");
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
            Email = (EditText) findViewById(R.id.txtEmail);
            Email.setEnabled(false);
            PhoneNum = (EditText) findViewById(R.id.txtPhoneNum);
            PhoneNum.setEnabled(false);
            lblShowRequired.setVisibility(View.INVISIBLE);
            btnEditProfile.setText("Edit Profile");
            Toast.makeText(ViewProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
        }
    }
}

