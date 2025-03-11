package com.svalero.tournaments.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TournamentApi {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.1.121:8080/api/v1/";
    public static Retrofit buildInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) //To string
                    .addConverterFactory(GsonConverterFactory.create(gson))//To JSON
                    .build();
        }
        return retrofit;
    }

    public static UserApiInterface getUserApi() {
        return buildInstance().create(UserApiInterface.class);
    }

    public static TournamentApiInterface getTournamentApi() {
        return buildInstance().create(TournamentApiInterface.class);
    }

    public static TeamApiInterface getTeamApi() {
        return buildInstance().create(TeamApiInterface.class);
    }
}
