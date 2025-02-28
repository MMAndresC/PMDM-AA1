package com.svalero.tournaments.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TournamentApi {

    private static final String BASE_URL = "http://192.168.1.121:8080/api/v1/";
    public static TournamentApiInterface buildInstance() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(TournamentApiInterface.class);
    }
}
