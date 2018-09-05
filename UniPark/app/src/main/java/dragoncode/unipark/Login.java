package dragoncode.unipark;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Login extends Activity {

    private Button btnLogin;
    private EditText txtUserName;
    private EditText txtPassword;
    private TextView showUserName, showPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        else
            connected = false;

        if(connected == false){
            Toast.makeText(Login.this, "Check Internet Connection.", Toast.LENGTH_LONG).show();
            txtUserName.setEnabled(false);
            txtPassword.setEnabled(false);
        }
        else if(connected == true){
            txtUserName.setEnabled(true);
            txtPassword.setEnabled(true);
        }

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        showUserName = (TextView) findViewById(R.id.lblShowUsername);
        showPassword = (TextView) findViewById(R.id.lblShowPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String facilityNo ="s" + txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                JSONObject data = new JSONObject();
                if(!txtUserName.getText().toString().equals("") && !txtPassword.getText().toString().equals("")) {
                    try {
                        data.put("facilityNo", facilityNo);
                        data.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(data.length() > 0){
                    login task = new login();
                    task.execute(String.valueOf(data));
                }
                else if(txtUserName.getText().toString().equals("") && txtPassword.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Please enter user details.", Toast.LENGTH_SHORT).show();
                    showPassword.setVisibility(View.VISIBLE);
                    showUserName.setVisibility(View.VISIBLE);
                }
                else if(txtUserName.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Please enter student/staff number.", Toast.LENGTH_SHORT).show();
                    showUserName.setVisibility(View.VISIBLE);
                    showPassword.setVisibility(View.INVISIBLE);
                }
                else if(txtPassword.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    showPassword.setVisibility(View.VISIBLE);
                    showUserName.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


        class login extends AsyncTask<String, Void, String> {


            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            protected String doInBackground(String... params) {
                String urlstr = getString(R.string.url) + "/personnel/login";
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
                progressBar.setVisibility(View.GONE);
                String response = jsonResponse;
                if(jsonResponse != null){
                    if(response.equals("\"Login Successful!\"")) {
                        openActivity();
                        txtUserName.setText("");
                        txtPassword.setText("");
                        showUserName.setVisibility(View.INVISIBLE);
                        showPassword.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    Toast.makeText(Login.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                    txtUserName.setText("");
                    txtPassword.setText("");
                    showUserName.setVisibility(View.INVISIBLE);
                    showPassword.setVisibility(View.INVISIBLE);
                }
            }
        }

    private void openActivity() {
        String userName = txtUserName.getText().toString();
        Intent intent = new Intent(this, LandingPageActivity.class);
        intent.putExtra("ID", userName);
        startActivity(intent);
    }

}


