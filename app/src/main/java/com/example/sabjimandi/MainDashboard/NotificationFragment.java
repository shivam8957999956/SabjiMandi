package com.example.sabjimandi.MainDashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabjimandi.MainDashboard.cartAttachments.CartAdapter;
import com.example.sabjimandi.MainDashboard.cartAttachments.CartHelperClass;
import com.example.sabjimandi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment, container, false);


        final ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        final RelativeLayout cartIsEmpty, cartIsNotEmpty;
        final RecyclerView cartListRecyclerView;
        final ArrayList<CartHelperClass> cartList;
        final TextView totalCost;
        cartListRecyclerView = view.findViewById(R.id.cartList);
        cartIsEmpty = view.findViewById(R.id.content_if_cart_is_empty);
        cartIsNotEmpty = view.findViewById(R.id.content_if_cart_not_empty);
        totalCost=view.findViewById(R.id.total_cost);
        DatabaseReference mRef;
        cartList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("phoneNo", "");
        final int[] f = {1};
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cartListRecyclerView.setLayoutManager(layoutManager);
        cartListRecyclerView.hasFixedSize();

            mRef = FirebaseDatabase.getInstance().getReference("User").child(phone).child("cart");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartList.clear();
                    int cost=0;
                    for (DataSnapshot cartSnapshot : snapshot.getChildren()) {
                        f[0] =0;
                        CartHelperClass cartHelperClass = new CartHelperClass();
                        cartHelperClass.setCartCost(cartSnapshot.child("itemCost").getValue().toString());
                        cartHelperClass.setCartName(cartSnapshot.child("itemName").getValue().toString());
                        cartHelperClass.setCartImage(cartSnapshot.child("itemImage").getValue().toString());
                        cartHelperClass.setCartQuantity(cartSnapshot.child("itemQuantity").getValue().toString());
                        cost=cost+Integer.parseInt(cartSnapshot.child("itemCost").getValue().toString());
                        cartList.add(cartHelperClass);

                    }
                    CartAdapter cartAdapter = new CartAdapter(getContext(), cartList);
                    cartListRecyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                    totalCost.setText(String.valueOf(cost));
                    if(f[0]==0){
                        cartIsNotEmpty.setVisibility(View.VISIBLE);
                        cartIsEmpty.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                    }else{
                        cartIsNotEmpty.setVisibility(View.GONE);
                        cartIsEmpty.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    cartIsNotEmpty.setVisibility(View.GONE);
                    cartIsEmpty.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }
            });

        return view;
    }
}
