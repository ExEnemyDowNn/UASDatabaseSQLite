package com.example.pertemuan14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button insert, btn2;
    EditText number;
    DatabaseHandler db;
    ImageView img1;
    final int kodeKamera = 99;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert = (Button) findViewById(R.id.insert);
        img1 = (ImageView) findViewById(R.id.gambar1);
        number = (EditText) findViewById(R.id.number);
        db = new DatabaseHandler(this);
        btn2 = (Button) findViewById(R.id.btn5);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, kodeKamera);
            }
        });
    }

    public void get(View view){
        startActivity(new Intent(MainActivity.this, MainActivity2.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            Uri uri = data.getData();
            String x = getPath(uri);
            Integer num =Integer.parseInt(number.getText().toString());
            if(db.insertimage(x,num)){
                Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == kodeKamera && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img1.setImageBitmap(bitmap);
        }
    }

    public String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


}