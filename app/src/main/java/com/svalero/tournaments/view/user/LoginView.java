package com.svalero.tournaments.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.user.UserLoginContract;
import com.svalero.tournaments.contract.user.UserRegisterContract;
import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.presenter.user.UserLoginPresenter;
import com.svalero.tournaments.presenter.user.UserRegisterPresenter;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.util.ValidateUtil;
import com.svalero.tournaments.view.tournament.ListNextTournamentsView;

public class LoginView extends AppCompatActivity implements UserLoginContract.View, UserRegisterContract.View {

    private String action;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
    }

    public void onClickActionUser(View view){
        User user = ValidateUtil.validateUserData(action, findViewById(R.id.main));
        if(user.getUsername() == null){
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        if(action.equals("signIn")){
            username = user.getUsername();
            UserLoginContract.Presenter presenter = new UserLoginPresenter(this);
            presenter.loginUser(user);
        }else if(action.equals("register")){
            UserRegisterContract.Presenter presenter = new UserRegisterPresenter(this);
            presenter.registerUser(user);
        }
    }

    @Override
    public void getSessionToken(TokenResponse token) {
        action = "";
        Toast.makeText(this, getString(R.string.user_logged), Toast.LENGTH_LONG).show();
        // Save token
        SharedPreferencesUtil.setCustomSharedPreferences(this, "token", token.getToken());
        SharedPreferencesUtil.setCustomSharedPreferences(this, "username", username);
        username = "";
        Intent intent = new Intent(this, ListNextTournamentsView.class);
        startActivity(intent);
    }

    @Override
    public void savedUser(String message) {
        action = "";
        Toast.makeText(this, getString(R.string.user_registered), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ListNextTournamentsView.class);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {
        action = "";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}