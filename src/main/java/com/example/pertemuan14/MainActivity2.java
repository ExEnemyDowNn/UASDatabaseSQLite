package com.example.pertemuan14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);
        db = new DatabaseHandler(this);
        ListView listView = (ListView) findViewById(R.id.listview1);
        ArrayList<Person> dolist = db.getli();
        listadapter listadapter = new listadapter(MainActivity2.this,R.layout.view_content_layout,dolist);
        listView.setAdapter(listadapter);
    }
}