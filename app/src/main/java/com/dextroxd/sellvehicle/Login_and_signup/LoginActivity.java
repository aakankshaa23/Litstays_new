package com.dextroxd.sellvehicle.Login_and_signup;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.activities.MainActivity;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.activities.viewEditProfile;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.Login.model.LoginPost;
import com.dextroxd.sellvehicle.network.Login.model.LoginResponse;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response_Submit;
import com.facebook.login.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView link_signup;
    private ApiInterface mApiInterface;
    SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getApplicationContext().getSharedPreferences("Litstays",0);
        TextView link_signup = findViewById(R.id.link_signup);
        mApiInterface = ApiUtils.getAPIService();
        textView = findViewById(R.id.forgetPassword);
        editor = preferences.edit();
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_phone_name);
                dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels*6/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = dialog.findViewById(R.id.heading);
                textView.setText("Forget Password");
                textView.setTextSize(16.0f);
                final EditText editText = dialog.findViewById(R.id.updated_value);
                editText.setHint("Enter your registered email");
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                Button button = dialog.findViewById(R.id.confirm_update);
                button.setText("Submit");
                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if(TextUtils.isEmpty(editText.getText().toString().trim())){
                                                      Toast.makeText(LoginActivity.this,"Please enter your email id.",Toast.LENGTH_SHORT).show();
                                                      return;
                                                  }
                                                  com.dextroxd.sellvehicle.network.forgetPassword.Response response = new com.dextroxd.sellvehicle.network.forgetPassword.Response();
                                                  response.setEmail(editText.getText().toString().trim());
                                                  mApiInterface.forgetPassword(response).enqueue(new Callback<Response_Submit>() {
                                                      @Override
                                                      public void onResponse(Call<Response_Submit> call, Response<Response_Submit> response) {
                                                          if(response.body().getMsg().equals("success")){
                                                              Toast.makeText(LoginActivity.this,"A password reset link has been sent to your email",Toast.LENGTH_SHORT).show();
                                                          }
                                                          else {
                                                              Toast.makeText(LoginActivity.this,"Please check your email and try again",Toast.LENGTH_SHORT).show();
                                                          }

                                                      }

                                                      @Override
                                                      public void onFailure(Call<Response_Submit> call, Throwable t) {
                                                          Toast.makeText(LoginActivity.this,"Error "+t.getMessage(),Toast.LENGTH_SHORT).show();
                                                      }
                                                  });
                                                  dialog.dismiss();
                                              }

                                          }
                );
                dialog.show();

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
        editor.putString("email",email);
        editor.apply();
        editor.commit();
        mApiInterface.loginUser(loginPost).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.code()==200){
                    Toast.makeText(LoginActivity.this, response.body() != null ? "Success" : "Failure",Toast.LENGTH_SHORT).show();
                    editor.putString("auth_Token",response.body().getAuthToken());
                    editor.putString("idOfUser",response.body().getId());
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
