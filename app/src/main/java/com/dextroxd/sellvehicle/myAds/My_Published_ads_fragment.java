package com.dextroxd.sellvehicle.myAds;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class My_Published_ads_fragment extends Fragment{
    private RecyclerView recyclerView;
    private ApiInterface mApiInterface;
    SharedPreferences preferences;
    List<Response> responses;
    private RecyclerForMyAds recyclerForMyAds;
    private SkeletonScreen skeletonScreen;
    public My_Published_ads_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_published_ads_fragment, container, false);
        mApiInterface = ApiUtils.getAPIService();
        recyclerView = view.findViewById(R.id.id_recycler_myads);
        responses = new ArrayList<>();
        preferences = getActivity().getApplicationContext().getSharedPreferences("Litstays",0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mApiInterface.getMyProperty(preferences.getString("auth_Token","hell")).enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                Log.e("Hell",response.body().toString());
                // List<Response_Submit> data=new ArrayList(Arrays.asList(response.body()));
                List<Response> data = response.body();
                responses = data;
                recyclerForMyAds = new RecyclerForMyAds(getActivity(),preferences,data);
                recyclerView.setAdapter(recyclerForMyAds);
                recyclerForMyAds.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                Log.d("Faliure", "onFailure: "+t.getMessage());
            }
        });
        enableSwipeToDeleteAndUndo();
        skeletonScreen = Skeleton.bind(recyclerView).adapter(recyclerForMyAds).shimmer(true).count(10).load(R.layout.skeleton_view).show();
        return view;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Response item = recyclerForMyAds.getData().get(position);

                recyclerForMyAds.removeItem(position);


//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        mAdapter.restoreItem(item, position);
//                        recyclerView.scrollToPosition(position);
//                    }
//                });
//
//                snackbar.setActionTextColor(Color.YELLOW);
//                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
