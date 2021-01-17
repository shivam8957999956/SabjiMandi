package com.example.sabjimandi.MainDashboard.cartAttachments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabjimandi.MainDashboard.Cart;
import com.example.sabjimandi.MainDashboard.ShopAdapter;
import com.example.sabjimandi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<CartHelperClass> cartList;

    public CartAdapter(Context mContext, ArrayList<CartHelperClass> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cartview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        holder.cartName.setText(cartList.get(position).getCartName());
        holder.cartQuantity.setText(cartList.get(position).getCartQuantity());
        holder.cartCost.setText(cartList.get(position).getCartCost());
        Picasso.get().load(cartList.get(position).getCartImage()).into(holder.cartImage);


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cartImage;
        TextView cartCost;
        TextView cartName;
        TextView cartQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartCost=itemView.findViewById(R.id.cart_item_amount);
            cartImage=itemView.findViewById(R.id.cart_item_image);
            cartName=itemView.findViewById(R.id.cart_item_name);
            cartQuantity=itemView.findViewById(R.id.cart_item_quantity);


        }
    }


}
