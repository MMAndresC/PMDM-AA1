package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.UserLoginContract;
import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.model.UserLoginModel;

public class UserLoginPresenter implements UserLoginContract.Presenter, UserLoginContract.Model.OnLoginUserListener {

    private UserLoginContract.View view;
    private UserLoginContract.Model model;

    public UserLoginPresenter(UserLoginContract.View view){
        this.view = view;
        this.model = new UserLoginModel();
    }
    @Override
    public void loginUser(User user) {
        model.loginUser(user, this);
    }

    @Override
    public void onLoginUserSuccess(TokenResponse token) {
        view.getSessionToken(token);
    }

    @Override
    public void onLoginUserError(String message) {
        view.showErrorMessage(message);
    }
}
