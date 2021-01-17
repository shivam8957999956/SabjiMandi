package com.example.sabjimandi.MainDashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sabjimandi.Account.LoginProcess;
import com.example.sabjimandi.Account.UserAddress;
import com.example.sabjimandi.Account.VerifyPin;
import com.example.sabjimandi.R;

public class AccountFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.account_fragment,container,false);
        TextView fullname,email,phoneNo;

        RelativeLayout relativeLayoutLogout,manageAddress;

        fullname=view.findViewById(R.id.details_fullname);
        email=view.findViewById(R.id.details_email);
        phoneNo=view.findViewById(R.id.details_phoneNo);

        //relative Layouts
        relativeLayoutLogout=view.findViewById(R.id.logout);
        manageAddress=view.findViewById(R.id.manage_address);



        final SharedPreferences sharedPref=getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            fullname.setText(sharedPref.getString("fullname",""));
            phoneNo.setText(sharedPref.getString("phoneNo",""));
            email.setText(sharedPref.getString("email",""));

            manageAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(), UserAddress.class);
                    startActivity(i);
                }
            });

            relativeLayoutLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(),LoginProcess.class);
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("fullname","");
                    editor.putString("email","");
                    editor.putString("phoneNo","");
                    editor.putString("Default Address","");
                    editor.putString("Primary Address","");
                    editor.putString("Secondary Address","");
                    editor.putString("delivery Colony","Add Location");
                    editor.putString("delivery Address","Select the saved Location");
                    editor.apply();
                    startActivity(i);
                    getActivity().finish();

                }
            });

        return view;
    }
}
