package com.svalero.tournaments.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Match {

    public Match(){}

    // Construct response API
    public Match(
            long id,
            LocalDate date,
            LocalTime hour,
            String type,
            String mapName,
            int duration,
            int day,
            Tournament tournament,
            Caster caster
    ){
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.type = type;
        this.mapName = mapName;
        this.duration = duration;
        this.day = day;
        this.tournament = tournament;
        this.caster = caster;
    }

    private long id;

    private LocalDate date;

    private LocalTime hour;

    private String type;

    private String mapName;

    private int duration; //Minutos

    private int day; //Nº de jornada

    private Tournament tournament;

    private Caster caster;
}
