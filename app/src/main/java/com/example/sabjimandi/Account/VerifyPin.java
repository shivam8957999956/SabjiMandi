package com.example.sabjimandi.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.sabjimandi.MainDashboard.MainDashboard;
import com.example.sabjimandi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.concurrent.TimeUnit;

public class VerifyPin extends AppCompatActivity {

    String codeBySystem;
    PinView pinFromUser;
    String phoneNo;
    RoundedHorizontalProgressBar mRoundedHorizontalProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_pin);

        phoneNo = getIntent().getStringExtra("phoneNo");
        mRoundedHorizontalProgressBar=findViewById(R.id.progress_bar_1);
        mRoundedHorizontalProgressBar.animateProgress(2000,0,100);
        pinFromUser = findViewById(R.id.pin_view);
        sendVerificationCodeToUser(phoneNo);
    }

    public void callNextDetailsScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), NewUserDetails.class);
        startActivity(intent);


    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyPin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyPin.this, "DONE", Toast.LENGTH_SHORT).show();
                            mRoundedHorizontalProgressBar.setVisibility(View.GONE);
                            checkpreviousExistence();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyPin.this, "Veriication not successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void checkpreviousExistence() {

        Query query = FirebaseDatabase.getInstance().getReference("User").orderByChild("phoneNo").equalTo(phoneNo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Intent i = new Intent(getApplicationContext(), MainDashboard.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fullname", snapshot.child(phoneNo).child("fullname").getValue().toString());
                    editor.putString("email", snapshot.child(phoneNo).child("email").getValue().toString());
                    editor.putString("Secondary Address", snapshot.child(phoneNo).child("address").child("Secondary Address").getValue().toString());
                    editor.putString("Default Address", snapshot.child(phoneNo).child("address").child("Default Address").getValue().toString());
                    editor.putString("Primary Address", snapshot.child(phoneNo).child("address").child("Primary Address").getValue().toString());
                    editor.putString("phoneNo", phoneNo);
                    editor.apply();
                    startActivity(i);
                    finish();


                } else {
                    storeUserPhoneNo();
                    Intent intent = new Intent(VerifyPin.this, NewUserDetails.class);
                    intent.putExtra("phoneNo", phoneNo);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void storeUserPhoneNo() {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("User");
        mRef.child(phoneNo).child("phoneNo").setValue(phoneNo);

    }


}
