package dragoncode.unipark;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LandingPageActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Button btnViewProfile;
    private Button btnViewParking;
    private Button btnReportUser;
    private Button btnMaps;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.landing_page);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnViewProfile = (Button) findViewById(R.id.btn_profile);
        btnViewParking = (Button) findViewById(R.id.btn_parking);
        btnReportUser = (Button) findViewById(R.id.btn_report);
        btnMaps = (Button) findViewById(R.id.btn_maps);

        btnViewProfile.setOnClickListener(this);
        btnMaps.setOnClickListener(this);
        btnReportUser.setOnClickListener(this);
        btnViewParking.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_profile:
                intent = new Intent(this, ViewProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_parking:
                intent = new Intent(this, view_parking.class);
                startActivity(intent);
                break;

            case R.id.btn_report:
                intent = new Intent(this, ActivityReportUser.class);
                startActivity(intent);
                break;

            case R.id.btn_maps:
                intent = new Intent(this, ViewProfileActivity.class);
                startActivity(intent);
                break;
        }

    }
}
