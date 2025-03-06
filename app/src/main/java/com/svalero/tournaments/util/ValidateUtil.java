package com.svalero.tournaments.util;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;

public class ValidateUtil {

    public static Tournament validateTournamentForm(View view) throws NullPointerException, NumberFormatException{
        try {
          /*  EditText nameContainer = view.findViewById(R.id.editTournamentName);
            if(nameContainer != null && nameContainer.getText() != null){
                String name = nameContainer.getText().toString().trim();
                if(name.isEmpty()){
                    int a=0;
                }
            }*/
            String name = ((EditText) view.findViewById(R.id.editTournamentName)).getText().toString();
            String manager = ((EditText) view.findViewById(R.id.editTournamentManager)).getText().toString();
            String city = ((EditText) view.findViewById(R.id.editTournamentCity)).getText().toString();
            String country = ((EditText) view.findViewById(R.id.editTournamentCountry)).getText().toString();
            String initDate = ((EditText) view.findViewById(R.id.editTournamentStart)).getText().toString().replace("/", "-");
            String endDate = ((EditText) view.findViewById(R.id.editTournamentEnd)).getText().toString().replace("/", "-");
            float prize = Float.parseFloat(((EditText) view.findViewById(R.id.editTournamentPrize)).getText().toString());
            double longitude = Double.parseDouble(((TextView) view.findViewById(R.id.editTournamentLong)).getText().toString());
            double latitude = Double.parseDouble(((TextView) view.findViewById(R.id.editTournamentLat)).getText().toString());

            if (name.isBlank() || manager.isBlank() || city.isBlank() || country.isBlank()) return new Tournament();
            initDate = DateUtil.formatFromString(initDate, "yyyy-MM-dd", "dd-MM-yyyy");
            endDate = DateUtil.formatFromString(endDate, "yyyy-MM-dd", "dd-MM-yyyy");
            if(initDate.isBlank() || endDate.isBlank()) return new Tournament();

            String address = city + ", " + country;
            return new Tournament(name, initDate, endDate, prize, address, manager, latitude, longitude);

        } catch (NumberFormatException | NullPointerException e) {
            return new Tournament();
        }
    }
}
