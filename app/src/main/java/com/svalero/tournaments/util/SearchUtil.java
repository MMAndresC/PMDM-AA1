package com.svalero.tournaments.util;

import com.svalero.tournaments.domain.TournamentWinners;

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
}
