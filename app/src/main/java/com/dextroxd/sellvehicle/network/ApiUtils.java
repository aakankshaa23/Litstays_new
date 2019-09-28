package com.dextroxd.sellvehicle.network;

import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.RetrofitClient;

public class ApiUtils
{
    public ApiUtils() {
    }
    public static final String BASE_URL = "http:13.235.43.83:8000/api/";

    public static ApiInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
