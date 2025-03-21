package com.svalero.tournaments.view.tournament;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.MainAdapter;
import com.svalero.tournaments.contract.tournament.ListNextTournamentsContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.fragment.MapFragment;
import com.svalero.tournaments.presenter.tournament.ListNextTournamentsPresenter;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.util.SortUtil;
import com.svalero.tournaments.view.user.LoginView;
import com.svalero.tournaments.view.team.ListTeamsView;
import com.svalero.tournaments.view.user.ZoneUserView;

import java.util.ArrayList;
import java.util.List;

public class ListNextTournamentsView extends AppCompatActivity implements ListNextTournamentsContract.View{

    private List<Tournament> tournamentsList;
    private ListNextTournamentsContract.Presenter presenter;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        presenter = new ListNextTournamentsPresenter(this);
        presenter.loadNextTournaments();

        tournamentsList = new ArrayList<>();

        RecyclerView nextTournamentsView = findViewById(R.id.nextTournamentsRecycler);
        //To put scroll in component
        nextTournamentsView.hasFixedSize();
        //Set inner layout type
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        nextTournamentsView.setLayoutManager(linearLayoutManager);

        //Link data with adapter and adapter with recycleView
        mainAdapter = new MainAdapter(tournamentsList);
        nextTournamentsView.setAdapter(mainAdapter);
    }

    //Add menu action_bar_main in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        hideMenuItems(menu);
        return true;
    }

    private void hideMenuItems(Menu menu){
        String username = SharedPreferencesUtil.getCustomSharedPreferences(this, "username");
        boolean isUserLogged = (username != null);
        menu.findItem(R.id.menuItemSignIn).setVisible(!isUserLogged);
        menu.findItem(R.id.menuItemRegister).setVisible(!isUserLogged);
        menu.findItem(R.id.menuItemSignOut).setVisible(isUserLogged);
        menu.findItem(R.id.menuItemMyZone).setVisible(isUserLogged);
        if(username != null)
            menu.findItem(R.id.menuItemNameUser).setTitle(getString(R.string.hi) + username);
        menu.findItem(R.id.menuItemNameUser).setVisible(isUserLogged);
    }

    // Select option in menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemTournaments) {
            Intent intent = new Intent(this, ListTournamentsView.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuItemTeams) {
            Intent intent = new Intent(this, ListTeamsView.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuItemSignIn){
            Intent intent = new Intent(this, LoginView.class);
            intent.putExtra("action", "signIn");
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuItemRegister){
            Intent intent = new Intent(this, LoginView.class);
            intent.putExtra("action", "register");
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuItemMyZone){
            Intent intent = new Intent(this, ZoneUserView.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuItemSignOut){
            SharedPreferencesUtil.setCustomSharedPreferences(this, "username", null);
            Intent intent = new Intent(this, ListNextTournamentsView.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void listNextTournaments(List<Tournament> tournamentsList) {
        SortUtil.sortTournamnentsByDate(tournamentsList, true);
        this.tournamentsList.addAll(tournamentsList);
        mainAdapter.notifyDataSetChanged();

        // Create map fragment with api data
        double focusLong =  -0.87734;
        double focusLat = 41.65606;
        String width = "match_parent";
        String height = "302dp";
        MapFragment mapFragment = MapFragment.newInstance(
                new ArrayList<>(tournamentsList),
                focusLong,
                focusLat,
                width,
                height
        );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMapMain, mapFragment);
        transaction.commitNow();
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