package com.svalero.tournaments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.UserLoginContract;
import com.svalero.tournaments.domain.TokenResponse;
import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.presenter.UserLoginPresenter;

public class LoginView extends AppCompatActivity implements UserLoginContract.View {

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
    }

    public void onClickActionUser(View view){
        User user = validateUserData();
        if(user.getUsername() == null){
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        UserLoginContract.Presenter presenter = new UserLoginPresenter(this);
        if(action.equals("signIn")){
            presenter.loginUser(user);
        }else if(action.equals("register")){

        }
    }

    private User validateUserData(){
        if(action != null){
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String password = ((EditText) findViewById(R.id.password)).getText().toString();
            if(!email.isBlank() && !password.isBlank())
             return new User(email, password);
        }
        return new User();
    }

    @Override
    public void getToken(TokenResponse token) {
        //TODO guardar token
        Toast.makeText(this, token.getToken(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}