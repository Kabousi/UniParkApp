package dragoncode.unipark;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.HttpURLConnection;


import javax.net.ssl.HttpsURLConnection;

public class ActivityReportUser extends AppCompatActivity{

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nav;

    private Button btnReport;
    private Button btnCamera;

    private EditText txtRegNum;
    private EditText txtDesc;

    private TextView lblShowDesc;
    private TextView lblShowReg;

    private ImageView imgPic;

    private String id;
    private String[] details = new String[1];

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Intent intent;

    private static final String TAG = "ActivityReportUser";

    private static final int requestPermissionID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);

        btnReport = (Button) findViewById(R.id.btnSubmitReport);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        txtRegNum = (EditText) findViewById(R.id.txtRegistration);
        txtDesc = (EditText) findViewById(R.id.txtReportDesc);

        lblShowDesc = (TextView) findViewById(R.id.lblShowDesc);
        lblShowReg = (TextView) findViewById(R.id.lblShowReg);

        imgPic = (ImageView) findViewById(R.id.imgPic);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.report_user_activity);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.Open, R.string.Close);

        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regNum = txtRegNum.getText().toString();
                JSONObject data = new JSONObject();

                lblShowDesc.setVisibility(View.INVISIBLE);
                lblShowReg.setVisibility(View.INVISIBLE);

                if (txtRegNum.getText().toString().equals("")) {
                    Toast.makeText(ActivityReportUser.this, "Please enter Registration number", Toast.LENGTH_LONG).show();
                    lblShowReg.setVisibility(View.VISIBLE);
                }
                else if (txtRegNum.getText().toString().length() > 8) {
                    Toast.makeText(ActivityReportUser.this, "Registration number too long", Toast.LENGTH_LONG).show();
                    lblShowReg.setVisibility(View.VISIBLE);
                }
                else {
                    if (txtDesc.getText().toString().equals("")) {
                        Toast.makeText(ActivityReportUser.this, "Please enter a description", Toast.LENGTH_LONG).show();
                        lblShowDesc.setVisibility(View.VISIBLE);
                    } else {
                        try {
                            data.put("LicensePlate", regNum);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (data.length() > 0) {
                            new GetPerson().execute(String.valueOf(data));
                            lblShowDesc.setVisibility(View.INVISIBLE);
                            lblShowReg.setVisibility(View.INVISIBLE);
                            imgPic.clearColorFilter();
                            imgPic.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ActivityReportUser.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                imgPic.setVisibility(View.VISIBLE);
            }
        });


        nav = (NavigationView) findViewById(R.id.nav_reportUser);
        id = getIntent().getStringExtra("ID");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_screen:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.LandingPageActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_profile:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.ViewProfileActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.view_parking:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.view_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.request_parking:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.activity_request_parking.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.report_user:
                        intent = new Intent(ActivityReportUser.this, dragoncode.unipark.ActivityReportUser.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                        break;

                    case R.id.maps:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.apps.maps");
                        if (intent.resolveActivity(getPackageManager()) != null)
                            startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgPic.setImageBitmap(imageBitmap);


            TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            if (!txtRecognizer.isOperational())
            {
                Toast.makeText(ActivityReportUser.this,"Detector dependencies are not yet available", Toast.LENGTH_LONG).show();
            }
            else
            {
                Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
                SparseArray items = txtRecognizer.detect(frame);
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < items.size(); i++)
                {
                        TextBlock item = (TextBlock) items.valueAt(i);
                        strBuilder.append(item.getValue());
                        for (com.google.android.gms.vision.text.Text line : item.getComponents()) {
                            //extract scanned text lines here
                            Log.v("lines", line.getValue());
                            for (com.google.android.gms.vision.text.Text element : line.getComponents()) {
                                //extract scanned text words here
                                Log.v("element", element.getValue());
                            }
                        }
                    //}
                }
                String[] oldStr = strBuilder.toString().split(" ");
                String newStr = "";
                for(int i = 0; i < oldStr.length; i++)
                {
                    newStr = newStr + oldStr[i];
                }
                txtRegNum.setText(newStr);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    class GetPerson extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String url = getString(R.string.url) + "/personnelByPlate/?" + txtRegNum.getText();
            String jsonstr = sh.makeServiceCall(url);

            if (jsonstr != null) {
                try {
                    JSONObject object = new JSONObject(jsonstr);
                    details[0] = object.getString("PersonnelID");
                } catch (final JSONException e) {
                    Toast.makeText(ActivityReportUser.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            String regNumber = txtRegNum.getText().toString();
            String desc = txtDesc.getText().toString();
            String Type = "1";
            JSONObject data = new JSONObject();

            try {
                data.put("ReportDesc", desc);
                data.put("ReportCreator", "s" + getIntent().getStringExtra("ID"));
                data.put("PersonnelID", details[0]);
                data.put("ReportTypeID", Type);
                data.put("LicensePlate", regNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (data.length() > 0) {
                new AddReport().execute(String.valueOf(data));
            }
        }
    }

    class AddReport extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String urlstr = getString(R.string.url) + "/report";
            String jsonResponse = null;
            String jsonData = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
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
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;

                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine);

                if (buffer.length() == 0) {
                    return null;
                }
                jsonResponse = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {

            String[] response = new String[1];

            try {
                JSONObject obj = new JSONObject(jsonResponse);

                response[0] = obj.getString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (response[0].equals(" SUCCESS ")) {
                txtDesc.setText("");
                txtRegNum.setText("");
                Toast.makeText(ActivityReportUser.this, "Report admitted for review!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ActivityReportUser.this, "Report not successful!", Toast.LENGTH_LONG).show();
            }
        }
    }

}

