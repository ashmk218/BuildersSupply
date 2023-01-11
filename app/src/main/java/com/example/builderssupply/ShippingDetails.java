package com.example.builderssupply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShippingDetails extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText add1_ed,add2_ed,add3_ed,pincode_ed,comment_ed;
    String address,code;
    String qnty,cost,prd_id,prd_nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        add1_ed = findViewById(R.id.add1_ed);
        add2_ed = findViewById(R.id.add2_ed);
        add3_ed = findViewById(R.id.add3_ed);
        pincode_ed = findViewById(R.id.pincode_ed);
        comment_ed = findViewById(R.id.comment_ed);


        prd_id = getIntent().getStringExtra("prd_id");
        prd_nm = getIntent().getStringExtra("prd_nm");
        qnty = getIntent().getStringExtra("qnty");
        cost = getIntent().getStringExtra("cost");

    }
    public void toFinal(View view)
    {
        if(add1_ed.getText().toString().equals(""))
        {
            add1_ed.setError("address 1 can't be null");
        }
        else if(add2_ed.getText().toString().equals(""))
        {
            add2_ed.setError("address 2 can't be null");
        }
        else if(add3_ed.getText().toString().equals(""))
        {
            add3_ed.setError("address 3 can't be null");
        }
        else if(pincode_ed.getText().toString().equals(""))
        {
            pincode_ed.setError("pincode is required");
        }
        else
        {
            String alert = setAlert();
            if(alert.equals(""))
            {
                dbHelper = new DatabaseHelper(this);
                address = add1_ed.getText().toString()+" "+add2_ed.getText().toString()+" "+add3_ed.getText().toString();
                code = prd_id+"X"+qnty;
                dbHelper.insertTemp(prd_id,prd_nm,qnty,cost,address,pincode_ed.getText().toString(),code,comment_ed.getText().toString());
                Intent toFinal = new Intent(this,Final_Order.class);
                startActivity(toFinal);
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ShippingDetails.this);
                builder.setTitle("ALERT");
                builder.setMessage(alert);
                builder.setPositiveButton("OK",null);
                builder.show();
            }
        }
    }
    public String setAlert()
    {
        String fault = "";
        if(pincode_ed.length() != 6)
        {
            fault = "Pincode must be of 6 digits only";
        }
        return fault;
    }
}