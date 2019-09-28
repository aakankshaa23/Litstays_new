package com.dextroxd.sellvehicle.activities;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;

public class viewEditProfile extends AppCompatActivity {
    private SharedPreferences preferences;
    private EditText editText;
    private EditText editText2;
    private int width,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_profile);
        Toolbar toolbar=findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EDIT PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        preferences =getApplicationContext().getSharedPreferences("Litstays",0);
        TextView textView = findViewById(R.id.change_name);
        TextView textView1 = findViewById(R.id.change_password);
        TextView textView2= findViewById(R.id.change_phone);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxName();
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxPassword();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBoxPhone();
            }
        });
    }
    public void dialogBoxName(){
        final Dialog dialog = new Dialog(viewEditProfile.this);
        dialog.setContentView(R.layout.dialog_phone_name);
        dialog.getWindow().setLayout(width*6/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = dialog.findViewById(R.id.heading);
        textView.setText("Edit Name");
         editText = dialog.findViewById(R.id.updated_value);
        editText.setHint("Enter new Username");
        Button button = dialog.findViewById(R.id.confirm_update);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          if(editText.getText().toString().trim().isEmpty()){
                                              Toast.makeText(viewEditProfile.this,"Please enter new name",Toast.LENGTH_SHORT).show();
                                              return;
                                          }
                                          updateName();
                                          SharedPreferences.Editor editor = preferences.edit();
                                          editor.putString("Name",editText.getText().toString().trim());
                                          editor.apply();
                                          editor.commit();
                                          dialog.dismiss();
                                      }
                                  });
        dialog.show();
    }
    public void updateName(){}
    public void dialogBoxPhone(){
        final Dialog dialog = new Dialog(viewEditProfile.this);
        dialog.setContentView(R.layout.dialog_phone_name);
        dialog.getWindow().setLayout(width*6/7, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = dialog.findViewById(R.id.heading);
        textView.setText("Edit Phone");
        editText = dialog.findViewById(R.id.updated_value);
        editText.setHint("Enter new phone");
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        Button button = dialog.findViewById(R.id.confirm_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(viewEditProfile.this,"Please enter new phone",Toast.LENGTH_SHORT).show();
                    return;
                }
                updateNumber();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Phone",editText.getText().toString().trim());
                editor.apply();
                editor.commit();
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void updateNumber(){}
    public void dialogBoxPassword(){
        final Dialog dialog = new Dialog(viewEditProfile.this);
        dialog.setContentView(R.layout.dialog_phone_name);
        dialog.getWindow().setLayout(width*6/7, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = dialog.findViewById(R.id.heading);
        textView.setText("Edit Password");
        editText = dialog.findViewById(R.id.updated_value);
        editText.setHint("Enter old password");
        editText2 = dialog.findViewById(R.id.confirm_updated_value);
        editText2.setVisibility(View.VISIBLE);
        editText2.setHint("Enter new password");
        Button button = dialog.findViewById(R.id.confirm_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(viewEditProfile.this,"Please enter new phone",Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePassword();
                dialog.dismiss();
            }
        });
        dialog.show();
}
    public void updatePassword(){
    }
}