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
import com.svalero.tournaments.view.tournament.DetailTournamentView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private List<Tournament> tournamentsList;
    public MainAdapter(List<Tournament> tournamentsList) {
        this.tournamentsList = tournamentsList;
    }

    @NonNull
    @Override
    // Create empty component to insert in recyclerView
    public MainAdapter.MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_next_tournament_item, parent, false);
        return new MainHolder(view);
    }

    @Override
    //Fill component with data
    public void onBindViewHolder(@NonNull MainAdapter.MainHolder holder, int position) {
        holder.itemName.setText(tournamentsList.get(position).getName());
        String outFormat = "dd-MM-yyyy";
        String inPattern = "yyyy-MM-dd";
        String initDate = DateUtil.formatFromString(tournamentsList.get(position).getInitDate(), outFormat, inPattern);
        String endDate = DateUtil.formatFromString(tournamentsList.get(position).getEndDate(), outFormat, inPattern);
        String date = initDate + " - " + endDate;
        holder.itemDate.setText(date);
        holder.itemAddress.setText(tournamentsList.get(position).getAddress());
        String prize = holder.itemView.getContext().getString(R.string.prize_header) + " " +  tournamentsList.get(position).getPrize() + " $";
        holder.itemPrize.setText(prize);
    }

    @Override
    public int getItemCount() {
        return tournamentsList.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemDate;
        private TextView itemPrize;
        private TextView itemAddress;
        public MainHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tournament_name);
            itemDate = itemView.findViewById(R.id.tournament_date);
            itemAddress = itemView.findViewById(R.id.tournament_address);
            itemPrize = itemView.findViewById(R.id.tournament_prize);

            //Add click listener in every element of recycler view
            itemView.setOnClickListener(view -> {
                Tournament tournament = tournamentsList.get(getAdapterPosition());
                Intent intent = new Intent(itemView.getContext(), DetailTournamentView.class);
                intent.putExtra("tournament", tournament);
                startActivity(itemView.getContext(), intent, null);
            });
        }
    }
}
