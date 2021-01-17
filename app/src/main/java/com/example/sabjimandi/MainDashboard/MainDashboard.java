package com.example.sabjimandi.MainDashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sabjimandi.Account.LoginProcess;
import com.example.sabjimandi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {

            case R.id.navigation_near_me:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_explore:
                fragment = new DashboardFragment();
                break;
            case R.id.navigation_cart:
                fragment = new NotificationFragment();
                break;
            case R.id.navigation_account:
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String phone = sharedPref.getString("phoneNo", "");
                if (phone.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), LoginProcess.class);
                    startActivity(intent);
                }else{
                fragment = new AccountFragment();}
                break;
        }
        return loadFragment(fragment);
    }
}
