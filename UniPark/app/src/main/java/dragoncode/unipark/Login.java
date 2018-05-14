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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends Activity {

    private Button btnLogin;
    private EditText txtUserName;
    private EditText txtPassword;
    private ProgressBar progressBar;

    /*
     * Connection to data base test 1
     *
     * START
     * */

    //Declare connection variables
    private Connection con;
    private String username, password, dbname, ip;
    //End declare connection variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        /*
         * Connection to data base test 1
         **/

        //Set connection variables
        ip = "10.0.0.27";
        dbname = "uniparkDB";
        username = "MicrosoftAccount/k.esterhuizen.ke@gmail.com";
        password = "";
        //End set connection variables

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin check = new checkLogin();
                check.execute("");
            }
        });
    }

    private void openActivity() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
    }

    public class checkLogin extends AsyncTask<String, String, String> {
        private String z = "";
        private Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String userName = txtUserName.getText().toString();
            String passWord = txtPassword.getText().toString();

            if (userName.equals("") || passWord.equals("")) {
                z = "Please enter username and password";
            } else {
                try {
                    con = connectionclass(username, password, dbname, ip);
                    if (con == null) {
                        z = "Check internet connection";
                    } else {
                        String query = "SELECT * FROM Personel WHERE PersonelID ='s" + userName.toString() + "' AND PersonelPassword =" + passWord.toString();
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            openActivity();
                            isSuccess = true;
                            con.close();
                        } else {
                            z = "Incorrect Username/Password";
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Login.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_LONG).show();
            }
        }

    }


    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String pass, String DB, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc,Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + DB + " ;user=" + user + " ;password" + pass + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (ClassNotFoundException ex) {
            Log.e("error here 1", ex.getMessage());
        } catch (SQLException ex) {
            Log.e("error here 2", ex.getMessage());
        } catch (Exception ex) {
            Log.e("error here 3", ex.getMessage());
        }

        return connection;
    }
}
