package com.example.sabjimandi.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabjimandi.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAddress extends AppCompatActivity {

    TextInputLayout houseNo, roadName, city, typeOfLocation;
    String typeOfAddress;
    TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        type=findViewById(R.id.type_of_address);

        houseNo = findViewById(R.id.houseNo);
        roadName = findViewById(R.id.road_name);
        city = findViewById(R.id.city);
        typeOfLocation = findViewById(R.id.type_of_location);
        typeOfAddress = getIntent().getStringExtra("typeOfAddress");
        type.setText(typeOfAddress);
    }

    public void callAddData(View view) {

        String _houseNo = houseNo.getEditText().getText().toString().trim();
        String _roadName = roadName.getEditText().getText().toString().trim();
        String _city = city.getEditText().getText().toString().trim();
        String _typeOfLocation = typeOfLocation.getEditText().getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userPhone = sharedPreferences.getString("phoneNo", "");

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("User");
        mRef.child(userPhone).child("address").child(typeOfAddress).setValue(_houseNo + ", " + _roadName + ", " + _city + ", " + _typeOfLocation);
        Toast.makeText(this, "Address Updated", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(typeOfAddress, _houseNo + ", " + _roadName + ", " + _city + ", " + _typeOfLocation);
        editor.putString(typeOfAddress + "Road Name", _roadName);
        editor.apply();
    }

}
