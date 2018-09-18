package dragoncode.unipark;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
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
import android.widget.ImageButton;
import android.widget.TextView;

public class LandingPageActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Button btnViewProfile;
    private Button btnViewParking;
    private Button btnReportUser;
    private Button btnMaps;

    private ImageButton ibtnHelp;

    private TextView txtProfileHelp;
    private TextView txtParkingHelp;
    private TextView txtReportHelp;
    private TextView txtMapsHelp;

    int view = 0;

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

        ibtnHelp = (ImageButton) findViewById(R.id.ibtnHelp);

        txtMapsHelp = (TextView) findViewById(R.id.txtMapsHelp);
        txtParkingHelp = (TextView) findViewById(R.id.txtParkingHelp);
        txtProfileHelp = (TextView) findViewById(R.id.txtProfileHelp);
        txtReportHelp = (TextView) findViewById(R.id.txtReportHelp);

        btnViewProfile.setOnClickListener(this);
        btnMaps.setOnClickListener(this);
        btnReportUser.setOnClickListener(this);
        btnViewParking.setOnClickListener(this);
        ibtnHelp.setOnClickListener(this);

        nav = (NavigationView) findViewById(R.id.nav_landingPage);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_screen:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.view_profile:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.view_parking:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.request_parking:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.report_user:
                        intent = new Intent(LandingPageActivity.this, dragoncode.unipark.ActivityReportUser.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.maps:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.apps.maps");
                        if(intent.resolveActivity(getPackageManager()) != null)
                            startActivity(intent);
                        break;

                    case R.id.logout:
                        intent = new Intent(LandingPageActivity.this, Login.class);
                        startActivity(intent);
                        finish();
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
                finish();
                break;

            case R.id.btn_parking:
                intent = new Intent(this, dragoncode.unipark.view_parking.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_report:
                intent = new Intent(this, dragoncode.unipark.ActivityReportUser.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_maps:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.apps.maps");
                if(intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
                break;

            case R.id.ibtnHelp:
                if(view == 0){
                    txtReportHelp.setVisibility(View.VISIBLE);
                    txtProfileHelp.setVisibility(View.VISIBLE);
                    txtMapsHelp.setVisibility(View.VISIBLE);
                    txtParkingHelp.setVisibility(View.VISIBLE);
                    view = 1;
                }
                else if(view == 1){
                    txtReportHelp.setVisibility(View.GONE);
                    txtProfileHelp.setVisibility(View.GONE);
                    txtMapsHelp.setVisibility(View.GONE);
                    txtParkingHelp.setVisibility(View.GONE);
                    view = 0;
                }
                break;
        }
    }
}
