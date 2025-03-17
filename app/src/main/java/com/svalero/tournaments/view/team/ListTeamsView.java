package com.svalero.tournaments.view.team;

import static com.svalero.tournaments.constants.Constants.DATABASE_NAME;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.TeamsListAdapter;
import com.svalero.tournaments.contract.team.ListTeamsContract;
import com.svalero.tournaments.contract.team.RemoveTeamContract;
import com.svalero.tournaments.dao.AppDatabase;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.domain.UserFavourite;
import com.svalero.tournaments.presenter.team.ListTeamsPresenter;
import com.svalero.tournaments.presenter.team.RemoveTeamPresenter;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.view.tournament.ListNextTournamentsView;
import com.svalero.tournaments.view.tournament.ListTournamentsView;
import com.svalero.tournaments.view.user.LoginView;
import com.svalero.tournaments.view.user.ZoneUserView;

import java.util.ArrayList;
import java.util.List;

public class ListTeamsView extends AppCompatActivity implements ListTeamsContract.View, RemoveTeamContract.View {

    private Team selectedTeam;
    private TeamsListAdapter adapter;
    private List<Team> teamList;
    private String username = null;
    private  AppDatabase db;
    private List<UserFavourite> userFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_list_view);

        username = SharedPreferencesUtil.getCustomSharedPreferences(this, "username");

        db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        ListTeamsContract.Presenter presenter = new ListTeamsPresenter(this);
        presenter.loadTeams();

        teamList = new ArrayList<>();

        getFavouritesTeam();

        RecyclerView recyclerView = findViewById(R.id.listTeamsRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TeamsListAdapter(teamList,this, userFavourites, username);
        recyclerView.setAdapter(adapter);
    }

    private void getFavouritesTeam(){
        if(username == null) userFavourites = new ArrayList<>();
        try {
            userFavourites = db.userFavouriteDao().getFavouritesTeams(username);
        }catch(SQLiteConstraintException sce) {
            String message = getString(R.string.db_error);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    //Add menu action_bar_main in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        MenuItem item = menu.findItem(R.id.menuItemTournaments);
        item.setIcon(R.drawable.esports);
        item = menu.findItem(R.id.menuItemTeams);
        item.setIcon(R.drawable.icon_tournament);
        hideMenuItems(menu);
        return true;
    }

    private void hideMenuItems(Menu menu){
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
            Intent intent = new Intent(this, ListNextTournamentsView.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuItemTeams) {
            Intent intent = new Intent(this, ListTournamentsView.class);
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

    //Register context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_context_menu_team, menu);
    }

    //Check selected option in context menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //WITH SWITCH NOT WORK
        if (id == R.id.editMenuTeam) {
            sendToModify();
            return true;
        } else if (id == R.id.removeMenuTeam) {
            createDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    //Create confirm dialog to delete tournament
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.auth_remove_team)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toDelete();
                            }})
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }

    private void toDelete(){
        RemoveTeamContract.Presenter presenterRemove = new RemoveTeamPresenter(this);
        if (selectedTeam != null) {
            String token = SharedPreferencesUtil.getCustomSharedPreferences(this, "token");
            if(token == null){
                String message = getString(R.string.required_user_logged);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                return;
            }
            token = "Bearer " + token;
            presenterRemove.removeTeam(token, selectedTeam.getId());
        }
    }

    private void sendToModify(){
        if (selectedTeam != null) {
            Intent intent = new Intent(this, FormTeamView.class);
            intent.putExtra("team", selectedTeam);
            startActivity(intent);
        }
    }

    @Override
    public void listTeams(List<Team> teamsList) {
        this.teamList.clear();
        this.teamList.addAll(teamsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deletedTeam(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //Delete from favourites local
        try {
            db.userFavouriteDao().deleteFavouriteTeam(username, selectedTeam.getId());
        }catch (SQLiteConstraintException sce) {
            Toast.makeText(this, getString(R.string.db_error), Toast.LENGTH_LONG).show();
        }
        teamList.remove(selectedTeam);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onClickAddTeam(View view){
        Intent intent = new Intent(this, FormTeamView.class);
        startActivity(intent);
    }

    public void setSelectedTeam(Team team){
        this.selectedTeam = team;
    }

}