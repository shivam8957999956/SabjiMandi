package com.example.sabjimandi.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.sabjimandi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAddress extends AppCompatActivity {

    TextView defaultAddress,primaryAddress,secondaryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        defaultAddress=findViewById(R.id.default_address_value);
        primaryAddress=findViewById(R.id.primary_address_value);
        secondaryAddress=findViewById(R.id.secondary_address_value);
        updateData();

    }

    private void updateData() {
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("User");
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String userPhone = sharedPreferences.getString("phoneNo", "");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                defaultAddress.setText(snapshot.child(userPhone).child("address").child("Default Address").getValue().toString());
                primaryAddress.setText(snapshot.child(userPhone).child("address").child("Primary Address").getValue().toString());
                secondaryAddress.setText(snapshot.child(userPhone).child("address").child("Secondary Address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callEditSecondaryAddress(View View) {
        Intent i = new Intent(getApplicationContext(), EditAddress.class);
        i.putExtra("typeOfAddress", "Secondary Address");
        startActivity(i);
        finish();

    }

    public void callEditDefaultAddress(View View) {
        Intent i = new Intent(getApplicationContext(), EditAddress.class);
        i.putExtra("typeOfAddress", "Default Address");
        startActivity(i);
        finish();

    }

    public void callEditPrimaryAddress(View View) {
        Intent i = new Intent(getApplicationContext(), EditAddress.class);
        i.putExtra("typeOfAddress", "Primary Address");
        startActivity(i);
        finish();
    }


}
