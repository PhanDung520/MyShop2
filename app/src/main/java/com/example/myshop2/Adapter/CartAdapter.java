package com.example.myshop2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop2.ActivityPackage.CartActivity;
import com.example.myshop2.ActivityPackage.HomeScreenActivity;
import com.example.myshop2.ObjectClass.Cart;
import com.example.myshop2.R;

import java.util.ArrayList;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    ArrayList<Cart> arrayListCart;
    Context context;
    int sum;
    Map<Boolean,Cart> listWithChecked;

    public interface OnItemCheckListener{
        void onItemCheck(Cart cart);
        void onItemUncheck(Cart cart);
    }
    private OnItemCheckListener onItemCheck;

    public CartAdapter(ArrayList<Cart> arrayListCart, Context context, OnItemCheckListener onItemCheck) {
        this.arrayListCart = arrayListCart;
        this.context = context;
        this.onItemCheck = onItemCheck;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart,parent,false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Cart cart = arrayListCart.get(position);
        Glide.with(context).load(cart.getThumbnail()).into(holder.imgCart);
        if(cart.getTitle().length()<35){
            holder.txtCardTitle.setText(cart.getTitle());
        }else{
            holder.txtCardTitle.setText(cart.getTitle().substring(0,35)+"...");
        }
        final int[] soluong = {cart.getSoluong()};
        holder.txtCartPrice.setText("đ"+ soluong[0] *cart.getPrice());
        holder.txtCardSoLuong.setText(cart.getSoluong()+"");
        holder.txtSoLuongTrongKhoCart.setText("Trong kho: "+cart.getSoLuongTorngKho());
        //
        final int[] totalPice = {0};
        sum = 0;
        ((CartActivity)context).SetTotalPrice(sum);
        holder.btnCartMinius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soluong[0] >1){
                    soluong[0] -=1;
                    holder.txtCardSoLuong.setText(soluong[0] +"");
                    holder.txtCartPrice.setText("đ"+ soluong[0] *cart.getPrice());
                    ((CartActivity)context).SetAffterMua(soluong[0],position);
                }
            }
        });
        holder.btnCartPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soluong[0] <cart.getSoLuongTorngKho()){
                    soluong[0] +=1;
                    holder.txtCardSoLuong.setText(""+ soluong[0]);
                    holder.txtCartPrice.setText("đ"+ soluong[0] *cart.getPrice());
                    ((CartActivity)context).SetAffterMua(soluong[0],position);

                }
            }
        });
        holder.checkAddToOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sum +=soluong[0] *cart.getPrice();
                    ((CartActivity)context).SetTotalPrice(sum);
                    //dang check
                    holder.btnCartMinius.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(soluong[0] >1){
                                soluong[0] -=1;
                                holder.txtCardSoLuong.setText(soluong[0] +"");
                                holder.txtCartPrice.setText("đ"+ soluong[0] *cart.getPrice());
                                sum -=cart.getPrice();
                                ((CartActivity)context).SetTotalPrice(sum);

                            }
                        }
                    });
                    holder.btnCartPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(soluong[0] <cart.getSoLuongTorngKho()){
                                soluong[0] +=1;
                                holder.txtCardSoLuong.setText(""+ soluong[0]);
                                holder.txtCartPrice.setText("đ"+ soluong[0] *cart.getPrice());
                                sum +=cart.getPrice();
                                ((CartActivity)context).SetTotalPrice(sum);
                                cart.setSoluong(soluong[0]);
                            }
                        }
                    });
                    onItemCheck.onItemCheck(cart);
                }
                else {
                    sum -=soluong[0] *cart.getPrice();
                    ((CartActivity)context).SetTotalPrice(sum);
                    cart.setSoluong(soluong[0]);
                    onItemCheck.onItemUncheck(cart);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListCart.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCart,btnCartMinius,btnCartPlus;
        TextView txtCardTitle,txtCartPrice,txtCardSoLuong, txtSoLuongTrongKhoCart;
        CheckBox checkAddToOrder;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoLuongTrongKhoCart = itemView.findViewById(R.id.txtSoLuongTrongKhoCart);
            imgCart = itemView.findViewById(R.id.imgCart);
            btnCartMinius = itemView.findViewById(R.id.btnCartMinius);
            btnCartPlus = itemView.findViewById(R.id.btnCartPlus);
            txtCardTitle = itemView.findViewById(R.id.txtCardTitle);
            txtCartPrice = itemView.findViewById(R.id.txtCartPrice);
            txtCardSoLuong = itemView.findViewById(R.id.txtCardSoLuong);
            checkAddToOrder = itemView.findViewById(R.id.checkAddToOrder);

        }
    }

    public class CartWithChecked{
        public boolean ischecked;
        String thumbnail, title;
        int soluong, soLuongTorngKho, product_id, pre_id,price;

        public CartWithChecked(boolean ischecked, String thumbnail, String title, int soluong, int soLuongTorngKho, int product_id, int pre_id, int price) {
            this.ischecked = ischecked;
            this.thumbnail = thumbnail;
            this.title = title;
            this.soluong = soluong;
            this.soLuongTorngKho = soLuongTorngKho;
            this.product_id = product_id;
            this.pre_id = pre_id;
            this.price = price;
        }

        public boolean isIschecked() {
            return ischecked;
        }

        public void setIschecked(boolean ischecked) {
            this.ischecked = ischecked;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSoluong() {
            return soluong;
        }

        public void setSoluong(int soluong) {
            this.soluong = soluong;
        }

        public int getSoLuongTorngKho() {
            return soLuongTorngKho;
        }

        public void setSoLuongTorngKho(int soLuongTorngKho) {
            this.soLuongTorngKho = soLuongTorngKho;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getPre_id() {
            return pre_id;
        }

        public void setPre_id(int pre_id) {
            this.pre_id = pre_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
