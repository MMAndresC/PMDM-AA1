package com.svalero.tournaments.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.util.DateUtil;
import com.svalero.tournaments.view.TournamentDetailView;
import com.svalero.tournaments.view.TournamentsListView;

import java.util.List;

public class TournamentsListAdapter extends RecyclerView.Adapter<TournamentsListAdapter.TournamentHolder>{

    private List<Tournament> tournamentsList;
    private TournamentsListView context;

    public TournamentsListAdapter(List<Tournament> tournamentList, TournamentsListView context){
        this.tournamentsList = tournamentList;
        this.context = context;
    }

    @NonNull
    @Override
    public TournamentsListAdapter.TournamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_tournament_item, parent, false);
        return new TournamentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentsListAdapter.TournamentHolder holder, int position) {
        holder.itemName.setText(tournamentsList.get(position).getName());
        holder.itemAddress.setText(tournamentsList.get(position).getAddress());
        String stringDate = tournamentsList.get(position).getInitDate();
        String outFormat = "MMM dd, yyyy";
        String inPattern = "yyyy-MM-dd";
        String date = DateUtil.formatFromString(stringDate, outFormat, inPattern);
        holder.itemDate.setText(date);
        holder.itemId.setText(String.valueOf(tournamentsList.get(position).getId()));
        // Context menu listener
        holder.itemView.setOnLongClickListener(v -> {
            context.setSelectedTournament(tournamentsList.get(position));
            v.showContextMenu();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tournamentsList.size();
    }

    public class TournamentHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView itemDate;
        private TextView itemAddress;
        private TextView itemName;
        private TextView itemId;
        public TournamentHolder(@NonNull View itemView) {
            super(itemView);

            this.itemDate = itemView.findViewById(R.id.dateTournament);
            this.itemAddress = itemView.findViewById(R.id.addressTournament);
            this.itemName = itemView.findViewById(R.id.nameTournament);
            this.itemId = itemView.findViewById(R.id.idTournament);

            //Add click listener in every element of recycler view
            itemView.setOnClickListener(view -> {
                Tournament tournament = tournamentsList.get(getAdapterPosition());
                Intent intent = new Intent(itemView.getContext(), TournamentDetailView.class);
                intent.putExtra("tournament", tournament);
                startActivity(itemView.getContext(), intent, null);
            });

            // Register context menu
            itemView.setOnCreateContextMenuListener(this);
        }

        //Create context menu
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.editMenuTournament, Menu.NONE, "Edit");
            menu.add(Menu.NONE, R.id.removeMenuTournament, Menu.NONE, "Remove");
        }
    }
}
