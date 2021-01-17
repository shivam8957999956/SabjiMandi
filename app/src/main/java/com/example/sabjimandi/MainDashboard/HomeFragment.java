package com.example.sabjimandi.MainDashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabjimandi.Account.SelectDeliveryLocation;
import com.example.sabjimandi.R;
import com.example.sabjimandi.ShopInnerDetails.ShopInnerDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment,container,false);
        final ProgressBar progressBar;
        final RelativeLayout relativeLayoutMainContent;
        final RelativeLayout deliveryLocation;

        //delivery address location

        TextView colonyName,fullAddress;
        colonyName=view.findViewById(R.id.colony);
        fullAddress=view.findViewById(R.id.fullAddress);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        colonyName.setText(sharedPreferences.getString("delivery Colony",""));
        fullAddress.setText(sharedPreferences.getString("delivery Address",""));

//        Toolbar toolbar=view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       //widgets
        final RecyclerView recyclerView;
        DatabaseReference mRef;
        final ArrayList<OfferZoneHelperClass> offerZoneList;

        progressBar=view.findViewById(R.id.progress_bar);
        relativeLayoutMainContent=view.findViewById(R.id.main_content);
        deliveryLocation=view.findViewById(R.id.deliveryLocation);

        deliveryLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), SelectDeliveryLocation.class);
                startActivity(i);

            }
        });



        recyclerView=view.findViewById(R.id.recyclerview_offer);
        mRef= FirebaseDatabase.getInstance().getReference();

        {
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            offerZoneList = new ArrayList<>();
            Query query = mRef.child("General").child("offerZone");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    offerZoneList.clear();
                    for (DataSnapshot offersnapshot : snapshot.getChildren()) {
                        OfferZoneHelperClass offerZoneHelperClass = new OfferZoneHelperClass();
                        offerZoneHelperClass.setImage(offersnapshot.getValue().toString());
                        offerZoneList.add(offerZoneHelperClass);

                    }
                    OfferZoneAdapter offerZoneAdapter = new OfferZoneAdapter(getContext(), offerZoneList);
                    recyclerView.setAdapter(offerZoneAdapter);
                    offerZoneAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        final RecyclerView recyclerViewShop;
        final ArrayList<ShopHeplerClass> shopList;



        recyclerViewShop=view.findViewById(R.id.recyclerview_shops);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerViewShop.setLayoutManager(linearLayoutManager);
        recyclerViewShop.setHasFixedSize(true);
        shopList=new ArrayList<>();
        Query query=mRef.child("General").child("shops");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopList.clear();
                for(DataSnapshot shopsnapshot:snapshot.getChildren()){
                    ShopHeplerClass shopHeplerClass=new ShopHeplerClass();
                    shopHeplerClass.setShopName(shopsnapshot.child("shopName").getValue().toString());
                    shopHeplerClass.setShopGenre(shopsnapshot.child("shopGenre").getValue().toString());
                    shopHeplerClass.setShopImage(shopsnapshot.child("shopImage").getValue().toString());
                    shopHeplerClass.setShopDistanceTime(shopsnapshot.child("shopDistanceTime").getValue().toString());
                    shopHeplerClass.setShopRating(shopsnapshot.child("shopRating").getValue().toString());
                    shopList.add(shopHeplerClass);
                }
                ShopAdapter shopAdapter=new ShopAdapter(getContext(),shopList);
                recyclerViewShop.setAdapter(shopAdapter);
                progressBar.setVisibility(View.GONE);
                relativeLayoutMainContent.setVisibility(View.VISIBLE);
                shopAdapter.notifyDataSetChanged();
                shopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(getContext(), ShopInnerDetails.class);
                        intent.putExtra("position",String.valueOf(position));
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;

    }


}
