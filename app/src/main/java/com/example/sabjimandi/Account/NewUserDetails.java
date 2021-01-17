package com.example.sabjimandi.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.sabjimandi.AccountDetails;
import com.example.sabjimandi.MainDashboard.AccountFragment;
import com.example.sabjimandi.MainDashboard.MainDashboard;
import com.example.sabjimandi.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewUserDetails extends AppCompatActivity {

    TextInputLayout fullname,email;
    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_details);
        phoneNo=getIntent().getStringExtra("phoneNo");
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);

    }

    public void callFinalAccountDetailsScreen(View view) {
        storeData();
    }

    private void storeData() {
        if(!verifyFullname() | !verifyEmail()){
            return;
        }
        String _fullname=fullname.getEditText().getText().toString();
        String _email=email.getEditText().getText().toString();
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("User");
        mRef.child(phoneNo).child("email").setValue(_email);
        mRef.child(phoneNo).child("fullname").setValue(_fullname);
        mRef.child(phoneNo).child("address").child("Primary Address").setValue("");
        mRef.child(phoneNo).child("address").child("Default Address").setValue("");
        mRef.child(phoneNo).child("address").child("Secondary Address").setValue("");

        SharedPreferences sharedPreferences=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("fullname",_fullname);
        editor.putString("email",_email);
        editor.putString("phoneNo",phoneNo);
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), MainDashboard.class);
        startActivity(intent);
        finish();

    }

    private boolean verifyEmail() {
        String val=email.getEditText().getText().toString();
        if(val.isEmpty()){
            email.setError("please enter the email");
            return false;
        }else
            return true;
    }

    private boolean verifyFullname() {
        String val=fullname.getEditText().getText().toString();
        if(val.isEmpty()){
            fullname.setError("please enter the fullname");
            return false;
        }else
            return true;

    }

}
