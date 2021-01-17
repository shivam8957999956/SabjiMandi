package com.example.sabjimandi.MainDashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabjimandi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<ShopHeplerClass> shopList;

    public ShopAdapter(Context mContext, ArrayList<ShopHeplerClass> shopList) {
        this.mContext = mContext;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        holder.shopName.setText(shopList.get(position).getShopName());
        holder.shopGenre.setText(shopList.get(position).getShopGenre());
        holder.shopRating.setText(shopList.get(position).getShopRating());
        holder.shopDistanceTime.setText(shopList.get(position).getShopDistanceTime());
        Picasso.get().load(shopList.get(position).getShopImage()).into(holder.shopImage);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView shopImage;
        TextView shopName, shopRating, shopDistanceTime, shopGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.shop_image);
            shopDistanceTime = itemView.findViewById(R.id.shop_distance_time);
            shopName = itemView.findViewById(R.id.shop_name);
            shopRating = itemView.findViewById(R.id.shop_rating);
            shopGenre = itemView.findViewById(R.id.shop_genre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }

}
