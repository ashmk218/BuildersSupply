package com.example.builderssupply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Final_Order extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    DatabaseHelper dbHelper;
    TextView prdct_name_rslt,prdct_price_rslt,qnty_rslt,final_prc;
    RadioButton rd_btn_cod,rd_btn_ptm_ptm;
    String prd_nm;
    SharedPreferences sharedPreferences;
    String pid,qnty,cost,name,adds,pcode,cmmt,status,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final__order);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("Login_Status", Context.MODE_PRIVATE);
        name =  sharedPreferences.getString("name",null);

        prdct_name_rslt = findViewById(R.id.prdct_name_rslt);
        prdct_price_rslt = findViewById(R.id.prdct_price_rslt);
        qnty_rslt = findViewById(R.id.qnty_rslt);
        final_prc = findViewById(R.id.final_prc);
        rd_btn_ptm_ptm = findViewById(R.id.rd_btn_ptm_ptm);
        rd_btn_cod = findViewById(R.id.rd_btn_cod);


        Cursor res = dbHelper.getTemp();
        while (res.moveToNext())
        {
            pid = res.getString(0);
            prd_nm = res.getString(1);
            qnty  = res.getString(2);
            cost  = res.getString(3);
            adds  = res.getString(4);
            pcode  = res.getString(5);
            code  = res.getString(6);
            cmmt  = res.getString(7);
        }

        prdct_name_rslt.setText(prd_nm);
        int cst = Integer.parseInt(cost);
        int qty = Integer.parseInt(qnty);
        int price = (cst/qty);
        prdct_price_rslt.setText(price+"");
        qnty_rslt.setText(qnty);
        final_prc.setText(cost);

        if(dbHelper.clearTemp() > 0)
        {
            Toast.makeText(getApplicationContext(),"Temp data cleared",Toast.LENGTH_SHORT).show();
        }

    }
    public void finalOrder(View view)
    {
        if(rd_btn_cod.isChecked())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    Final_Order.this);
            builder.setTitle("SUCESSFUL");
            builder.setMessage("YOUR ORDER PLACED SUCESSFULLY");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Final_Order.this,Category_List.class);
                    startActivity(intent);
                }
            });
            builder.show();
        }
        else if(rd_btn_ptm_ptm.isChecked())
        {
            dbHelper = new DatabaseHelper(this);
            Toast.makeText(getApplicationContext(),"ID-"+pid+" qnty-"+qnty+" cost-"+cost+" name-"+name+" adds-"+adds+" pcode-"+pcode+" cmmnt-"+cmmt+" sts- F code-"+code,Toast.LENGTH_LONG).show();
            if(dbHelper.insertFinal(pid,qnty,cost,name,adds,pcode,cmmt,"F",code))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Final_Order.this);
                builder.setTitle("ORDER PLACED");
                builder.setMessage("PLEASE PAYTM TO CALLING NUMBER WITH CODE "+code+" SO THAT ORDER GETS COMPLETED");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Final_Order.this,Category_List.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    Final_Order.this);
            builder.setTitle("ALERT");
            builder.setMessage("Please select payment mode");
            builder.setPositiveButton("OK",null);
            builder.show();
        }
    }
    public void action(View view)
    {
        int id = view.getId();
        if(id == R.id.call)
        {
            makePhoneCall("8828620498");
        }
        else if(id == R.id.whatsapp)
        {
            boolean installed = appInstalledOrNot("com.whatsapp");
            if(installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+918828620498"));
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Whatsapp not found on device",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.mail)
        {
            /*intent.putExtra(Intent.EXTRA_EMAIL,"patudalvi18@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT,"App generated mail from "+name);
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent,"Select Email client"));*/
            String mailto = "mailto:patudalvi18@gmail.com"+
                    "?cc"+
                    "&subject=" + Uri.encode("App generated mail from "+name);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(mailto));
            try {
                startActivity(Intent.createChooser(intent,"Select Email client"));
            }
            catch (ActivityNotFoundException e)
            {
                Toast.makeText(getApplicationContext(),"No email client found on device",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean appInstalledOrNot(String url)
    {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }
    private void makePhoneCall(String number)
    {
        if(ContextCompat.checkSelfPermission(Final_Order.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Final_Order.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
        }
    }
}