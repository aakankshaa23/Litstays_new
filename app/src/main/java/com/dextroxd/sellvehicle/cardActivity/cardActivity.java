package com.dextroxd.sellvehicle.cardActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.GetDetails.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class cardActivity extends AppCompatActivity {
    MyCustomPagerAdapter myCustomPagerAdapter;
    ViewPager viewPager;
    TextView contact_seller,location_card,type_sell,furnishing_sell,cost_sell,bedroom_sell,size_sell,facing_sell,floors_sell,bathroom_sell,bachelors_sell,parking_sell,description_sell;
    String id,type,furnishing,cost,bedroom,size,facing,Desc,bathroom,floors,location;
    Boolean parking,bachelors;
    EditText contact;
    Button send_button;
    private ApiInterface mApiInterface;

    int images[]={R.drawable.ic_location_on_white_24dp,R.drawable.ic_location_on_white_24dp,R.drawable.ic_location_on_white_24dp,R.drawable.ic_location_on_white_24dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card2);
        Toolbar toolbar=findViewById(R.id.toolbar_card);
        mApiInterface = ApiUtils.getAPIService();
        setSupportActionBar(toolbar);
        contact_seller=findViewById(R.id.contact);
        contact=findViewById(R.id.contact_email);
        send_button=findViewById(R.id.button_contact);
        getSupportActionBar().setTitle("Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        final Intent in=getIntent();
        type=in.getStringExtra("Type");
        id=in.getStringExtra("id");
        Log.e("IDOFUSER",id);
        location=in.getStringExtra("Location");
        cost=in.getStringExtra("Cost");
        bedroom=in.getStringExtra("Bedroom");
        Toast.makeText(this,bedroom,Toast.LENGTH_SHORT).show();
        size=in.getStringExtra("Area");
        facing=in.getStringExtra("Facing");
        bathroom=in.getStringExtra("Bathroom");
        bachelors=in.getBooleanExtra("Bachelors",true);
        parking=in.getBooleanExtra("Parking",true);
        floors=in.getStringExtra("Floors");
        Desc=in.getStringExtra("Description");
        furnishing=in.getStringExtra("Furnishing");
        ArrayList<String> image=in.getStringArrayListExtra("Array");
        cost_sell=findViewById(R.id.cost_sell);
        type_sell=findViewById(R.id.type_sell);
        bedroom_sell=findViewById(R.id.bedroom_sell);
        size_sell=findViewById(R.id.size_sell);
        facing_sell=findViewById(R.id.facing_sell);
        bathroom_sell=findViewById(R.id.bathroom_sell);
        description_sell=findViewById(R.id.description_sell);
        floors_sell=findViewById(R.id.floor_sell);
        location_card=findViewById(R.id.location_sell);
        bachelors_sell=findViewById(R.id.bachelors_Sell);
        parking_sell=findViewById(R.id.parking_sell);
        furnishing_sell=findViewById(R.id.furnishing_sell);

        if(furnishing.equals('0')){
            furnishing_sell.setText(" UNFURNISHED");
        }
        else if(furnishing.equals('1')){
            furnishing_sell.setText(" SEMI-FURNISHED");

        }
        else{
            furnishing_sell.setText(" FULLY FURNISHED");
        }
        cost_sell.setText(" ₹"+cost);
        type_sell.setText("TYPE:- "+type);
        bedroom_sell.setText(" "+bedroom);
        size_sell.setText(" "+size);
        location_card.setText(location);
        facing_sell.setText(" "+facing);
        bathroom_sell.setText(" "+bathroom);
        description_sell.setText(" "+Desc);
        floors_sell.setText(" "+floors);
        if(bachelors)
            bachelors_sell.setText(" YES");
        else
            bachelors_sell.setText(" NO");
        if(parking)
            parking_sell.setText(" YES");
        else
            parking_sell.setText(" NO");
        mApiInterface.getDetails(id).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                contact_seller.setText("Email- "+String.valueOf(response.body().getEmail()));
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

        myCustomPagerAdapter = new MyCustomPagerAdapter(cardActivity.this, image);
        viewPager.setAdapter(myCustomPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact.getText().toString().trim().isEmpty()){
                    Toast.makeText(cardActivity.this,"Please enter the text...",Toast.LENGTH_SHORT).show();
                    return;
                }
                mApiInterface.getDetails(id).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        //contact_seller.setText("EMAIL- "+String.valueOf(response.body().getEmail()));
                        if(response.code()==200){
                            String email=response.body().getEmail();
                            Intent send = new Intent(Intent.ACTION_SENDTO);
                            String uriText = "mailto:" + Uri.encode(email) +
                                    "?subject=" + Uri.encode("I am interested in your property at Litstays") +
                                    "&body=" + Uri.encode(contact.getText().toString().trim());
                            Uri uri = Uri.parse(uriText);

                            send.setData(uri);
                            startActivity(Intent.createChooser(send, "Send mail..."));
                            Toast.makeText(cardActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(cardActivity.this, response.body().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(cardActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

}