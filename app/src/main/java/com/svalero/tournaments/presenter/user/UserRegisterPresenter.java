package com.svalero.tournaments.presenter.user;

import com.svalero.tournaments.contract.user.UserRegisterContract;
import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.model.user.UserRegisterModel;

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
