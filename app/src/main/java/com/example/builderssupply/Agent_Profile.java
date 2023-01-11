package com.example.builderssupply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Agent_Profile extends AppCompatActivity {
    DatabaseHelper dbHelper;
    TextView agnt_name_rslt,agnt_add_rslt,agnt_gst_rslt;
    String seller;
    String ltt,lgt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent__profile);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        agnt_name_rslt = findViewById(R.id.agnt_name_rslt);
        agnt_add_rslt = findViewById(R.id.agnt_add_rslt);
        agnt_gst_rslt = findViewById(R.id.agnt_gst_rslt);


        seller = getIntent().getStringExtra("seller");

        dbHelper = new DatabaseHelper(this);
        Cursor res = dbHelper.getSeller(seller);
        while(res.moveToNext())
        {
            agnt_name_rslt.setText(res.getString(0));
            agnt_add_rslt.setText(res.getString(5));
            agnt_gst_rslt.setText(res.getString(4));
            ltt = res.getString(6);
            lgt = res.getString(7);
        }
    }
    public void toLocation(View view)
    {
        String location = "geo:"+ltt+","+lgt;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(location));
        startActivity(intent);
    }
}