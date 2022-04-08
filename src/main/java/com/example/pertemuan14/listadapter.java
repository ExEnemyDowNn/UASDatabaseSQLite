package com.example.pertemuan14;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listadapter extends ArrayAdapter<Person> {
    private Context mcontext;
    int mresource;

    private static class ViewHolder{
        ImageView img;
    }
    public listadapter(Context context, int resource, ArrayList<Person>objects){
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        byte[] img = getItem(position).getImg();
        Person person = new Person(img);
        View result;
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);
            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(person.getImg(),0, person.getImg().length));
        return convertView;
    }
}
