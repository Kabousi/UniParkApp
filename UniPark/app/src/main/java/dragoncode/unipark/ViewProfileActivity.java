package dragoncode.unipark;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewProfileActivity extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private EditText EmpNum;
    private EditText Name;
    private EditText Email;
    private EditText PhoneNum;

    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

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
}
