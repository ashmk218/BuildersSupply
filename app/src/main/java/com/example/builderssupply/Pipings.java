package com.example.builderssupply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class Pipings extends AppCompatActivity {
    //------------------------------ElECTRICIAN AND PLUMBERS------------
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipings);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
    public void action(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.call1 :
                makePhoneCall("9762601600");
                break;
            case R.id.call2 :
                makePhoneCall("8651101179");
                break;
            case R.id.call3 :
                makePhoneCall("8080571732");
                break;
            case R.id.call4 :
                makePhoneCall("9892448142");
                break;
            default:
                break;
        }
    }
    private void makePhoneCall(String number)
    {
        if(ContextCompat.checkSelfPermission(Pipings.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Pipings.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall();
            }
        }
    }*/
}