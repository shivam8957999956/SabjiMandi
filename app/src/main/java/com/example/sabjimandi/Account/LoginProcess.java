package com.example.sabjimandi.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sabjimandi.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class LoginProcess extends AppCompatActivity {
    TextInputLayout phoneNo;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_process);
        phoneNo=findViewById(R.id.phone_number);

        countryCodePicker=findViewById(R.id.countryCodePicker);


    }

    public void callPinVerify(View view){
        String _phoneNo=phoneNo.getEditText().getText().toString();
        String _completephone="+"+ countryCodePicker.getFullNumber()+_phoneNo;

        Intent intent=new Intent(getApplicationContext(),VerifyPin.class);
        intent.putExtra("phoneNo",_completephone);
        startActivity(intent);
        finish();


    }

}
