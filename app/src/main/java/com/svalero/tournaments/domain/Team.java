package com.svalero.tournaments.domain;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Team implements Parcelable {

    private long id;

    private String name;

    private String representative;

    private String phone;

    private boolean partner;

    private String registrationDate;

    private String address;

    private int region;

    public Team(){}

    //Constructor insert
    public Team(
            String name,
            String representative,
            String phone,
            boolean partner,
            String address,
            int region
    ){
        this.name = name;
        this.representative = representative;
        this.phone = phone;
        this.partner = partner;
        this.address = address;
        this.region = region;
    }

    //Constructor complete
    public Team(
            long id,
            String name,
            String representative,
            String phone,
            boolean partner,
            String registrationDate,
            String address,
            int region
    ){
        this.id = id;
        this.name = name;
        this.representative = representative;
        this.phone = phone;
        this.partner = partner;
        this.registrationDate = registrationDate;
        this.address = address;
        this.region = region;
    }

    protected Team(Parcel in) {
        id = in.readLong();
        name = in.readString();
        representative = in.readString();
        phone = in.readString();
        partner = in.readByte() != 0;
        registrationDate = in.readString();
        address = in.readString();
        region = in.readInt();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPartner() {
        return partner;
    }

    public void setPartner(boolean partner) {
        this.partner = partner;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(representative);
        dest.writeString(phone);
        dest.writeByte((byte) (partner ? 1 : 0));
        dest.writeString(registrationDate);
        dest.writeString(address);
        dest.writeInt(region);
    }
}
