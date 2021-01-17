package com.example.sabjimandi.MainDashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabjimandi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OfferZoneAdapter extends RecyclerView.Adapter<OfferZoneAdapter.ViewHolder> {

    private static final String tag = "RecyclerView";

    private Context mContext;
    private ArrayList<OfferZoneHelperClass> offerZoneList;

    public OfferZoneAdapter(Context mContext, ArrayList<OfferZoneHelperClass> offerZoneList) {
        this.mContext = mContext;
        this.offerZoneList = offerZoneList;
    }

    @NonNull
    @Override
    public OfferZoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_zone_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferZoneAdapter.ViewHolder holder, int position) {
        Picasso.get().load(offerZoneList.get(position).getImage()).into(holder.offerImage);
    }

    @Override
    public int getItemCount() {
        return offerZoneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView offerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerImage=itemView.findViewById(R.id.offer_zone_image);


        }
    }

}
