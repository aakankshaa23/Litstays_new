package com.dextroxd.sellvehicle.Login_and_signup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.activities.MainActivity;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.Login.model.Response;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;


public class SignUpActivity extends AppCompatActivity {
    Button signup_button;
    EditText name_text;
    EditText password_text;
    EditText city_text;
    EditText email_text;
    ImageView fb_button;
    ImageView google_button;
    private ApiInterface mApiInterface;
    GoogleSignInClient googleSignInClient;
    TextView login_link;
    private SharedPreferences preferences;
    CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 007;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        preferences = getApplicationContext().getSharedPreferences("Litstays",0);
        if(!TextUtils.equals(preferences.getString("auth_Token","hell"),"hell")){
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
        }
        google_button=findViewById(R.id.btn_google);
        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                googleSignInClient = GoogleSignIn.getClient(SignUpActivity.this, gso);
                signIn();
            }
        });
        callbackManager = CallbackManager.Factory.create();

        login_link = findViewById(R.id.link_login);
        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
        mApiInterface = ApiUtils.getAPIService();
        fb_button = findViewById(R.id.btn_fb);
        fb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                Toast.makeText(SignUpActivity.this,loginResult.getAccessToken().getUserId().toString(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancel() {
                                // App code
                                Toast.makeText(SignUpActivity.this,"Cancel",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                Toast.makeText(SignUpActivity.this,"Error",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    public void onSignup(View v){

        EditText name_text=findViewById(R.id.input_name);
        EditText password_text=findViewById(R.id.input_password);
        EditText phone_text=findViewById(R.id.input_phone);
        EditText email_text=findViewById(R.id.input_email);


        String nameattr=name_text.getText().toString();
        String emailattr=email_text.getText().toString();
        String phoneattr=phone_text.getText().toString();
        String passatr=password_text.getText().toString();

        if(nameattr.matches("") || emailattr.matches("")|| phoneattr.matches("")||passatr.matches(""))
        {
            Toast toast=Toast.makeText(SignUpActivity.this,"Please Enter Details",Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            onSignUp(nameattr,emailattr,passatr,phoneattr);
        }


    }

    public void onSignUp(String name, String email, String password,String phone)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Name",name);
        editor.putString("Email",email);
        editor.putString("Phone",phone);
        editor.apply();
        editor.commit();
        Response response = new Response();
        response.setName(name);
        response.setEmail(email);
        response.setPassword(password);
        response.setPhone(phone);
        mApiInterface.saveUser(response).enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.code()==200)
                {
                    Toast.makeText(SignUpActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(SignUpActivity.this,"Please try again",Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 007:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = (task).getResult(ApiException.class);
                        updateUI(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w("status", "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            updateUI(alreadyloggedAccount);
        } else {
            Log.d("status", "Not logged in");
        }
    }
    private void updateUI(GoogleSignInAccount googleSignInAccount) {
        Intent in=new Intent(SignUpActivity.this, MainActivity.class);
//        email=inputEmail.getText().toString();
//        in.putExtra("data",email);
//        flag=true;
//        in.putExtra("flag",flag);
        startActivity(in);
        finish();
    }
}

