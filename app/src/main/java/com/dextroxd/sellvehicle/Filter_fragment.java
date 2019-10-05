package com.dextroxd.sellvehicle;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.activities.filterActivity;
import com.dextroxd.sellvehicle.exploreFragment.ExploreFragment;
import com.dextroxd.sellvehicle.exploreFragment.adapter_explore.GridAdapter;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostOfSearch.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class Filter_fragment extends Fragment {
    SeekBar rent_seekbar;
    TextView text_rent,text_duration,text_distance,location_filter;
    String location;
    int max=50000;int min=1000;int current=10000;// max , min,current are related to rent_seekbar
    boolean bachelorsAllowed = false;
    int selected_bedroom;
    private GridAdapter gridAdapter;
    private ApiInterface mApiInterface;
    int selected_furnishing;
    private RecyclerView recyclerView;
    Button apply_filter;

    public Filter_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.filter_fragment, container, false);
        Toolbar toolbar=view.findViewById(R.id.toolbar_filter);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("FILTER");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().popBackStack();
            }
        });
        mApiInterface = ApiUtils.getAPIService();
        text_rent=view.findViewById(R.id.textView);

        rent_seekbar=view.findViewById(R.id.seekBar);
        rent_seekbar.setMax(max);
        apply_filter = view.findViewById(R.id.apply_filter_button);
        location_filter=view.findViewById(R.id.location_filter);
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
                Toast toast=Toast.makeText(getActivity(),"Range selected from ₹1000 to ₹"+current,Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        RadioGroup bachelors = view.findViewById(R.id.radiogroup);
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
        RadioGroup radio_bedroom = view.findViewById(R.id.radiogroup2);
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
        RadioGroup radio_furnishing = view.findViewById(R.id.radiogroup3);
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
                //Response is from package - com.dextroxd.sellvehicle.network.PostOfSearch;
                Response response = new Response();
                response.setBedroom(selected_bedroom);
                response.setLocation(String.valueOf(location_filter));
                response.setBachelorsAllowed(bachelorsAllowed);
                response.setMaxPrice(current);
                response.setFurnishing(selected_furnishing);
                ((ExploreFragment)getParentFragment()).searchProperty(response);
                getFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }

    }


