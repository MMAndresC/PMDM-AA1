package com.svalero.tournaments.util;

import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.domain.TournamentWinners;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {

    public static TournamentWinners findTournamentWinner(List<TournamentWinners> tournamentWinners){
        for(TournamentWinners winner : tournamentWinners){
            if(winner.getType().equalsIgnoreCase("final")){
                return winner;
            }
        }
        return new TournamentWinners();
    }

    public static List<Tournament> findTournamentsName(List<Tournament> tournaments, String query){
        query = query.toLowerCase();
        List<Tournament> filteredTournaments = new ArrayList<>();
        for(Tournament tournament : tournaments){
            if(tournament.getName().toLowerCase().contains(query))
                filteredTournaments.add(tournament);
        }
        return filteredTournaments;
    }
}
