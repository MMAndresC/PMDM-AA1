package com.svalero.tournaments.view.user;

import static com.svalero.tournaments.constants.Constants.DATABASE_NAME;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.svalero.tournaments.R;
import com.svalero.tournaments.dao.AppDatabase;
import com.svalero.tournaments.domain.SpinnerOption;
import com.svalero.tournaments.domain.UserData;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.view.tournament.ListNextTournamentsView;

import java.util.ArrayList;

public class ZoneUserView extends AppCompatActivity {

    private UserData userData;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_user_view);

        //Load spinner with position 0 as default
        ArrayList<SpinnerOption> options = getRegionOptions();
        ArrayAdapter<SpinnerOption> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner regionSpinner = findViewById(R.id.editRegionUserData);
        regionSpinner.setAdapter(adapter);

        username = SharedPreferencesUtil.getCustomSharedPreferences(this, "username");
        if(username != null){
            //DB local
            final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();

            try {
                userData = db.userDataDao().getUserData(username);
                loadData();
            } catch (SQLiteConstraintException sce) {
                String message = getString(R.string.db_error);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ListNextTournamentsView.class);
                startActivity(intent);
                finish();
            }
        } else{
            Intent intent = new Intent(this, ListNextTournamentsView.class);
            startActivity(intent);
            finish();
        }
    }

    //Create confirm dialog to delete tournament
    private void createDialog(String message, String action){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String definitiveAction = "";
                                if(action.equals("save") && userData != null)
                                    definitiveAction = "update";
                                else definitiveAction = action;
                                actionsUserData(definitiveAction);
                            }})
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }

    private void actionsUserData(String action){
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        try {
            String message = "";
            if(action.equals("save")){
                getData();
                db.userDataDao().insert(userData);
                message = getString(R.string.user_data_saved);
            }
            if(action.equals("update")){
                getData();
                db.userDataDao().update(userData);
                message = getString(R.string.user_data_saved);
            }
            if(action.equals("delete")){
                db.userDataDao().deleteByName(username);
                resetView();
                userData = null;
                message = getString(R.string.user_data_deleted);
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException sce) {
            String message = getString(R.string.db_error);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    //Add menu action_bar_user_data in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_user_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveUserDataMenu) {
            createDialog(getString(R.string.save_user_data), "save");
        }else if(item.getItemId() == R.id.deleteUserDataMenu) {
            createDialog(getString(R.string.delete_user_data), "delete");
        }
        return true;
    }

    public ArrayList<SpinnerOption> getRegionOptions(){
        ArrayList<SpinnerOption> options = new ArrayList<>();
        options.add(new SpinnerOption(0, "Region"));
        options.add(new SpinnerOption(1, getString(R.string.region_NA)));
        options.add(new SpinnerOption(2, getString(R.string.region_SA)));
        options.add(new SpinnerOption(3, getString(R.string.region_EU)));
        options.add(new SpinnerOption(4, getString(R.string.region_AS)));
        options.add(new SpinnerOption(5, getString(R.string.region_OC)));
        return options;
    }

    private void loadData(){
        if(userData == null) return;
        String alias = userData.getAlias();
        if(alias == null) alias = "";
        ((EditText) findViewById(R.id.editAliasUserData)).setText(alias);
        ((Spinner) findViewById(R.id.editRegionUserData)).setSelection(userData.getRegion());
        String role = userData.getMainRole();
        if(role.isBlank()) return;
        if(role.equals("support"))
            ((RadioButton) findViewById(R.id.radioButtonSupport)).setChecked(true);
        else if(role.equals("dps"))
            ((RadioButton) findViewById(R.id.radioButtonDps)).setChecked(true);
        else if(role.equals("tank"))
            ((RadioButton) findViewById(R.id.radioButtonTank)).setChecked(true);
    }

    private void getData(){
        userData = new UserData();
        userData.setUsername(username);
        String alias = ((EditText) findViewById(R.id.editAliasUserData)).getText().toString();
        userData.setAlias(alias);
        userData.setRegion(getRegionFromSpinner());
        userData.setMainRole(getMainRoleFromRadioGroup());
    }

    private int getRegionFromSpinner() {
        Spinner spinner = findViewById(R.id.editRegionUserData);
        SpinnerOption regionSelected = (SpinnerOption) spinner.getSelectedItem();
        return regionSelected.getId();
    }

    private String getMainRoleFromRadioGroup() {
        if(((RadioButton) findViewById(R.id.radioButtonSupport)).isChecked())
            return "support";
        else if(((RadioButton) findViewById(R.id.radioButtonDps)).isChecked())
            return  "dps";
        else if(((RadioButton) findViewById(R.id.radioButtonTank)).isChecked())
            return  "tank";
        return "";
    }

    private void resetView() {
        ((EditText) findViewById(R.id.editAliasUserData)).setText("");
        ((Spinner) findViewById(R.id.editRegionUserData)).setSelection(0);
        ((RadioButton) findViewById(R.id.radioButtonSupport)).setChecked(false);
        ((RadioButton) findViewById(R.id.radioButtonDps)).setChecked(false);
        ((RadioButton) findViewById(R.id.radioButtonTank)).setChecked(false);
    }
}