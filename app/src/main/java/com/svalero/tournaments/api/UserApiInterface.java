package com.svalero.tournaments.api;

import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiInterface {

    @POST("login")
    Call<TokenResponse> loginUser(@Body User user);

    @POST("register")
    Call<User> registerUser(@Body User user);

}
