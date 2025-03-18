package com.svalero.tournaments.view.team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.team.AddTeamContract;
import com.svalero.tournaments.contract.team.ModifyTeamContract;
import com.svalero.tournaments.domain.SpinnerOption;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.presenter.team.AddTeamPresenter;
import com.svalero.tournaments.presenter.team.ModifyTeamPresenter;
import com.svalero.tournaments.util.ValidateUtil;

import java.util.ArrayList;

public class FormTeamView extends AppCompatActivity implements AddTeamContract.View, ModifyTeamContract.View {

    private String action;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_form_view);

        //Load spinner with position 0 as default
        ArrayList<SpinnerOption> options = getRegionOptions();
        ArrayAdapter<SpinnerOption> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner regionSpinner = findViewById(R.id.editTeamRegion);
        regionSpinner.setAdapter(adapter);

        Intent intent = getIntent();
        Team team = intent.getParcelableExtra("team");
        if(team != null){
            action = "modify";
            id = team.getId();
            loadData(team);
        } else action = "add";
    }

    public void onClickSaveTeam(View view){
        Team team = ValidateUtil.validateTeamForm(findViewById(R.id.main));
        if(team.getName() == null) {
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        if(action.equals("add")) {
            AddTeamContract.Presenter presenter = new AddTeamPresenter(this);
            presenter.saveTeam(team);
        }else if(action.equals("modify")){
            ModifyTeamContract.Presenter presenter = new ModifyTeamPresenter(this);
            presenter.modifyTeam(team, id);
        }

        action = "";
        changeActivity();
    }

    public ArrayList<SpinnerOption> getRegionOptions(){
        ArrayList<SpinnerOption> options = new ArrayList<>();
        options.add(new SpinnerOption(1, getString(R.string.region_NA)));
        options.add(new SpinnerOption(2, getString(R.string.region_SA)));
        options.add(new SpinnerOption(3, getString(R.string.region_EU)));
        options.add(new SpinnerOption(4, getString(R.string.region_AS)));
        options.add(new SpinnerOption(5, getString(R.string.region_OC)));
        return options;
    }

    @Override
    public void updatedTeam(Team team) {
        action = "";
        changeActivity();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void changeActivity(){
        //Redirection to list view
        Intent intent = new Intent(this, ListTeamsView.class);
        startActivity(intent);
        //Not let come back with back button
        finish();
    }

    private void loadData(Team team){
        ((EditText) findViewById(R.id.editTeamName)).setText(team.getName());
        ((EditText) findViewById(R.id.editTeamRepresentative)).setText(team.getRepresentative());
        ((EditText) findViewById(R.id.editTeamAddress)).setText(team.getAddress());
        ((EditText) findViewById(R.id.editTeamPhone)).setText(team.getPhone());
        ((CheckBox) findViewById(R.id.editTeamPartner)).setChecked(team.isPartner());
        ((Spinner) findViewById(R.id.editTeamRegion)).setSelection(team.getRegion() - 1);
    }
}