package com.example.builderssupply;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "supplydb";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String userLogin = "CREATE TABLE user_login(username VARCHAR PRIMARY KEY,password VARCHAR)";
        String registration = "CREATE TABLE registration(user_name VARCHAR PRIMARY KEY,pass_word VARCHAR,e_mail VARCHAR,cont_num VARCHAR)";
        String seller = "CREATE TABLE seller(uname VARCHAR PRIMARY KEY,mailing VARCHAR,contacts VARCHAR,sellers_code VARCHAR,gst_number VARCHAR,address VARCHAR,ltt VARCHAR,lgt VARCHAR,pass_word VARCHAR)";
        String products = "CREATE TABLE products(pid INTEGER PRIMARY KEY AUTOINCREMENT,category VARCHAR,prd_nm VARCHAR,prd_prc VARCHAR,seller_nm VARCHAR,desc1 VARCHAR,desc2 VARCHAR,desc3 VARCHAR)";
        String tmp_order =  "CREATE TABLE tmp_order(pid VARCHAR,pname VARCHAR,qnty VARCHAR,cost VARHCAR,adds VARCHAR,pcode VARCHAR,code VARHCAR,cmmt VARCHAR)";
        String orders =  "CREATE TABLE orders(pid VARCHAR,qnty VARCHAR,cost VARHCAR,name VARCHAR,adds VARCHAR,pcode VARCHAR,cmmt VARCHAR,status VARHCAR,code VARHCAR)";
        String insert_query1 = "INSERT INTO user_login values('abc','123')";
        String insert_query2 = "INSERT INTO seller values('pqr','mail','12345667890','seller code','gst','address','19.115748','72.924891','123')";
        String insert_query3 = "INSERT INTO user_login values('pqr','123')";
        db.execSQL(userLogin);
        db.execSQL(registration);
        db.execSQL(seller);
        db.execSQL(products);
        db.execSQL(tmp_order);
        db.execSQL(orders);
        db.execSQL(insert_query1);
        db.execSQL(insert_query2);
        db.execSQL(insert_query3);
    }
    public boolean validateLogin(String username , String passworrd)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_login where username = '"+username+"' and password = '"+passworrd+"'",null);
        if(cursor.getCount() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean validateSeller(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from seller where uname = '"+username+"'",null);
        if(res.getCount() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //--------------------LOGIN METHODS ENDS----------------------------------------------------

    public boolean addUser(String username,String password,String e_mail,
                           String cont_num)
    {
        SQLiteDatabase writer_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name",username);
        contentValues.put("pass_word",password);
        contentValues.put("e_mail",e_mail);
        contentValues.put("cont_num",cont_num);

        long flag = writer_db.insert("registration",null,contentValues);
        boolean flag1 = addLoginData(username,password);
        if(flag == -1 || flag1 == false)
            return false;
        else
            return true;
    }
    public boolean addSeller(String uname,String mailing,String contacts,
                             String sellers_code,String gst_number,String address,
                             String ltt,String lgt,String pass_word)
    {
        SQLiteDatabase writerSeller_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uname",uname);
        contentValues.put("mailing",mailing);
        contentValues.put("contacts",contacts);
        contentValues.put("sellers_code",sellers_code);
        contentValues.put("gst_number",gst_number);
        contentValues.put("address",address);
        contentValues.put("ltt",ltt);
        contentValues.put("lgt",lgt);
        contentValues.put("pass_word",pass_word);

        long flag = writerSeller_db.insert("seller",null,contentValues);
        boolean flag1 = addLoginData(uname,pass_word);
        if(flag == -1 && flag1 == false)
            return false;
        else
            return true;
    }
    public boolean addLoginData(String username,String password)
    {
        SQLiteDatabase writerLogin_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);

        long flag = writerLogin_db.insert("user_login",null,contentValues);
        if(flag == -1)
            return false;
        else
            return true;
    }

    //-------------------------------REGISTRATION METHODS ENDS----------------------------------

    public boolean insertProduct(String category,String prd_nm,String prd_prc,
                                 String seller_nm,String desc1,String desc2,String desc3)
    {
        SQLiteDatabase writerSeller_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category",category);
        contentValues.put("prd_nm",prd_nm);
        contentValues.put("prd_prc",prd_prc);
        contentValues.put("seller_nm",seller_nm);
        contentValues.put("desc1",desc1);
        contentValues.put("desc2",desc2);
        contentValues.put("desc3",desc3);

        long flag = writerSeller_db.insert("products",null,contentValues);
        if(flag == -1)
            return false;
        else
            return true;
    }
    public Cursor getItems(String seller)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from products where seller_nm = '"+seller+"'",null);
        return res;
    }
    public boolean removeProduct(String prd_nm)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("products","prd_nm=?",new String[]{prd_nm});
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //----------------------------ADD & REMOVE PRODUCTS-----------------------------------------

    public Cursor getCatItems(String cat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from products where category = '"+cat+"'",null);
        return res;
    }

    //----------------------------CATEGORY DATA METHOD--------------------------------------------

    public Cursor getSeller(String seller_name)
    {
        SQLiteDatabase sellerdb = this.getWritableDatabase();
        Cursor res = sellerdb.rawQuery("Select * from seller where uname = '"+seller_name+"';",null);
        return res;
    }

    //-------------------------------------GET SELLER PROFILE------------------------------------

    public boolean insertTemp(String pid,String pname,String qnty,String cost,String adds,String pcode,String code,String cmmt)
    {
        SQLiteDatabase tmp_insert = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pid",pid);
        contentValues.put("pname",pname);
        contentValues.put("qnty",qnty);
        contentValues.put("cost",cost);
        contentValues.put("adds",adds);
        contentValues.put("pcode",pcode);
        contentValues.put("code",code);
        contentValues.put("cmmt",cmmt);

        long flag = tmp_insert.insert("tmp_order",null,contentValues);
        if(flag == -1)
            return false;
        else
            return true;
    }

    public Cursor getTemp()
    {
        SQLiteDatabase tmp = this.getWritableDatabase();
        Cursor res = tmp.rawQuery("Select * from tmp_order",null);
        return res;
    }

    public Integer clearTemp()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tmp_order",null,null);
    }

    public boolean insertFinal(String pid,String qnty,String cost,String name,String adds,String pcode,String cmmt,String status,String code)
    {
        SQLiteDatabase finaldb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pid",pid);
        contentValues.put("qnty",qnty);
        contentValues.put("cost",cost);
        contentValues.put("name",name);
        contentValues.put("adds",adds);
        contentValues.put("pcode",pcode);
        contentValues.put("cmmt",cmmt);
        contentValues.put("status",status);
        contentValues.put("code",code);


        long flag = finaldb.insert("orders",null,contentValues);
        if(flag == -1)
            return false;
        else
            return true;
    }

    //------------------------------------ORDERS METHOD------------------------------------------
    public Cursor getAck(String prd_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from products where prd_nm = '"+prd_name+"'",null);
        return res;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String userLogin = "DROP TABLE IF EXISTS user_login";
        String registration = "DROP TABLE IF EXISTS registration";
        String tmp_order = "DROP TABLE IF EXISTS tmp_order";
        String orders = "DROP TABLE IF EXISTS orders";
        String seller = "DROP TABLE IF EXISTS seller";
        String products = "DROP TABLE IF EXISTS products";
        db.execSQL(userLogin);
        db.execSQL(registration);
        db.execSQL(seller);
        db.execSQL(tmp_order);
        db.execSQL(orders);
        db.execSQL(products);
        onCreate(db);
    }
}
