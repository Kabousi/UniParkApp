package dragoncode.unipark;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
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
    private NavigationView nav;

    private Intent intent;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        id = getIntent().getStringExtra("ID");

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

        nav = (NavigationView) findViewById(R.id.nav_landingPage);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_screen:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_profile:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_parking:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.request_parking:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.report_user:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.ActivityReportUser.class);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_profile:
                intent = new Intent(this, dragoncode.unipark.ViewProfileActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                break;

            case R.id.btn_parking:
                intent = new Intent(this, dragoncode.unipark.view_parking.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                break;

            case R.id.btn_report:
                intent = new Intent(this, dragoncode.unipark.ActivityReportUser.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                break;

            case R.id.btn_maps:

                intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.apps.maps");
                if(intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);

                break;
        }

    }
}
