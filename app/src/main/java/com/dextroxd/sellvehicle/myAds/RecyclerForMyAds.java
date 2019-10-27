package com.dextroxd.sellvehicle.myAds;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.exploreFragment.model_explore.ModelCard;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RecyclerForMyAds extends RecyclerView.Adapter<RecyclerForMyAds.MyViewHolder>{
private ArrayList<ModelCard>a1=new ArrayList<>();
private TextView cost,bedroom,furnishing;
    private ApiInterface mApiInterface;
    SharedPreferences preferences;
    private Context context;
private List<Response> houseList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView cost, bedroom, size;
    public ImageView image_house;
    ImageView like_button;
    public RelativeLayout viewBackground, viewForeground;
    public MyViewHolder(View view) {
        super(view);
        cost = (TextView) view.findViewById(R.id.cost_sell);
        bedroom = (TextView) view.findViewById(R.id.bedroom_sell);
        size = (TextView) view.findViewById(R.id.size_sell);
        like_button=(ImageView)view.findViewById(R.id.likebutton);
        image_house = view.findViewById(R.id.imageView_house);
        like_button = view.findViewById(R.id.likebutton);
        viewBackground = view.findViewById(R.id.view_background);
        viewForeground = view.findViewById(R.id.view_foreground);
    }
}
    public RecyclerForMyAds(Context context,SharedPreferences preferences, List<Response> houseList) {
        this.context=context;
        this.houseList = houseList;
        mApiInterface = ApiUtils.getAPIService();
        this.preferences = preferences;
    }



    @NonNull
    @Override
    public RecyclerForMyAds.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardsofsale, viewGroup, false);
        return new RecyclerForMyAds.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerForMyAds.MyViewHolder myViewHolder, int i) {
        final Response response = houseList.get(i);
        myViewHolder.cost.setText(String.valueOf(response.getPrice()));
        myViewHolder.bedroom.setText(String.valueOf(response.getBedroom()));
        myViewHolder.size.setText(String.valueOf(response.getArea()));
        if(!response.getImages().isEmpty())
            Picasso.get().load("http://13.235.43.83:8000/uploads/"+response.getImages().get(0)).centerCrop().fit().into(myViewHolder.image_house);
        myViewHolder.like_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myViewHolder.like_button.setImageResource(R.drawable.ic_favorite_black_24dp);}
                }
        );
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }


    public void removeItem(int position) {
        com.dextroxd.sellvehicle.network.RequestofId.Response response = new com.dextroxd.sellvehicle.network.RequestofId.Response();
        response.setId(houseList.get(position).get_id());
        mApiInterface.removeProperty(preferences.getString("auth_Token","hell"),response).enqueue(new Callback<com.dextroxd.sellvehicle.network.RequestofId.Message.Response>() {
            @Override
            public void onResponse(Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> call, retrofit2.Response<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> response) {
                if (response.body().getMessage().equals("success")){
                    Toast.makeText(context,"Successfully Deleted.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context,"Something unusual happened.",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> call, Throwable t) {
                Toast.makeText(context,"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        houseList.remove(position);
        notifyItemRemoved(position);
//        data.remove(position);
//        notifyItemRemoved(position);
    }

    public void restoreItem(Response item, int position) {
        houseList.add(position, item);
        notifyItemInserted(position);
    }

    public List<Response> getData() {
        return houseList;
    }


}
