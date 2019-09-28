package com.dextroxd.sellvehicle.activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.exploreFragment.ExploreFragment;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class filterActivity extends AppCompatActivity {
    SeekBar rent_seekbar;
    TextView text_rent,text_duration,text_distance;
    int max=50000;int min=1000;int current=10000;// max , min,current are related to rent_seekbar
    boolean bachelorsAllowed = false;
    int selected_bedroom;
    int selected_furnishing;
    Button apply_filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar=findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FILTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_rent=(TextView)findViewById(R.id.textView);
        rent_seekbar=(SeekBar)findViewById(R.id.seekBar);
        rent_seekbar.setMax(max);
        apply_filter = findViewById(R.id.apply_filter_button);
        rent_seekbar.setProgress(current);
        text_rent.setText(""+current);
        rent_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current=progress;
                text_rent.setText(""+"₹"+current);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast toast=Toast.makeText(filterActivity.this,"Range selected from ₹1000 to ₹"+current,Toast.LENGTH_SHORT);
                toast.show();



            }
        });


        RadioGroup bachelors = (RadioGroup)findViewById(R.id.radiogroup);
        bachelors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioFilterY){
                    bachelorsAllowed = true;
                }
                else if(checkedId==R.id.radioFilterN){
                    bachelorsAllowed = false;
                }

            }
        });
        RadioGroup radio_bedroom = (RadioGroup)findViewById(R.id.radiogroup2);
        radio_bedroom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.one){
                    selected_bedroom=1;

                }
                else if(checkedId==R.id.two){
                    selected_bedroom=2;
                }
                else if(checkedId==R.id.three){
                    selected_bedroom=3;
                }
                else{
                    selected_bedroom=4;
                }
            }
        });
        RadioGroup radio_furnishing = (RadioGroup)findViewById(R.id.radiogroup3);
        radio_furnishing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.unfurnished){
                    selected_furnishing=1;
                }
                else if(checkedId==R.id.semifurnished){
                    selected_furnishing=2;
                }
                else{
                    selected_furnishing=3;
                }
            }
        });
        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
