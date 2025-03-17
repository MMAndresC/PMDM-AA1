package com.svalero.tournaments.view.team;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.tournaments.R;
import com.svalero.tournaments.constants.Constants;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.util.ParseUtil;

public class DetailTeamView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail_view);

        Intent intent = getIntent();
        long id = loadDetailData(intent);
        //TODO listar jugadores del equipo
    }

    private long loadDetailData(Intent intent) {
        Team team = intent.getParcelableExtra("team");
        if(team == null){
            Toast.makeText(this, getString(R.string.team_null), Toast.LENGTH_LONG).show();
            Intent comeBack = new Intent(this, ListTeamsView.class);
            startActivity(comeBack);
            finish();
            return -1;
        }
        String name = team.getName();

        String imageName = ParseUtil.parseImageName(name);
        ImageView imageView = findViewById(R.id.logoDetailTeam);
        if(imageName != null) {
            int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if(resID != 0) imageView.setImageResource(resID);
            else imageView.setImageResource(R.drawable.no_photos);
        } else imageView.setImageResource(R.drawable.no_photos);

        ((TextView) findViewById(R.id.nameDetailTeam)).setText(name);

        String representative = getString(R.string.representative) + ": " + ParseUtil.extractParam(team.getRepresentative());
        ((TextView) findViewById(R.id.representativeDetailTeam)).setText(representative);

        String phone = getString(R.string.phone) + ": " + ParseUtil.extractParam(team.getPhone());
        ((TextView) findViewById(R.id.phoneDetailTeam)).setText(phone);

        String address = getString(R.string.address) + ": " + ParseUtil.extractParam(team.getAddress());
        ((TextView) findViewById(R.id.addressDetailTeam)).setText(address);

        //Translate region to base language
        int codeRegion = team.getRegion();
        int resourceId = getResources().getIdentifier(Constants.STRING_REGIONS.get(codeRegion - 1), "string", getPackageName());
        String regionValue = getString(resourceId);
        String region = "Region: " + ParseUtil.extractParam(regionValue);
        ((TextView) findViewById(R.id.regionDetailTeam)).setText(region);

        boolean isPartner = team.isPartner();
        String partner = isPartner ? getString(R.string.partner) + " ✅" : getString(R.string.partner) + " ❌";
        ((TextView) findViewById(R.id.partnerDetailTeam)).setText(partner);

        return team.getId();
    }
}