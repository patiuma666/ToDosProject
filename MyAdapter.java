package com.example.iis5.todo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS 5 on 29-12-2017.
 */

public class MyAdapter extends ArrayAdapter<Todos> {
    Context context;
    int resource;
    List<Todos>dataList=new ArrayList<>();
    LayoutInflater inflater;
    Todos todoData;
    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Todos> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        dataList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        todoData = dataList.get(position);
        SqLiteHelper helper = new SqLiteHelper(context);
        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource,parent,false);
        TextView textTopDate = (TextView)view.findViewById(R.id.textView);
        TextView textTitle = (TextView)view.findViewById(R.id.textView2);
        TextView textDescrip = (TextView)view.findViewById(R.id.textView3);
        TextView texDate =(TextView)view.findViewById(R.id.tv4);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        textTopDate.setText(todoData.getDate());
        textTitle.setText(todoData.getTitle());
        textDescrip.setText(todoData.getDescription());
        texDate.setText(todoData.getDate());
        imageView.setImageResource(todoData.getImage());

        return view;
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }
}
