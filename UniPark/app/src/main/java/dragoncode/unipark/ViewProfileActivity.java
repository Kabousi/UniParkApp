package dragoncode.unipark;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewProfileActivity extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private EditText EmpNum;
    private EditText Name;
    private EditText Email;
    private EditText PhoneNum;

    private Button btnEditProfile;
    private String[] details = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        new GetPersonDetails().execute();

        mdrawerlayout = (DrawerLayout) findViewById(R.id.view_profile);
        mToggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.Open, R.string.Close);

        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEditProfile = (Button) findViewById(R.id.btn_editProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    EmpNum = (EditText) findViewById(R.id.txtEmpNum);
                    EmpNum.setEnabled(true);
                    Name = (EditText) findViewById(R.id.txtName);
                    Name.setEnabled(true);
                    Email = (EditText) findViewById(R.id.txtEmail);
                    Email.setEnabled(true);
                    PhoneNum = (EditText) findViewById(R.id.txtPhoneNum);
                    PhoneNum.setEnabled(true);

                    btnEditProfile.setText("Update");

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
        protected void onPreExecute() {
           /* EmpNum.setText("");
            Name.setText("");
            Email.setText("");
            PhoneNum.setText("");*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {

                HttpHandler sh = new HttpHandler();
                String url = "http://10.0.2.2:9000" + "/personnel/specified/s" + getIntent().getStringExtra("ID");
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
}

