package com.svalero.tournaments.contract.user;


import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.User;

public interface UserLoginContract {

    interface Model {
        interface OnLoginUserListener {
            void onLoginUserSuccess(TokenResponse token);
            void onLoginUserError(String message);
        }
        void loginUser(User user, UserLoginContract.Model.OnLoginUserListener listener);
    }

    interface View {
        void getSessionToken(TokenResponse token);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loginUser(User user);
    }
}
