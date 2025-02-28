package com.svalero.tournaments.domain;

import java.time.LocalDate;


public class Tournament {

        public Tournament(){}

        //Construct add form
        public Tournament(
                String name,
                LocalDate initDate,
                LocalDate endDate,
                float prize,
                String address,
                String manager,
                float latitude,
                float longitude
        ){
                this.name = name;
                this.initDate = initDate;
                this.endDate = endDate;
                this.prize = prize;
                this.address = address;
                this.manager = manager;
                this.longitude = longitude;
                this.latitude = latitude;
        }

        //Construct response API
        public Tournament(
                long id,
                String name,
                LocalDate initDate,
                LocalDate endDate,
                float prize,
                String address,
                String manager,
                float latitude,
                float longitude
        ){
                this.id = id;
                this.name = name;
                this.initDate = initDate;
                this.endDate = endDate;
                this.prize = prize;
                this.address = address;
                this.manager = manager;
                this.longitude = longitude;
                this.latitude = latitude;
        }

        private long id;

        private String name;
        private LocalDate initDate;

        private LocalDate endDate;

        private float prize;

        private String address;

        private String manager;

        private float latitude;

        private float longitude;

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

        public LocalDate getInitDate() {
                return initDate;
        }

        public void setInitDate(LocalDate initDate) {
                this.initDate = initDate;
        }

        public LocalDate getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDate endDate) {
                this.endDate = endDate;
        }

        public float getPrize() {
                return prize;
        }

        public void setPrize(float prize) {
                this.prize = prize;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getManager() {
                return manager;
        }

        public void setManager(String manager) {
                this.manager = manager;
        }

        public float getLatitude() {
                return latitude;
        }

        public void setLatitude(float latitude) {
                this.latitude = latitude;
        }

        public float getLongitude() {
                return longitude;
        }

        public void setLongitude(float longitude) {
                this.longitude = longitude;
        }
}
