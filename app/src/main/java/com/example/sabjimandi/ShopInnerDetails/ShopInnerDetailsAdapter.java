package com.example.sabjimandi.ShopInnerDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabjimandi.MainDashboard.OfferZoneAdapter;
import com.example.sabjimandi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopInnerDetailsAdapter extends RecyclerView.Adapter<ShopInnerDetailsAdapter.ViewHolder> {


    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mListener=listener;

    }

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<ShopInnerDetailsHelperClass> shopInnerDetailsList;
    public ShopInnerDetailsAdapter(Context mContext, ArrayList<ShopInnerDetailsHelperClass> shopInnerDetailsList) {
        this.mContext = mContext;
        this.shopInnerDetailsList = shopInnerDetailsList;
    }



    @NonNull
    @Override
    public ShopInnerDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_cardview, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShopInnerDetailsAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(shopInnerDetailsList.get(position).getItemName());
        holder.itemAmount.setText(shopInnerDetailsList.get(position).getItemAmount());
        Picasso.get().load(shopInnerDetailsList.get(position).getItemImage()).into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return shopInnerDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        TextView itemAmount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            itemAmount = itemView.findViewById(R.id.item_amount);
            itemImage = itemView.findViewById(R.id.item_image);

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
