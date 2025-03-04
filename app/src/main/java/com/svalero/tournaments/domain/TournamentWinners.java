package com.svalero.tournaments.domain;

public class TournamentWinners {

    long idTournament;
    String nameTournament;
    String date;
    int day;
    String nameTeam;
    String type;

    public TournamentWinners(){}

    public TournamentWinners(long id, String nameTournament, String date, int day, String type, String nameTeam){
        this.idTournament = id;
        this.nameTournament = nameTournament;
        this.date = date;
        this.day = day;
        this.type = type;
        this.nameTeam = nameTeam;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(long idTournament) {
        this.idTournament = idTournament;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public void setNameTournament(String nameTournament) {
        this.nameTournament = nameTournament;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
