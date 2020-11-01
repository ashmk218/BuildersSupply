package com.example.builderssupply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Acknowledgement extends AppCompatActivity {
    DatabaseHelper dbHelper;
    TextView desc1_txt,desc2_txt,desc3_txt;
    TextView qnty_txt,main_title_ack,cost_txt_ack,seller_direct_txt;
    int currr_qnt,actual_qnt;
    ImageView prdct_img_btn;
    int prd_id;
    String seller_redirect, prd_nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledgement);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        qnty_txt = findViewById(R.id.txt_qnt);   //changiing qnty
        main_title_ack = findViewById(R.id.main_title_ack);  //prdct name
        cost_txt_ack = findViewById(R.id.cost_txt_ack);  // Final price
        seller_direct_txt = findViewById(R.id.seller_direct_txt);  //sellers name
        desc1_txt = findViewById(R.id.desc1_txt);    //desc1
        desc2_txt = findViewById(R.id.desc2_txt);    // desc2
        desc3_txt = findViewById(R.id.desc3_txt);    // desc3
        prdct_img_btn = findViewById(R.id.prdct_img_btn);

        currr_qnt = 1;
        qnty_txt.setText(currr_qnt+"");
        prd_nm = getIntent().getStringExtra("PRD_NAME");   // to get name
        String prd_cat = getIntent().getStringExtra("PRD_CAT");   // to get categories
        switch(prd_cat)
        {
            case "CONSTRUCTIONS":
                prdct_img_btn.setImageResource(R.drawable.constructions);
                break;
            case "FURNITURE" :
                prdct_img_btn.setImageResource(R.drawable.furniture);
                break;
            case "PAINTS":
                prdct_img_btn.setImageResource(R.drawable.paints);
                break;
            default:
                break;
        }
        main_title_ack.setText(prd_nm);


        dbHelper = new DatabaseHelper(this);
        Cursor res = dbHelper.getAck(prd_nm);
        while(res.moveToNext())
        {
            prd_id = Integer.parseInt(res.getString(0));
            actual_qnt = Integer.parseInt(res.getString(3));
            seller_redirect = res.getString(4);

            seller_direct_txt.setText("Seller : "+seller_redirect);
            cost_txt_ack.setText("Rs. "+actual_qnt+"/-");
            desc1_txt.setText(res.getString(5));
            desc2_txt.setText(res.getString(6));
            desc3_txt.setText(res.getString(7));
        }

    }
    public void sellerView(View view)
    {
        Intent intent = new Intent(Acknowledgement.this,Agent_Profile.class);
        intent.putExtra("seller",seller_redirect);
        startActivity(intent);
    }
    public void toShipping(View view)
    {
        Intent toShipping = new Intent(this,ShippingDetails.class);
        toShipping.putExtra("prd_id",prd_id+"");
        toShipping.putExtra("prd_nm",prd_nm);
        toShipping.putExtra("qnty",currr_qnt+"");
        toShipping.putExtra("cost",(actual_qnt * currr_qnt)+"");
        startActivity(toShipping);
    }
    public void changeQuantity(View view)
    {
        int id = view.getId();
            if(id == R.id.add_qnt) {
                if(currr_qnt < 10) {
                    currr_qnt++;
                    qnty_txt.setText(currr_qnt + "");
                    cost_txt_ack.setText("Rs. "+(currr_qnt * actual_qnt)+" /-");
                }
                else
                {
                    sendAlert();
                }
            }
            else if(id == R.id.remove_qnt)
            {
                if(currr_qnt > 1 )
                {
                    currr_qnt--;
                    qnty_txt.setText(currr_qnt+"");
                    cost_txt_ack.setText("Rs. "+(currr_qnt * actual_qnt)+" /-");
                }
                else
                {
                    sendAlert();
                }
            }
    }
    public  void sendAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Acknowledgement.this);
        builder.setTitle("OUT OF STOCK");
        builder.setMessage("Please select quantity in range of 1 to 10");
        builder.setPositiveButton("OK",null);
        builder.show();
    }
}