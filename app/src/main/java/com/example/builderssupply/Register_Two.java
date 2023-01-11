package com.example.builderssupply;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class Register_Two extends AppCompatActivity {
    DatabaseHelper dbHelper;
    protected static double ltt;
    protected static double lgt;
    private static final int REQUEST_CODE_LOCATION_PERMISSSION = 1;
    protected LocationManager locationManager;
    EditText gst_number,address,password,cpassword;
    Switch geo_switch;
    //TextView tstTxt;
    String uname,mailing,contacts,sellers_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__two);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        gst_number = findViewById(R.id.reg_two_gst_edt);
        address = findViewById(R.id.reg_two_add_edt);
        password = findViewById(R.id.register_pass);
        geo_switch = findViewById(R.id.geo_switch);
        cpassword = findViewById(R.id.register_cpass);
        dbHelper = new DatabaseHelper(this);
        uname = getIntent().getStringExtra("username");
        mailing = getIntent().getStringExtra("email");
        contacts = getIntent().getStringExtra("mobile");
        sellers_code = getIntent().getStringExtra("sellers_code");
        //Toast.makeText(getApplicationContext(),"username-"+uname+",email-"+mailing+",mobile-"+contacts+",sellers_code-"+sellers_code,Toast.LENGTH_LONG).show();
        //tstTxt = findViewById(R.id.tstTxt);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Register_Two.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSSION);
        }
        else
        {
            final LocationRequest locationRequest  = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.getFusedLocationProviderClient(Register_Two.this).
                    requestLocationUpdates(locationRequest,new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(Register_Two.this).
                                    removeLocationUpdates(this);
                            if(locationResult != null & locationResult.getLocations().size() > 0)
                            {
                                int latestLocatioIndex = locationResult.getLocations().size() -1;
                                ltt = locationResult.getLocations().get(latestLocatioIndex).getLatitude();
                                lgt = locationResult.getLocations().get(latestLocatioIndex).getLongitude();
                            }
                        }
                    }, Looper.getMainLooper());
        }
    }
    public void onRegister(View view)
    {
        String lati = ltt+"";
        String longi = lgt+"";
        if(gst_number.getText().toString().equals(""))
        {
            gst_number.setError("GST number required");
        }
        else if(address.getText().toString().equals(""))
        {
            address.setError("address can't be empty");
        }
        else if(password.getText().toString().equals(""))
        {
            password.setError("password required");
        }
        else if(cpassword.getText().toString().equals(""))
        {
            cpassword.setError("confirming can't be empty");
        }
        else if(!geo_switch.isChecked())
        {
            geo_switch.setError("Location must be shared");
        }
        else
        {
            if(password.getText().toString().equals(cpassword.getText().toString()))
            {
                if(dbHelper.addSeller(uname,mailing,contacts,sellers_code,gst_number.getText().toString(),address.getText().toString(),lati,longi,password.getText().toString()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Register_Two.this);
                    builder.setTitle("SUCESSFUL");
                    builder.setMessage("Your Registered sucessfully.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent toLogin = new Intent(Register_Two.this,MainActivity.class);
                            startActivity(toLogin);
                        }
                    });
                    builder.show();
                }
            }
            else
            {
                cpassword.setError("Password doesn't matched");
            }
        }
    }
}
