package com.example.builderssupply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText prd_nm,prd_prc,desc1,desc2,desc3;
    Spinner spinner;
    String name;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        prd_nm = findViewById(R.id.prd_nm);
        prd_prc = findViewById(R.id.prd_prc);
        desc1 = findViewById(R.id.desc1);
        desc2 = findViewById(R.id.desc2);
        desc3 = findViewById(R.id.desc3);
        spinner = findViewById(R.id.spinner);
        sharedPreferences = getSharedPreferences("Login_Status", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name",null);

    }
    public void addItem(View view)
    {
        dbHelper = new DatabaseHelper(this);
        String cat = String.valueOf(spinner.getSelectedItem());
        if(prd_nm.getText().toString().trim().equals("") ||
                prd_prc.getText().toString().trim().equals("") ||
                desc1.getText().toString().trim().equals("") ||
                desc2.getText().toString().trim().equals("") ||
                desc3.getText().toString().trim().equals("") )
        {
            Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(dbHelper.insertProduct(cat,prd_nm.getText().toString().trim(),prd_prc.getText().toString().trim(),name,
                    desc1.getText().toString().trim(),desc2.getText().toString().trim(),desc3.getText().toString().trim()))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddActivity.this);
                builder.setTitle("SUCESSFUL");
                builder.setMessage("Item Added Sucessfully");
                builder.setPositiveButton("OK",null);
                builder.show();
                Intent intent = new Intent(this,Agent_Activity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Something went wrong....",Toast.LENGTH_LONG).show();
            }
        }
    }
}