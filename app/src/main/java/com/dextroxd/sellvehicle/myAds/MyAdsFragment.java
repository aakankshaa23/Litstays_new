package com.dextroxd.sellvehicle.myAds;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dextroxd.sellvehicle.R;


public class MyAdsFragment extends Fragment {
    Animation animFadein;


    public MyAdsFragment() {
        // Required empty public constructor
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment= new My_Published_ads_fragment();
            switch (menuItem.getItemId()){
                case R.id.my_Ads:
                    selectedFragment=new My_Published_ads_fragment();
                    break;
                case R.id.favorites:
                    selectedFragment=new favoritesFragment();
                    break;
                    default:
                        selectedFragment=new My_Published_ads_fragment();
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_ad,selectedFragment).commit();
            return true;

        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(!isConnected(getContext()))buildDialog(getContext()).show();
        View RootView = inflater.inflate(R.layout.fragment_my_ads, container, false);
        animFadein = AnimationUtils.loadAnimation(RootView.getContext(),
                R.anim.fade_in);
        RootView.startAnimation(animFadein);
        FragmentTransaction tx=getFragmentManager().beginTransaction();
        tx.replace(R.id.frame_ad,new My_Published_ads_fragment());
        tx.commit();
        BottomNavigationView bottomNavigationView =(BottomNavigationView)RootView.findViewById(R.id.nav_bar_1);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        // Inflate the layout for this fragment
        return RootView;
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if( netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        return builder;
    }


}
