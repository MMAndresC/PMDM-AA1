package com.svalero.tournaments.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TournamentApi {

    private static final String URL = "http://192.168.1.121:8080/api/v1/";
    public static TournamentApiInterface buildInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TournamentApiInterface.class);
    }
}
