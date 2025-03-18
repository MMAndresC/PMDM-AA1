package com.svalero.tournaments.contract.user;

import com.svalero.tournaments.domain.User;

public interface UserRegisterContract {

    interface Model {
        interface OnRegisterUserListener {
            void onRegisterUserSuccess(String message);
            void onRegisterUserError(String message);
        }
        void registerUser(User user, UserRegisterContract.Model.OnRegisterUserListener listener);
    }

    interface View {
        void savedUser(String message);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void registerUser(User user);
    }
}
