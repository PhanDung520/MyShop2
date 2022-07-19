package com.example.myshop2.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop2.ObjectClass.BeforeSearchClass;
import com.example.myshop2.R;

import java.util.ArrayList;

public class BeforeSearchAdapter extends RecyclerView.Adapter<BeforeSearchAdapter.BeforeSearchViewHolder>{
    ArrayList<BeforeSearchClass> arrayListBeforeSearch;
    Context context;

    public BeforeSearchAdapter(ArrayList<BeforeSearchClass> arrayListBeforeSearch, Context context) {
        this.arrayListBeforeSearch = arrayListBeforeSearch;
        this.context = context;
    }

    @NonNull
    @Override
    public BeforeSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_before_search,parent,false);
        BeforeSearchViewHolder beforeSearchViewHolder = new BeforeSearchViewHolder(view);
        return beforeSearchViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BeforeSearchViewHolder holder, int position) {
        BeforeSearchClass search = arrayListBeforeSearch.get(position);
        holder.txtBeforeSearch.setText(search.getTitle());
        holder.imgBeforeSearch.setImageResource(search.getImage());
        holder.beforesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListBeforeSearch.size();
    }

    public static class BeforeSearchViewHolder extends RecyclerView.ViewHolder{
        TextView txtBeforeSearch;
        ImageView imgBeforeSearch;
        CardView beforesearch;
        public BeforeSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            beforesearch = itemView.findViewById(R.id.beforesearch);
            txtBeforeSearch = itemView.findViewById(R.id.txtBeforeSearch);
            imgBeforeSearch = itemView.findViewById(R.id.imgBeforeSearch);

        }
    }

}
