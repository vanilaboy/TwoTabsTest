package org.bitart.twotabstest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Kot3Api {
    @GET("/xim/api.php")
    Call<Kot3Response> getData(@Query("query") String animalName);
}
