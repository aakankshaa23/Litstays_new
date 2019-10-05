package com.dextroxd.sellvehicle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ProfileImageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_view);
        ImageView imageView = findViewById(R.id.image_profile);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        if(imageUrl!=null&&!imageUrl.isEmpty()){
            Picasso.get().load(imageUrl).centerCrop().fit().into(imageView);
        }
    }
}
