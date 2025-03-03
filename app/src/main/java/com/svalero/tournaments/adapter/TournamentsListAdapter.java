package com.svalero.tournaments.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.util.DateUtil;

import java.util.List;

public class TournamentsListAdapter extends RecyclerView.Adapter<TournamentsListAdapter.TournamentHolder>{

    private List<Tournament> tournamentList;

    public TournamentsListAdapter(List<Tournament> tournamentList){
        this.tournamentList = tournamentList;
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
        holder.itemName.setText(tournamentList.get(position).getName());
        holder.itemAddress.setText(tournamentList.get(position).getAddress());
        String stringDate = tournamentList.get(position).getInitDate();
        String outFormat = "MMM dd, yyyy";
        String date = DateUtil.formatFromString(stringDate, outFormat);
        holder.itemDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
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
        }
    }
}
