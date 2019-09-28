package com.dextroxd.sellvehicle.Image_fetching;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageFetching {
    String  to_be_returned;
    int mreturn;
    String retrunCode;
    public String fetchImage(Context context, String id) {
        final String url_link = "http://www.litstays.com/wp-json/wp/v2/media/" + id;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject media_details = response.getJSONObject("media_details");
                    JSONObject sizes = media_details.getJSONObject("sizes");
                    JSONObject thumbnail = sizes.getJSONObject("thumbnail");
                    String urlofImage = thumbnail.getString("source_url");
                    if(retrunCode=="500"||retrunCode=="503"||retrunCode=="508"){
                        to_be_returned="null";
                    }
                    else
                        to_be_returned=urlofImage;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());

            }
        })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                 mreturn = response.statusCode;
                 retrunCode=String.valueOf(mreturn);
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS
        ));
        return to_be_returned;
    }

}
