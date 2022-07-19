package com.example.myshop2.Adapter;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop2.ActivityPackage.AllCateChiTiet;
import com.example.myshop2.ActivityPackage.ProductActivity2;
import com.example.myshop2.ObjectClass.AllCategoryClass;
import com.example.myshop2.R;

import java.util.ArrayList;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder>{
    ArrayList<AllCategoryClass> arrayAllCate;
    Context context;

    public AllCategoryAdapter(ArrayList<AllCategoryClass> arrayAllCate, Context context) {
        this.arrayAllCate = arrayAllCate;
        this.context = context;
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_allcategory,parent,false);
        AllCategoryViewHolder allCategoryViewHolder = new AllCategoryViewHolder(view);
        return allCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {
        AllCategoryClass allCate = arrayAllCate.get(position);
        Glide.with(context).load(arrayAllCate.get(position).getImage()).into(holder.imgCardAllcate);
        //holder.imgCardAllcate.setImageResource(allCate.getImage());
        if(allCate.getDesc().length()<40){
            holder.txtAllcateDesc.setText(allCate.getDesc());
        }else{
            holder.txtAllcateDesc.setText(allCate.getDesc().substring(0,40)+"...");
        }
        holder.txtTitleCardAllcate.setText(allCate.getTitle());
        holder.ratingCardAllcate.setRating(allCate.getRating());
        holder.allcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+allCate.getId(), Toast.LENGTH_SHORT).show();
                goToDetail(allCate.getId());
            }
        });
    }
    private void goToDetail(int id) {
        Intent intent = new Intent(context, AllCateChiTiet.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("_idCate",id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrayAllCate.size();
    }

    public static class AllCategoryViewHolder extends RecyclerView.ViewHolder{
        CardView allcate;
        ImageView imgCardAllcate;
        TextView txtTitleCardAllcate;
        RatingBar ratingCardAllcate;
        TextView txtAllcateDesc;
        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            allcate = itemView.findViewById(R.id.allcate);
            imgCardAllcate = itemView.findViewById(R.id.imgCardAllcate);
            txtTitleCardAllcate = itemView.findViewById(R.id.txtTitleCardAllcate);
            ratingCardAllcate = itemView.findViewById(R.id.ratingCardAllcate);
            txtAllcateDesc = itemView.findViewById(R.id.txtAllcateDesc);
        }
    }
}
