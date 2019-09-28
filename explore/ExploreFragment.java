package com.dextroxd.sellvehicle.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dextroxd.sellvehicle.exploreFragment.adapter_explore.GridAdapter;
import com.dextroxd.sellvehicle.exploreFragment.model_explore.ModelCard;
import com.dextroxd.sellvehicle.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.design.internal.BottomNavigationMenu.MAX_ITEM_COUNT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {
    private final String LINK_URL = "http://www.litstays.com/wp-json/wp/v2/properties";
    int p = 1;
    int to_be_returned = 200;
    private GridView gridView;
    private Drawable filter_button;
    String cost, bedroom, furnishing;
    private GridAdapter gridAdapter;
    private ArrayList<ModelCard> modelCards;
    private int visibleThreshold = 6;
    private int currentPage = 1;
    private int previousTotal = 0;
    private boolean loading = true;


    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_explore, container, false);
        modelCards = new ArrayList<>();
        gridView = view.findViewById(R.id.grid_view);
        gridAdapter = new GridAdapter(view.getContext(), modelCards);
        gridView.setAdapter(gridAdapter);
        getJson(LINK_URL,view.getContext());
        gridAdapter.notifyDataSetChanged();
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    Log.e("serrr",firstVisibleItem+" "+visibleItemCount+" "+totalItemCount);
    if(firstVisibleItem+visibleItemCount==totalItemCount){
        p++;
        if(getJson(LINK_URL+"?page="+p,view.getContext())==400){
            return;
        }
    }
            }
        });
        return view;
    }

    public int getJson(String link_url, final Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, link_url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject obj1 = response.optJSONObject(i);
                    JSONObject property = obj1.optJSONObject("property_meta");
                    JSONArray price_array = property.optJSONArray("REAL_HOMES_property_price");
                    String price = price_array.optString(0);
                    //JSONArray bedroom_array = property.optJSONArray("REAL_HOMES_property_bedrooms");
                    //String bedroom = bedroom_array.optString(0);
                    JSONArray size_array = property.optJSONArray("REAL_HOMES_property_size");
                    String size = size_array.optString(0);
                    JSONArray thumbnail_array=property.optJSONArray("_thumbnail_id");
                    String thumbnail_id=thumbnail_array.optString(0);
                    ModelCard m1 = new ModelCard(price, "7", size,thumbnail_id);
                    modelCards.add(m1);


                }

                gridAdapter.notifyDataSetChanged();
//                gridAdapter.notify();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());

            }
        })
        {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                to_be_returned = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(jsonArrayRequest);

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return to_be_returned;
    }
//    public String getThumbnail(String id,Context context){
//        RequestQueue requestQueue=Volley.newRequestQueue(context);
//        String LINK_MEDIA="http://www.litstays.com/wp-json/wp/v2/media"+"/"+id;
//
//        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, LINK_MEDIA, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                JSONObject request=response.optJSONObject("media_details");
//                JSONObject request_size=request.optJSONObject("sizes");
//                JSONObject thumbnail=request_size.optJSONObject("thumbnail");
//                 url_image=thumbnail.optString("source_url");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", error.toString());
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        return url_image;
//
//    }

}










