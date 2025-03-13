package com.svalero.tournaments.view.user;

import static com.svalero.tournaments.constants.Constants.DATABASE_NAME;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.svalero.tournaments.R;
import com.svalero.tournaments.dao.Database;
import com.svalero.tournaments.domain.UserData;
import com.svalero.tournaments.util.SharedPreferencesUtil;

public class ZoneUserView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_view);

        String username = SharedPreferencesUtil.getCustomSharedPreferences(this, "username");
        if(username != null){
            //DB local
            final Database db = Room.databaseBuilder(this, Database.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();

            try {
                UserData userData = db.userDataDao().getUserData(username);
                int a = 1;
            } catch (SQLiteConstraintException sce) {
                String message = getString(R.string.db_error);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    //TODO search if exist username data
    //TODO show data in UI
    //TODO implement save, edit & delete function in DB

    //Add menu action_bar_user_data in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_user_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return true;
    }
}