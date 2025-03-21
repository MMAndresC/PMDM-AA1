package com.svalero.tournaments.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.SpinnerOption;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.domain.User;

import java.text.ParseException;

public class ValidateUtil {

    public static Tournament validateTournamentForm(View view) throws NullPointerException, NumberFormatException {
        try {
            String name = ((EditText) view.findViewById(R.id.editTournamentName)).getText().toString();
            String manager = ((EditText) view.findViewById(R.id.editTournamentManager)).getText().toString();
            String city = ((EditText) view.findViewById(R.id.editTournamentCity)).getText().toString();
            String country = ((EditText) view.findViewById(R.id.editTournamentCountry)).getText().toString();
            String initDate = ((EditText) view.findViewById(R.id.editTournamentStart)).getText().toString();
            String endDate = ((EditText) view.findViewById(R.id.editTournamentEnd)).getText().toString();
            float prize = Float.parseFloat(((EditText) view.findViewById(R.id.editTournamentPrize)).getText().toString());
            double longitude = Double.parseDouble(((TextView) view.findViewById(R.id.editTournamentLong)).getText().toString());
            double latitude = Double.parseDouble(((TextView) view.findViewById(R.id.editTournamentLat)).getText().toString());

            if (name.isBlank() || manager.isBlank() || city.isBlank() || country.isBlank() || initDate.isBlank() || endDate.isBlank())
                return new Tournament();
            initDate = initDate.replace("/", "-");
            initDate = DateUtil.formatFromString(initDate, "yyyy-MM-dd", "dd-MM-yyyy");
            endDate = endDate.replace("/", "-");
            endDate = DateUtil.formatFromString(endDate, "yyyy-MM-dd", "dd-MM-yyyy");

            if(initDate.isBlank() || endDate.isBlank()) return new Tournament();

            if(!DateUtil.checkDates(initDate, endDate)) return new Tournament();

            String address = city + ", " + country;
            return new Tournament(name, initDate, endDate, prize, address, manager, latitude, longitude);
        } catch (NumberFormatException | NullPointerException | ParseException e) {
            return new Tournament();
        }
    }

    public static User validateUserData(String action, View view){
        if(action != null){
            String username = ((EditText) view.findViewById(R.id.username)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.password)).getText().toString();
            if(!username.isBlank() && !password.isBlank())
                return new User(username, password);
        }
        return new User();
    }

    public static Team validateTeamForm(View view) throws NullPointerException, NumberFormatException {
        try {
            String name = ((EditText) view.findViewById(R.id.editTeamName)).getText().toString();
            String representative = ((EditText) view.findViewById(R.id.editTeamRepresentative)).getText().toString();
            String phone = ((EditText) view.findViewById(R.id.editTeamPhone)).getText().toString();
            String address = ((EditText) view.findViewById(R.id.editTeamAddress)).getText().toString();
            CheckBox checkBox = view.findViewById(R.id.editTeamPartner);
            boolean isPartner = checkBox.isChecked();
            Spinner spinner = view.findViewById(R.id.editTeamRegion);
            SpinnerOption regionSelected = (SpinnerOption) spinner.getSelectedItem();
            int region = regionSelected.getId();

            if (name.isBlank() || representative.isBlank() || phone.isBlank() || address.isBlank())
                return new Team();

            //Only numbers and +,  +34 689754215
            if(!phone.matches("[0-9+ ]+") || phone.length() > 15)
                return new Team();

            if(region < 1 || region > 5)
                return new Team();

            return new Team(name, representative, phone, isPartner, address, region);
        } catch (NumberFormatException | NullPointerException e) {
            return new Team();
        }
    }
}
