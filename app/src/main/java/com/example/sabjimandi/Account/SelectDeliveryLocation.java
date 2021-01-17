package com.example.sabjimandi.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabjimandi.MainDashboard.MainDashboard;
import com.example.sabjimandi.R;

public class SelectDeliveryLocation extends AppCompatActivity {


    TextView defaultAddress, primaryAddress, secondaryAddress;

    RelativeLayout defaultDelivery, primaryDelivery, secondaryDelivery;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_location);

        defaultAddress = findViewById(R.id.default_address);
        primaryAddress = findViewById(R.id.primary_address);
        secondaryAddress = findViewById(R.id.secondary_address);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("Default Address", "").equals("")) {
            defaultAddress.setText("Address not Saved Yet");
        } else {
            defaultAddress.setText(sharedPreferences.getString("Default Address", ""));
        }

        if (sharedPreferences.getString("Primary Address","").equals("")){
           primaryAddress.setText("Address not Saved Yet");
        }else{
            primaryAddress.setText(sharedPreferences.getString("Primary Address",""));
        }
        if (sharedPreferences.getString("Secondary Address", "").equals("")) {
           secondaryAddress.setText("Address not yet Saved");
        } else {
            secondaryAddress.setText(sharedPreferences.getString("Secondary Address", ""));
        }


        defaultDelivery = findViewById(R.id.defaultDelivery);
        primaryDelivery = findViewById(R.id.primaryDelivery);
        secondaryDelivery = findViewById(R.id.secondaryDelivery);

        defaultDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("Default Address", "").equals("")) {

                    Toast.makeText(SelectDeliveryLocation.this, "Address not saved Yet", Toast.LENGTH_SHORT).show();

                } else {
                    String roadname = sharedPreferences.getString("Default AddressRoad Name", "");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("delivery Colony", roadname);
                    editor.putString("delivery Address", sharedPreferences.getString("Default Address", ""));
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(), MainDashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(i);
                    finish();
                }
            }
        });
        primaryDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("Primary Address", "").equals("")) {

                    Toast.makeText(SelectDeliveryLocation.this, "Address not saved Yet", Toast.LENGTH_SHORT).show();

                } else {
                    String roadname = sharedPreferences.getString("Primary AddressRoad Name", "");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("delivery Colony", roadname);
                    editor.putString("delivery Address", sharedPreferences.getString("Primary Address", ""));
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(), MainDashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        });
        secondaryDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("Secondary Address", "").equals("")) {

                    Toast.makeText(SelectDeliveryLocation.this, "Address not saved Yet", Toast.LENGTH_SHORT).show();

                } else {
                    String roadname = sharedPreferences.getString("Secondary AddressRoad Name", "");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("delivery Colony", roadname);
                    editor.putString("delivery Address", sharedPreferences.getString("Secondary Address", ""));
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(), MainDashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
