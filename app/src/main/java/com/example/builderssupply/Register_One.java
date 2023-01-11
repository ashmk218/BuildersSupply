package com.example.builderssupply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_One extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText sellercode_txt;
    EditText username,email,mobile,password;
    Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__one);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        sellercode_txt = findViewById(R.id.sellercode_Edt);
        register_btn = findViewById(R.id.register_dynamic_btn);
        username = findViewById(R.id.register_uer_Edt);
        email = findViewById(R.id.register_mail_Edt);
        mobile = findViewById(R.id.resiter_mobile_Edt);
        password = findViewById(R.id.sellercode_Edt);
        dbHelper = new DatabaseHelper(this);
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if(checked)
        {
            register_btn.setText("NEXT");
            sellercode_txt.setHint("Sellers Code");
        }
        else
        {
            register_btn.setText("REGISTER");
            sellercode_txt.setHint("password");
        }
    }
    public void dynamicButton(View view)
    {
        if(username.getText().toString().equals(""))
        {
            username.setError("Username is required");
        }
        else if(email.getText().toString().equals(""))
        {
            email.setError("Mail is required");
        }
        else if(mobile.getText().toString().equals(""))
        {
            mobile.setError("Mobile is required");
        }
        else
        {
            String fault = validateRegOne();
            if(fault.equals(""))
            {
                String btnStatus = register_btn.getText().toString();
                if(btnStatus.equals("NEXT"))
                {
                    if(password.getText().toString().equals("") || !password.getText().toString().equals("A100"))
                    {
                        sellercode_txt.setError("Sellers Code is required");
                    }
                    else
                    {
                        Intent reg2_intent = new Intent(this,Register_Two.class);
                        reg2_intent.putExtra("username",username.getText().toString());
                        reg2_intent.putExtra("email",email.getText().toString());
                        reg2_intent.putExtra("mobile",mobile.getText().toString());
                        reg2_intent.putExtra("sellers_code",password.getText().toString());
                        startActivity(reg2_intent);
                    }
                }
                else if(btnStatus.equals("REGISTER"))
                {
                    if(password.getText().toString().equals(""))
                    {
                        sellercode_txt.setError("Password is required");
                    }
                    else
                    {
                        boolean isInserted = dbHelper.addUser(username.getText().toString(),password.getText().toString()
                                ,email.getText().toString(),mobile.getText().toString());
                        if(isInserted)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    Register_One.this);
                            builder.setTitle("SUCESSFUL");
                            builder.setMessage("Your Registered sucessfully.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent toLogin = new Intent(Register_One.this,MainActivity.class);
                                    startActivity(toLogin);
                                }
                            });
                            builder.show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Something went wrong....Please try again",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Register_One.this);
                builder.setTitle("WARNING");
                builder.setMessage(fault);
                builder.setPositiveButton("OK",null);
                builder.show();
            }
        }
    }
    public String validateRegOne()
    {
        String fault = "";
        String format = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(email.getText().toString().trim());
        if(!matcher.matches())
        {
            fault = "Enter proper email.";
        }
        else if(mobile.length() != 10)
        {
            fault = "Enter Valid Number";
        }
        return fault;
    }
}