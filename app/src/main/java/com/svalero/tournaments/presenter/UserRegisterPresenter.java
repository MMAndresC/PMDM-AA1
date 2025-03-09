package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.UserRegisterContract;
import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.model.UserRegisterModel;

public class UserRegisterPresenter implements UserRegisterContract.Presenter, UserRegisterContract.Model.OnRegisterUserListener {
    private UserRegisterContract.View view;
    private UserRegisterContract.Model model;

    public UserRegisterPresenter(UserRegisterContract.View view){
        this.view = view;
        this.model = new UserRegisterModel();
    }
    @Override
    public void onRegisterUserSuccess(String message) {
       view.savedUser(message);
    }

    @Override
    public void onRegisterUserError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void registerUser(User user) {
        model.registerUser(user, this);
    }
}
