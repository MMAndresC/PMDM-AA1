package com.svalero.tournaments.contract;


import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.domain.User;

import java.util.List;

public interface UserLoginContract {

    interface Model {
        interface OnLoginUserListener {
            void onLoginUserSuccess(TokenResponse token);
            void onLoginUserError(String message);
        }
        void loginUser(User user, UserLoginContract.Model.OnLoginUserListener listener);
    }

    interface View {

        void getToken(TokenResponse token);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loginUser(User user);
    }
}
