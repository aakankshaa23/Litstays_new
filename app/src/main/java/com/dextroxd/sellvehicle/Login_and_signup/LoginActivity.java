package com.dextroxd.sellvehicle.Login_and_signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.activities.MainActivity;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.Login.model.LoginPost;
import com.dextroxd.sellvehicle.network.Login.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView link_signup;
    private ApiInterface mApiInterface;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getApplicationContext().getSharedPreferences("Litstays",0);
        TextView link_signup = findViewById(R.id.link_signup);
        mApiInterface = ApiUtils.getAPIService();
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void onLogin(View v)
    {

        EditText email_login=findViewById(R.id.input_email_login);
        EditText password_login=findViewById(R.id.input_password_login);
        String email_attr_login=email_login.getText().toString();
        String password_attr_login=password_login.getText().toString();
        if(email_attr_login.matches("")||password_attr_login.matches(""))
        {
            Toast toast=Toast.makeText(LoginActivity.this,"Please Enter Details",Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            onLogin(email_attr_login,password_attr_login);
        }

    }
    public void onLogin(String email,String password){

        LoginPost loginPost = new LoginPost();
        loginPost.setEmail(email);
        loginPost.setPassword(password);
        mApiInterface.loginUser(loginPost).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.code()==200){
                    Toast.makeText(LoginActivity.this, response.body() != null ? "Success" : "Failure",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("auth_Token",response.body().getAuthToken());
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}
