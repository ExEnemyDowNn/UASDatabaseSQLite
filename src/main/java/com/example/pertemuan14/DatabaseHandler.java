package com.example.pertemuan14;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public  DatabaseHandler(Context context){
        super(context, "ImageDb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table images(id interger primary key, img blob not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists images");
    }

    public Boolean insertimage(String x, Integer i) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("id",i);
            contentValues.put("img",imgbyte);
            db.insert("images", null, contentValues);
            fs.close();
            return true;
        }

        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Bitmap getimage(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images where id=?", new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            byte[] imag = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(imag,0,imag.length);
        }
        return bt;
    }

    public ArrayList<Person> getli(){
        Person person = null;
        ArrayList<Person> personList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from images", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            person = new Person(cursor.getBlob(1));
            personList.add(person);
            cursor.moveToNext();
        }
        return personList;
    }
}
