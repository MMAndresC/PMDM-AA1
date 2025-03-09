package com.svalero.tournaments.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.UserLoginContract;
import com.svalero.tournaments.contract.UserRegisterContract;
import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.presenter.UserLoginPresenter;
import com.svalero.tournaments.presenter.UserRegisterPresenter;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.util.ValidateUtil;

public class LoginView extends AppCompatActivity implements UserLoginContract.View, UserRegisterContract.View {

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
    }

    public void onClickActionUser(View view){
        User user = ValidateUtil.validateUserData(action, findViewById(R.id.containerLogin));
        if(user.getUsername() == null){
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        if(action.equals("signIn")){
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
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

    @Override
    public void savedUser(String message) {
        action = "";
        Toast.makeText(this, getString(R.string.user_registered), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainView.class);
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