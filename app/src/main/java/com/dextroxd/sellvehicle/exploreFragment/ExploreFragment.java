package com.dextroxd.sellvehicle.exploreFragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dextroxd.sellvehicle.RecyclerItemClickListener;
import com.dextroxd.sellvehicle.cardActivity.cardActivity;
import com.dextroxd.sellvehicle.exploreFragment.adapter_explore.GridAdapter;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.Filter_fragment;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.v7.widget.RecyclerView.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment implements Animation.AnimationListener {
    public static final String TAG="Explore Fragment";
    int p = 1;
    int to_be_returned = 200;
    Context context;
    EditText editText;
    private RecyclerView recyclerView;
    ImageButton button;
    int rent = 0;
    int furnished = 0;
    int bedrooms = 1;
    boolean bachelors = true;

    private ApiInterface mApiInterface;
    private ImageButton filter_button;
    String cost, bedroom, furnishing;
    private GridAdapter gridAdapter;
    private SkeletonScreen skeletonScreen;


//    private ArrayList<ModelCard> modelCards;
    private int visibleThreshold = 6;

    List<Response> responsesProperty;
    private int currentPage = 1,visibleItemCount,totalItemCount,firstVisibleItem;
    private int previousTotal = 0;
    private boolean loading = true;
    Animation animFadein;



    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         final  View view = inflater.inflate(R.layout.fragment_explore, container, false);
        context = view.getContext();
        animFadein = AnimationUtils.loadAnimation(context,
                R.anim.fade_in);
        mApiInterface = ApiUtils.getAPIService();
        responsesProperty =  new ArrayList<>();
        editText = view.findViewById(R.id.name_edit_text);
        recyclerView = view.findViewById(R.id.id_recycler_explore);
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent in=new Intent(view.getContext(), cardActivity.class);
//                in.putExtra("Cost",String.valueOf(responsesProperty.get(position).getPrice()));
//                in.putExtra("Bedroom",String.valueOf(responsesProperty.get(position).getBedroom()));
////                Toast.makeText(getActivity(),String.valueOf(responsesProperty.get(position).getBedroom()),Toast.LENGTH_SHORT).show();
//                in.putExtra("Furnishing",String.valueOf(responsesProperty.get(position).isFurnished()));
//                in.putExtra("Type",String.valueOf(responsesProperty.get(position).getType()));
//                in.putExtra("Bathroom",String.valueOf(responsesProperty.get(position).getBathroom()));
//                in.putExtra("Facing",String.valueOf(responsesProperty.get(position).getFacing()));
//                in.putExtra("Area",String.valueOf(responsesProperty.get(position).getArea()));
//                in.putStringArrayListExtra("Array",responsesProperty.get(position).getImages());
//                in.putExtra("Description",String.valueOf(responsesProperty.get(position).getDescription()));
//                in.putExtra("Floors",String.valueOf(responsesProperty.get(position).getFloors()));
//                in.putExtra("Parking",String.valueOf(responsesProperty.get(position).isParking()));
//                in.putExtra("Bachelors",String.valueOf(responsesProperty.get(position).isBachelorsAllowed()));
//                startActivity(in);
//            }
//        }));
        button = view.findViewById(R.id.search_name);
        view.startAnimation(animFadein);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        mApiInterface.getProperty().enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                Log.e("Hell",String.valueOf(response.body().size()));
                List<Response> data = response.body();
                responsesProperty = data;
                gridAdapter = new GridAdapter(context,data);
                recyclerView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
               // Toast.makeText(getActivity(), "Checking "+response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "Failure - "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(),"Please enter the name of property",Toast.LENGTH_SHORT).show();
                    return;
                }
//                com.dextroxd.sellvehicle.network.PostOfSearch.Response response = new com.dextroxd.sellvehicle.network.PostOfSearch.Response();
//                response.setName(editText.getText().toString().trim());
//                searchProperty(response);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",editText.getText().toString().trim());
                searchProperty(hashMap);
            }
        });
        filter_button=(ImageButton)view.findViewById(R.id.filter_button);
        filter_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(context, filterActivity.class));
                getChildFragmentManager().beginTransaction().replace(R.id.frame_explore,new Filter_fragment()).addToBackStack(null).commit();

            }
        });



        skeletonScreen = Skeleton.bind(recyclerView).adapter(gridAdapter).shimmer(true).count(10).load(R.layout.skeleton_view).show();
       return view;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    public void searchProperty(HashMap<String,Object> response){
//        String body = response.toString();
        mApiInterface.searchProperty(response).enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                List<Response> data = response.body();
                Log.e("whatyougot",String.valueOf(data.size()));
//                Toast.makeText(getActivity(),data.toString(),Toast.LENGTH_SHORT).show();
                responsesProperty = data;
                gridAdapter = new GridAdapter(context,data);
//                Log.e("DataofFilter",data.toString());
                recyclerView.setAdapter(gridAdapter);
//                gridAdapter.setHasStableIds(true);
                gridAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Toast.makeText(getActivity(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

}










