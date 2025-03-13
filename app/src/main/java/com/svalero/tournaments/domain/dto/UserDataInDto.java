package com.svalero.tournaments.domain.dto;



public class UserDataInDto {
    private String username;
    private String alias;
    private byte[] image;
    private int region;
    private String mainRole;

    public UserDataInDto(){}

    public UserDataInDto(String username, String alias, byte[] image, int region, String mainRole){
        this.username = username;
        this.alias = alias;
        this.image = image;
        this.region = region;
        this.mainRole = mainRole;
    }

    public String getUsername() {
        return username;
    }

    public String getAlias() {
        return alias;
    }

    public byte[] getImage() {
        return image;
    }

    public int getRegion() {
        return region;
    }

    public String getMainRole() {
        return mainRole;
    }
}
