package com.example.builderssupply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveActivity extends AppCompatActivity {
    String seller_name;
    String[] s_main_title;
    DatabaseHelper dbHelper;
    SharedPreferences sharedPreferences;
    ListView listvw;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("Login_Status", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name",null);
        //Toast.makeText(getApplicationContext(),"Status :"+sharedPreferences.getString("status",null)+"Name"+sharedPreferences.getString("name",null),Toast.LENGTH_SHORT).show();
        dbHelper = new DatabaseHelper(this);
        listvw = findViewById(R.id.remove_listsvw);
        Cursor res = dbHelper.getItems(name);
        s_main_title = new String[res.getCount()];
        if(res.getCount() == 0 )
        {
            Toast.makeText(getApplicationContext(),"No products found....",Toast.LENGTH_LONG).show();
        }
        else
        {
            //Toast.makeText(getApplicationContext(),"Inside Remove with col count "+res.getCount(),Toast.LENGTH_SHORT).show();
            int i = 0;
            while(res.moveToNext())
            {
                s_main_title[i] = res.getString(2);
                i++;
            }
            ArrayAdapt adp = new ArrayAdapt(this,s_main_title);
            listvw.setAdapter(adp);
            listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Toast.makeText(getApplicationContext(),""+s_main_title[position]+" selected",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            RemoveActivity.this);
                    builder.setTitle("ALERT");
                    builder.setMessage("ARE YOU SURE TO REMOVE "+s_main_title[position]);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbHelper.removeProduct(s_main_title[position]);
                            Toast.makeText(getApplicationContext(),"item deleted",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RemoveActivity.this,Agent_Activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
            });
        }
    }
    class ArrayAdapt extends ArrayAdapter<String>
    {
        Context context;
        String r_main_title[];

        ArrayAdapt(Context c,String[] title){
            super(c,R.layout.removelist,R.id.remove_main_title,title);
            this.context = c;
            this.r_main_title = title;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.removelist,parent,false);
            TextView my_titles = row.findViewById(R.id.remove_main_title);

            //setting resources on view
            my_titles.setText(r_main_title[position]);
            return row;
        }
    }
}