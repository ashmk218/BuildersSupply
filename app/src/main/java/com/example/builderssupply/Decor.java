package com.example.builderssupply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
//----------------------------------------FURNITURE ACTIVITY-----------------------
public class Decor extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView listvw;
    String[] s_main_title;
    String[] s_seller;
    String[] s_sub_title;
    int s_images = R.drawable.furniture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decor);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        dbHelper = new DatabaseHelper(this);
        Cursor res = dbHelper.getCatItems("FURNITURE");
        s_main_title = new String[res.getCount()];
        s_seller  = new String[res.getCount()];
        s_sub_title  = new String[res.getCount()];
        if(res.getCount() == 0)
        {
            setAlert("FURNITURE");
        }
        int i = 0;
        while (res.moveToNext())
        {
            s_main_title[i] = res.getString(2);
            s_sub_title[i] = res.getString(3);
            s_seller[i] = res.getString(4);
            i++;
        }
        listvw = findViewById(R.id.decor_listsvw);
        final Intent toRegister = new Intent(this,Acknowledgement.class);
        ArrayAdapt adp = new ArrayAdapt(this,s_main_title,s_sub_title,s_images,s_seller);
        listvw.setAdapter(adp);
        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),""+s_main_title[position]+" selected",Toast.LENGTH_SHORT).show();
                toRegister.putExtra("PRD_NAME",s_main_title[position]);
                toRegister.putExtra("PRD_CAT","FURNITURE");
                startActivity(toRegister);
            }
        });
    }
    class ArrayAdapt extends ArrayAdapter<String>
    {
        Context context;
        String r_main_title[];
        String r_sub_title[];
        String r_seller[];
        int r_images;

        ArrayAdapt(Context c,String[] title,String[] sub_title,int image,String[] seller){
            super(c,R.layout.list_row,R.id.main_title,title);
            this.context = c;
            this.r_main_title = title;
            this.r_sub_title = sub_title;
            this.r_images = image;
            this.r_seller = seller;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.list_row,parent,false);
            ImageView images = row.findViewById(R.id.images);
            TextView my_titles = row.findViewById(R.id.main_title);
            TextView my_sub_titles = row.findViewById(R.id.sub_title);
            TextView my_seller = row.findViewById(R.id.seller_txt);

            //setting resources on view
            images.setImageResource(r_images);
            my_titles.setText(r_main_title[position]);
            my_sub_titles.setText(r_sub_title[position]);
            my_seller.setText(r_seller[position]);
            return row;
        }
    }
    public void setAlert(String cat)
    {
        Toast.makeText(getApplicationContext()," No items added by seller in "+cat+" section",Toast.LENGTH_SHORT).show();
    }
}