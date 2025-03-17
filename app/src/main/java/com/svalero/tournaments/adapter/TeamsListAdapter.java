package com.svalero.tournaments.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import static com.svalero.tournaments.constants.Constants.DATABASE_NAME;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.tournaments.R;
import com.svalero.tournaments.dao.AppDatabase;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.domain.UserFavourite;
import com.svalero.tournaments.util.ParseUtil;
import com.svalero.tournaments.view.team.DetailTeamView;
import com.svalero.tournaments.view.team.ListTeamsView;

import java.util.List;

public class TeamsListAdapter extends RecyclerView.Adapter<TeamsListAdapter.TeamHolder> {

    private List<Team> teamsList;
    private ListTeamsView context;
    private List<UserFavourite> userFavourites;
    private String username;

    public TeamsListAdapter(List<Team> teamsList, ListTeamsView context, List<UserFavourite> userFavourites, String username){
        this.teamsList = teamsList;
        this.context = context;
        this.userFavourites = userFavourites;
        this.username = username;
    }

    @NonNull
    @Override
    public TeamsListAdapter.TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_team_item, parent, false);
        return new TeamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsListAdapter.TeamHolder holder, int position) {
        long id = teamsList.get(position).getId();
        holder.itemId.setText(String.valueOf(id));
        holder.itemName.setText(teamsList.get(position).getName());
        setImage(holder.itemLogo, teamsList.get(position).getName());
        String region = translateRegionToString(teamsList.get(position).getRegion());
        holder.itemRegion.setText(region);
        String partner = teamsList.get(position).isPartner() ? "Partner" : "";
        holder.itemPartner.setText(partner);

        boolean isFavourite = userFavourites.stream().anyMatch(fav -> fav.getTeamId() == id);
        holder.itemFavourite.setChecked(isFavourite);
        holder.itemFavourite.setEnabled(username != null);

        // Event click on checkbox
        holder.itemFavourite.setOnClickListener(v -> {
            if (username == null) {
                Toast.makeText(context,
                        context.getString(R.string.required_logged_mark_favorite),
                        Toast.LENGTH_LONG).show();
                //Uncheck
                holder.itemFavourite.setChecked(!holder.itemFavourite.isChecked());
                return;
            }
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            try {
                if (holder.itemFavourite.isChecked()) {
                    db.userFavouriteDao().insert(new UserFavourite(username, id));
                    userFavourites.add(new UserFavourite(username, id));
                } else {
                    db.userFavouriteDao().deleteFavouriteTeam(username, id);
                    userFavourites.removeIf(fav -> fav.getTeamId() == id);
                }
            } catch (SQLiteConstraintException sce) {
                String message = context.getString(R.string.db_error);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

        // Context menu listener
        holder.itemView.setOnLongClickListener(v -> {
            context.setSelectedTeam(teamsList.get(position));
            v.showContextMenu();
            return true;
        });
    }

    private void setImage(ImageView imageView, String imageName) {
        imageName = ParseUtil.parseImageName(imageName);
        int resourceId = imageView.getContext()
                .getResources()
                .getIdentifier(imageName, "drawable", imageView.getContext().getPackageName());

        if (resourceId != 0)
            imageView.setImageResource(resourceId);
        else
            imageView.setImageResource(R.drawable.no_photos);
    }

    public String translateRegionToString(int region){
        switch(region){
            case 1: return context.getString(R.string.region_NA);
            case 2: return context.getString(R.string.region_SA);
            case 3: return context.getString(R.string.region_EU);
            case 4: return context.getString(R.string.region_AS);
            case 5: return context.getString(R.string.region_OC);
        }
        return "N/D";
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    public class TeamHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView itemName;
        private TextView itemId;
        private TextView itemRegion;
        private TextView itemPartner;
        private ImageView itemLogo;
        private CheckBox itemFavourite;
        public TeamHolder(@NonNull View itemView) {
            super(itemView);

            this.itemId = itemView.findViewById(R.id.idTeamItem);
            this.itemName = itemView.findViewById(R.id.nameTeamItem);
            this.itemPartner = itemView.findViewById(R.id.partnerTeamItem);
            this.itemRegion = itemView.findViewById(R.id.regionTeamItem);
            this.itemLogo = itemView.findViewById(R.id.logoTeamItem);
            this.itemFavourite = itemView.findViewById(R.id.favouriteTeamCheckbox);

            //listener event click to go to detail view
            itemView.setOnClickListener(view -> {
                Team team = teamsList.get(getAdapterPosition());
                Intent intent = new Intent(itemView.getContext(), DetailTeamView.class);
                intent.putExtra("team", team);
                startActivity(itemView.getContext(), intent, null);
            });

            // Register context menu
            itemView.setOnCreateContextMenuListener(this);
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.editMenuTeam, Menu.NONE, "Edit");
            menu.add(Menu.NONE, R.id.removeMenuTeam, Menu.NONE, "Remove");
        }
    }
}
