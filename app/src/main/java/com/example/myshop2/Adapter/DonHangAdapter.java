package com.example.myshop2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop2.ObjectClass.DonHang;
import com.example.myshop2.R;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder>{
    ArrayList<DonHang> arrayList;
    Context context;

    public DonHangAdapter(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_donhang,parent,false);
        DonHangViewHolder donHangViewHolder = new DonHangViewHolder(view);

        return donHangViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.imgDonHang);
        holder.txtDonHangTitle.setText(arrayList.get(position).getTitle());
        holder.txtDonHangPrice.setText("tổng tiền: đ"+arrayList.get(position).getTotalprice());
        holder.txtDateDonHang.setText("ngày đặt: "+arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DonHangViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDonHang;
        TextView txtDonHangTitle, txtDonHangPrice,txtDateDonHang;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateDonHang = itemView.findViewById(R.id.txtDateDonHang);
            imgDonHang = itemView.findViewById(R.id.imgDonHang);
            txtDonHangTitle = itemView.findViewById(R.id.txtDonHangTitle);
            txtDonHangPrice = itemView.findViewById(R.id.txtDonHangPrice);
        }
    }
}
