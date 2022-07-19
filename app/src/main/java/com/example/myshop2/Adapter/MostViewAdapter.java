package com.example.myshop2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myshop2.ActivityPackage.HomeScreenActivity;
import com.example.myshop2.ActivityPackage.ProductActivity;
import com.example.myshop2.ActivityPackage.ProductActivity2;
import com.example.myshop2.ObjectClass.MostViewClass;
import com.example.myshop2.R;

import java.util.ArrayList;


public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.MostViewViewHolder>{
    private ArrayList<MostViewClass> arrayMostView;
    private Context context;


    public MostViewAdapter(ArrayList<MostViewClass> arrayMostView, Context context) {
        this.arrayMostView = arrayMostView;
        this.context = context;
    }

    @NonNull
    @Override
    public MostViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mostview,parent,false);
        MostViewViewHolder mostViewViewHolder = new MostViewViewHolder(view);
        return mostViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostViewViewHolder holder, int position) {
        MostViewClass mostView = arrayMostView.get(position);
        Glide.with(context).load(mostView.getImage()).into(holder.imgCardMostView);
        holder.txtMostViewTitle.setText(mostView.getTitle());

        if(mostView.getDesc().length() >100){
            String first = mostView.getDesc().substring(0,99)+"...";
            holder.txtMostViewDesc.setText(first);
        }
        holder.ratingMostView.setRating(mostView.getRating());
        holder.layoutMostView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetail(mostView.getId());
            }
        });
    }

    private void goToDetail(int id) {
        Intent intent = new Intent(context, ProductActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("_idProduct",id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrayMostView.size();
    }

    public static class MostViewViewHolder extends RecyclerView.ViewHolder{
        CardView layoutMostView;
        ImageView imgCardMostView;
        TextView txtMostViewTitle,txtMostViewDesc;
        RatingBar ratingMostView;

        public MostViewViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutMostView = itemView.findViewById(R.id.layoutMostView);
            imgCardMostView = itemView.findViewById(R.id.imgCardMostView);
            txtMostViewTitle = itemView.findViewById(R.id.txtMostViewTitle);
            txtMostViewDesc = itemView.findViewById(R.id.txtMostViewDesc);
            ratingMostView = itemView.findViewById(R.id.ratingMostView);
        }
    }
}
