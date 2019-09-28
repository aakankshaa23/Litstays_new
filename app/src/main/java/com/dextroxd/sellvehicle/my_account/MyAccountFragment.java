package com.dextroxd.sellvehicle.my_account;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.Login_and_signup.LoginActivity;
import com.dextroxd.sellvehicle.activities.viewEditProfile;
import com.dextroxd.sellvehicle.R;
import com.facebook.login.Login;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    ImageButton viewProfile;
    Button logout;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    Animation animFadein;
    View view;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_account, container, false);
        animFadein = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fade_in);
        view.startAnimation(animFadein);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(view.getContext());
        SharedPreferences preferences = view.getContext().getSharedPreferences("Litstays",0);
        TextView textView = view.findViewById(R.id.text_pic);
        textView.setText(preferences.getString("Name","Name"));
        TextView textView1 = view.findViewById(R.id.profile_email);
        textView1.setText(preferences.getString("Email","abc@gmail.com"));
        TextView textView2 = view.findViewById(R.id.profile_number);
        textView2.setText(preferences.getString("Phone","1234567890"));
        viewProfile=view.findViewById(R.id.id_viewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(view.getContext(), viewEditProfile.class);
                startActivity(in);
            }
        });
        logout=view.findViewById(R.id.logout_user);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(view.getContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(view.getContext(), LoginActivity.class);
                                startActivity(i);
                                getActivity().finish();

                            }
                        });
            }


        });


        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

}
