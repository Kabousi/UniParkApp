package dragoncode.unipark;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dragoncode.unipark.LandingPageActivity;


public class Login extends Activity {

    private Button btnLogin;
    private EditText txtUserName;
    private EditText txtPassword;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;

    String userName;
    String password;

    //String API_URL = "Localhost:9000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        requestQueue = Volley.newRequestQueue(this);

        progressBar.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login task = new login();
                task.execute();
            }
        });
    }
    private void openActivity() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
    }

        class login extends AsyncTask<Void, Void, String> {

            private Exception exception;

            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            protected String doInBackground(Void... urls) {

                try {
                    URL url = new URL( "http://10.0.2.2:9000/personnel/login/s" + txtUserName.getText().toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        return stringBuilder.toString();
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            protected void onPostExecute(String response) {
                if(response == null) {
                    response = "THERE WAS AN ERROR";
                }
                else if(response != null){
                    try{
                        JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                        JSONArray objarry = object.getJSONArray("PersonnelPassword");
                        password = objarry.getString(0);
                    }
                    catch (JSONException e){
                        Log.e("Error", e.getMessage());
                    }

                    if(password == txtPassword.getText().toString()){
                        openActivity();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        }


}


