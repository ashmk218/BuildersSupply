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

public class Agent_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView txtvw;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        txtvw = findViewById(R.id.txtvw);
        sharedPreferences = getSharedPreferences("Login_Status", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name",null);
        txtvw.setText("Welcome,"+name);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Activity.this);
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
    public void logoutAction(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Activity.this);
        builder.setMessage("DO YOU WANT TO LOGOUT ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("status","logout");
                        editor.putString("name","no user");
                        editor.commit();
                        Intent toMain = new Intent(Agent_Activity.this,MainActivity.class);
                        startActivity(toMain);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).show();
    }
    public void actionRedirect(View view)
    {
        final Intent toAction;
        if(view.getId() == R.id.add_prdct)
        {
            toAction = new Intent(this,AddActivity.class);
        }
        else if(view.getId() == R.id.rmv_prdct)
        {
            toAction = new Intent(this,RemoveActivity.class);
        }
        else
        {
            toAction = null;
        }
        startActivity(toAction);
    }
}