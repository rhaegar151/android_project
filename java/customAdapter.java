package com.example.yogeshwar.rentalplatform;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class customAdapter extends ArrayAdapter<hy_brid>{


    public customAdapter(@NonNull Context context, ArrayList<hy_brid> h) {
        super(context, R.layout.custom_row,h);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater Lf=LayoutInflater.from(getContext());
        View customView=Lf.inflate(R.layout.custom_row,parent,false);

        hy_brid h=getItem(position);
        TextView t1=customView.findViewById(R.id.textView2);
        TextView t2=customView.findViewById(R.id.textView3);
        ImageView img=customView.findViewById(R.id.imageView);

     t1.setText("Book: "+h.b.bname+"Price: "+h.b.getPrice());
     t2.setText("@ "+h.s.getNam());
     String url=""+h.b.getMurl();
        Picasso.with(getContext()).load(url).into(img);

        return customView;
    }
}
