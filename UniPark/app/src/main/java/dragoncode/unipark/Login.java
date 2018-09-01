package dragoncode.unipark;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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



public class Login extends Activity {

    private Button btnLogin;
    private EditText txtUserName;
    private EditText txtPassword;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;

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

                String facilityNo ="s" + txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                JSONObject data = new JSONObject();

                try{
                    data.put("facilityNo", facilityNo);
                    data.put("password", password);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

                if(data.length() > 0){
                    login task = new login();
                    task.execute(String.valueOf(data));
                }
                else{
                    Toast.makeText(Login.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                }



                //openActivity();
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
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urlstr);
                    urlConnection = (HttpURLConnection) url.openConnection();
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
                if(response.equals("\"Login Successful!\"")) {
                    openActivity();
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


