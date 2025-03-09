package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.UserApiInterface;
import com.svalero.tournaments.contract.UserRegisterContract;
import com.svalero.tournaments.domain.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterModel implements UserRegisterContract.Model {
    @Override
    public void registerUser(User user, OnRegisterUserListener listener) {
        UserApiInterface api = TournamentApi.getUserApi();
        Call<String> getUserCall = api.registerUser(user);
        getUserCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onRegisterUserSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onRegisterUserError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onRegisterUserError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onRegisterUserError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onRegisterUserError("Request failed: " + t.getMessage());
            }
        });
    }
}
