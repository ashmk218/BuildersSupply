package com.example.builderssupply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText username,password;
    String status;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.login_uer_Edt);
        password = findViewById(R.id.login_pass_Edt);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("Login_Status", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("status"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("status","neutral");
            editor.putString("name","no user");
            editor.commit();
        }
        status = sharedPreferences.getString("status",null);
        //Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
        if(status.equals("sellerLogin"))
        {
            Intent toSeller = new Intent(MainActivity.this,Agent_Activity.class);
            startActivity(toSeller);
        }
        else if(status.equals("buyerLogin"))
        {
            Intent toBuyer = new Intent(MainActivity.this,Category_List.class);
            startActivity(toBuyer);
        }
    }
    public void login(View view)
    {
        dbHelper = new DatabaseHelper(this);
        String validate = validateLogin();
        if(validate.equals(""))
        {
            if(dbHelper.validateLogin(username.getText().toString().trim(),password.getText().toString().trim()))
            {
                if(dbHelper.validateSeller(username.getText().toString().trim()))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("status","sellerLogin");
                    editor.putString("name",username.getText().toString().trim());
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,Agent_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("status","buyerLogin");
                    editor.putString("name",username.getText().toString().trim());
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,Category_List.class);
                    startActivity(intent);
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setTitle("UNSUCESSFUL");
                builder.setMessage("Please provide correct credentials");
                builder.setPositiveButton("OK",null);
                builder.show();
            }
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle("WARNING");
            builder.setMessage(validate);
            builder.setPositiveButton("OK",null);
            builder.show();
        }
    }
    public void newUser(View view)
    {
        Intent intent = new Intent(this,Register_One.class);
        startActivity(intent);
    }
    public String validateLogin()
    {
        String fault = "";
        if(username.getText().toString().equals("") || password.getText().toString().equals(""))
        {
            fault = "Username and Password can't be null";
        }
        return fault;
    }
}