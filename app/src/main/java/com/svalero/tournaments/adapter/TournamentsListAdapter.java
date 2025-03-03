package com.svalero.tournaments.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.util.DateUtil;
import com.svalero.tournaments.view.TournamentDetailView;

import java.util.List;

public class TournamentsListAdapter extends RecyclerView.Adapter<TournamentsListAdapter.TournamentHolder>{

    private List<Tournament> tournamentsList;

    public TournamentsListAdapter(List<Tournament> tournamentList){
        this.tournamentsList = tournamentList;
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
        String date = DateUtil.formatFromString(stringDate, outFormat);
        holder.itemDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return tournamentsList.size();
    }

    public class TournamentHolder extends RecyclerView.ViewHolder {

        private TextView itemDate;
        private TextView itemAddress;
        private TextView itemName;

        public TournamentHolder(@NonNull View itemView) {
            super(itemView);

            this.itemDate = itemView.findViewById(R.id.dateTournament);
            this.itemAddress = itemView.findViewById(R.id.addressTournament);
            this.itemName = itemView.findViewById(R.id.nameTournament);

            //Add click listener in every element of recycler view
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), TournamentDetailView.class);
                intent.putExtra("id", tournamentsList.get(getAdapterPosition()).getId());
                intent.putExtra("name", tournamentsList.get(getAdapterPosition()).getName());
                intent.putExtra("address", tournamentsList.get(getAdapterPosition()).getAddress());
                intent.putExtra("initDate", tournamentsList.get(getAdapterPosition()).getInitDate());
                intent.putExtra("endDate", tournamentsList.get(getAdapterPosition()).getEndDate());
                intent.putExtra("manager", tournamentsList.get(getAdapterPosition()).getManager());
                intent.putExtra("prize", tournamentsList.get(getAdapterPosition()).getPrize());
                intent.putExtra("longitude", tournamentsList.get(getAdapterPosition()).getLongitude());
                intent.putExtra("latitude", tournamentsList.get(getAdapterPosition()).getLatitude());
                startActivity(itemView.getContext(), intent, null);
            });
        }
    }
}
