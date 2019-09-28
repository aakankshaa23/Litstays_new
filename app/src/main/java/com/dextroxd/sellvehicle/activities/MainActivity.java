package com.dextroxd.sellvehicle.activities;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.Sell.SellFragment;
import com.dextroxd.sellvehicle.exploreFragment.ExploreFragment;
import com.dextroxd.sellvehicle.myAds.MyAdsFragment;
import com.dextroxd.sellvehicle.my_account.MyAccountFragment;
import com.facebook.share.Share;

public class MainActivity extends AppCompatActivity {
    //Bottom Navigation Bar and its navigator
    //Just to select the various fragments associated with the navigation bar ©Dextroxd(DIVYANSHU)


    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            android.support.v4.app.Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.explore:
                    selectedFragment = new ExploreFragment();
                    break;
                case R.id.sell:
                    selectedFragment = new SellFragment();
                    break;
                case R.id.myads:
                    selectedFragment = new MyAdsFragment();
                    break;
                case R.id.myaccount:
                    selectedFragment = new MyAccountFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Litstays",0);
        Toast.makeText(MainActivity.this,preferences.getString("auth_Token","hell"),Toast.LENGTH_SHORT).show();
        Log.i("authTpken",preferences.getString("auth_Token","hell"));
        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ExploreFragment()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("permission",String.valueOf(grantResults[0]));

        }
    }
    private boolean hasStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
