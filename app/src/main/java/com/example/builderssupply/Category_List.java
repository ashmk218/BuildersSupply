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
import android.widget.TextView;
import android.widget.Toast;

public class Category_List extends AppCompatActivity{
    SharedPreferences sharedPreferences;
    TextView cat_txt;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__list);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        cat_txt = findViewById(R.id.cat_txt);
        sharedPreferences = getSharedPreferences("Login_Status", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name",null);
        cat_txt.setText("WELCOME,"+name);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Category_List.this);
        builder.setMessage("DO YOU WANT TO EXIT ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).show();
    }
    public void catRedirect(View view)
    {
        int id = view.getId();
        Intent catRedirects;
        switch (id)
        {
            case R.id.cement_btn:
                catRedirects = new Intent(this,Cements.class);
                break;
            case R.id.paint_btn:
                catRedirects = new Intent(this,Paints.class);
                break;
            case R.id.decor_btn:
                catRedirects = new Intent(this,Decor.class);
                break;
            case R.id.piping_btn:
                catRedirects = new Intent(this,Pipings.class);
                break;
            default:
                catRedirects = null;
                break;
        }
        startActivity(catRedirects);
    }
    public void logoutAction(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Category_List.this);
        builder.setMessage("DO YOU WANT TO LOGOUT ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getApplicationContext(),"Logout Clicked",Toast.LENGTH_SHORT).show();
                        Intent toMain = new Intent(Category_List.this,MainActivity.class);
                        startActivity(toMain);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("status","logout");
                        editor.putString("name","No User");
                        editor.commit();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).show();
    }
}