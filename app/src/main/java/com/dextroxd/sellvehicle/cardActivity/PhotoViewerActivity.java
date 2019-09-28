package com.dextroxd.sellvehicle.cardActivity;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dextroxd.sellvehicle.R;

public class PhotoViewerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String url = getIntent().getStringExtra("image");
        // you can show image from url using library like glide or picasso
    }


    protected int getLayoutResourceId() {
        return R.layout.activity_photo_viewer;
    }

    @Override
    public void onBackPressed() {
        PhotoViewerActivity.this.finish();
    }
}