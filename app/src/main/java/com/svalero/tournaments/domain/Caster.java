package com.svalero.tournaments.domain;

import java.time.LocalDate;

public class Caster {

    public Caster(){}

    public Caster(
      long id,
      String name,
      String alias,
      String phone,
      int region,
      String languages,
      LocalDate hireDate
    ){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.phone = phone;
        this.region = region;
        this.languages = languages;
        this.hireDate = hireDate;
    }

    private long id;

    private String name;

    private String alias;

    private String phone;

    private int region;

    private String languages;

    private LocalDate hireDate;
}
