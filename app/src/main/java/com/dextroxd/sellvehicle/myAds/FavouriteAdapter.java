package com.dextroxd.sellvehicle.myAds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.cardActivity.cardActivity;
import com.dextroxd.sellvehicle.exploreFragment.adapter_explore.GridAdapter;
import com.dextroxd.sellvehicle.exploreFragment.model_explore.ModelCard;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>{
private ArrayList<ModelCard>a1=new ArrayList<>();
private TextView cost,bedroom,furnishing;
private ApiInterface mApiInterface;
        SharedPreferences preferences;
private Context context;
private List<Response> houseList;
        boolean b = false;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView cost, bedroom, size,furnishing,location;
    public ImageView image_house;
    ImageView like_button;
    public MyViewHolder(View view) {
        super(view);
        cost = view.findViewById(R.id.cost_sell);

        bedroom = view.findViewById(R.id.bedroom_sell);
        size = view.findViewById(R.id.size_sell);
        like_button = view.findViewById(R.id.likebutton);
        image_house = view.findViewById(R.id.imageView_house);
        furnishing=view.findViewById(R.id.furnishing);
        location=view.findViewById(R.id.locationofsale);
        like_button = view.findViewById(R.id.likebutton);
    }
}
    public FavouriteAdapter(Context context, List<Response> houseList) {
        this.context=context;
        this.houseList = houseList;
        mApiInterface = ApiUtils.getAPIService();
        preferences = context.getApplicationContext().getSharedPreferences("Litstays",0);
    }
    @NonNull
    @Override
    public FavouriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardsofsale, viewGroup, false);
        return new FavouriteAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final FavouriteAdapter.MyViewHolder myViewHolder, final int i) {
        final Response response = houseList.get(i);
        String furnishing="none";
        if(response.getFurnished()==0)
            furnishing="None";
        else if(response.getFurnished()==1)
            furnishing="Semi";
        else if(response.getFurnished()==2)
            furnishing="Fully";
        myViewHolder.cost.setText("RENT- ₹"+String.valueOf(response.getPrice()));
        myViewHolder.bedroom.setText("Bedrooms-     "+String.valueOf(response.getBedroom()));
        myViewHolder.furnishing.setText("Furnishing-     "+furnishing);
        String gender;
        if(response.getBoysallowed()==true&&response.getGirlsallowed()==false){

            gender="Boys";
        }
        else if(response.getGirlsallowed()==true&&response.getBoysallowed()==false){

            gender="Girls";
        }
        else{

            gender="Both";
        }
        myViewHolder.size.setText("Boys/Girls-     "+gender);
        myViewHolder.location.setText(String.valueOf(response.getLocation()));
        com.dextroxd.sellvehicle.network.RequestofId.Response response1 = new com.dextroxd.sellvehicle.network.RequestofId.Response();
        response1.setId(houseList.get(i).get_id());

        if(!response.getImages().isEmpty())
            Picasso.get().load("http://13.235.43.83:8000/uploads/"+response.getImages().get(0)).centerCrop().fit().into(myViewHolder.image_house);
        myViewHolder.image_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response responsesProperty = houseList.get(i);
                Intent in=new Intent(context, cardActivity.class);
                in.putExtra("Cost",String.valueOf(responsesProperty.getPrice()));
                in.putExtra("Bedroom",String.valueOf(responsesProperty.getBedroom()));
                in.putExtra("id",String.valueOf(responsesProperty.getListedBy()));
                in.putExtra("Furnishing",String.valueOf(responsesProperty.isFurnished()));
                in.putExtra("Title",String.valueOf(responsesProperty.getName()));
                in.putExtra("Type",String.valueOf(responsesProperty.getType()));
                in.putExtra("Location",String.valueOf(responsesProperty.getLocation()));
                in.putExtra("Bathroom",String.valueOf(responsesProperty.getBathroom()));
                in.putExtra("Facing",String.valueOf(responsesProperty.getFacing()));
                in.putExtra("Area",String.valueOf(responsesProperty.getArea()));
                in.putStringArrayListExtra("Array",responsesProperty.getImages());

                in.putExtra("Description",String.valueOf(responsesProperty.getDescription()));
                in.putExtra("Floors",String.valueOf(responsesProperty.getFloors()));
                in.putExtra("Parking",String.valueOf(responsesProperty.isParking()));
                in.putExtra("Bachelors",String.valueOf(responsesProperty.isBachelorsAllowed()));
                context.startActivity(in);
            }
        });
        myViewHolder.like_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myViewHolder.like_button.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp).getConstantState())
                        {
                            removeFavourite(i);
                            myViewHolder.like_button.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        }
                        else{
                            setFavourite(i);
                            myViewHolder.like_button.setImageResource(R.drawable.ic_favorite_black_24dp);
                        }

                    }
                }
        );

        com.dextroxd.sellvehicle.network.RequestofId.Response response12 = new com.dextroxd.sellvehicle.network.RequestofId.Response();
        response12.setId(houseList.get(i).get_id());
        mApiInterface.checkFav((preferences.getString("auth_Token","hell")),response12).enqueue(new Callback<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response>() {
            @Override
            public void onResponse(Call<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> call, retrofit2.Response<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> response) {
                if(response.body().isFav()){
                    myViewHolder.like_button.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                else {
                    myViewHolder.like_button.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
//                Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> call, Throwable t) {
                Toast.makeText(context,"Error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

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

    public void setFavourite(int position){
        com.dextroxd.sellvehicle.network.RequestofId.Response response = new com.dextroxd.sellvehicle.network.RequestofId.Response();
        response.setId(houseList.get(position).get_id());
        mApiInterface.addfav(preferences.getString("auth_Token","hell"),response).enqueue(new Callback<com.dextroxd.sellvehicle.network.RequestofId.Message.Response>() {
            @Override
            public void onResponse(Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> call, retrofit2.Response<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> response) {
                if (response.body().getMessage().toString().equals("success"))
                {
                    Toast.makeText(context,"Successfully Added to favourites",Toast.LENGTH_SHORT).show();
                }
//            else {
//                Toast.makeText(context,"Something unusual happened.",Toast.LENGTH_SHORT).show();
//            }
//            Toast.makeText(context,response.body().getMessage().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> call, Throwable t) {
                Toast.makeText(context,"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    public boolean checkForFavourite(int position){

        com.dextroxd.sellvehicle.network.RequestofId.Response response = new com.dextroxd.sellvehicle.network.RequestofId.Response();
        response.setId(houseList.get(position).get_id());
        mApiInterface.checkFav((preferences.getString("auth_Token","hell")),response).enqueue(new Callback<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response>() {
            @Override
            public void onResponse(Call<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> call, retrofit2.Response<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> response) {
                if(response.body().isFav()){
                    b =true;
                }
            }

            @Override
            public void onFailure(Call<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> call, Throwable t) {
                Toast.makeText(context,"Error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return b;
    }
    public void removeFavourite(final int position){
        com.dextroxd.sellvehicle.network.RequestofId.Response response = new com.dextroxd.sellvehicle.network.RequestofId.Response();
        response.setId(houseList.get(position).get_id());
        mApiInterface.removeFav(preferences.getString("auth_Token","hell"),response).enqueue(new Callback<com.dextroxd.sellvehicle.network.RequestofId.Message.Response>() {
            @Override
            public void onResponse(Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> call, retrofit2.Response<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> response) {
                if (response.body().getMessage().equals("success")){
                    Toast.makeText(context,"Successfully removed from favourites.",Toast.LENGTH_SHORT).show();
                    removeItem(position);

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

    }
    public void removeItem(int position){
    houseList.remove(position);
    notifyItemRemoved(position);
    }
}
