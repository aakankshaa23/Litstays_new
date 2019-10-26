package com.dextroxd.sellvehicle.myAds;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.exploreFragment.adapter_explore.GridAdapter;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class favoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiInterface mApiInterface;
    SharedPreferences preferences;
    List<Response> responses;
    private SkeletonScreen skeletonScreen;
    FavouriteAdapter favouriteAdapter;

    public favoritesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_favorites, container, false);
        mApiInterface = ApiUtils.getAPIService();
        recyclerView = view.findViewById(R.id.id_recycler_myads);
        responses = new ArrayList<>();
        preferences = getActivity().getApplicationContext().getSharedPreferences("Litstays",0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mApiInterface.getFav(preferences.getString("auth_Token","hell")).enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                List<Response> data = response.body();
                responses = data;
                favouriteAdapter = new FavouriteAdapter(getActivity(),data);
                recyclerView.setAdapter(favouriteAdapter);
                favouriteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.d("Faliure", "onFailure: "+t.getMessage());

            }
        });
        skeletonScreen = Skeleton.bind(recyclerView).adapter(favouriteAdapter).shimmer(true).count(10).load(R.layout.skeleton_view).show();
      return view;
    }


}
