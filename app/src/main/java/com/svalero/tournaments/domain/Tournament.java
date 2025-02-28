package com.svalero.tournaments.domain;


public class Tournament {

        public Tournament(){}

        //Construct add form
        public Tournament(
                String name,
                String initDate,
                String endDate,
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
                String initDate,
                String endDate,
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
        private String initDate;

        private String endDate;

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

        public String getInitDate() {
                return initDate;
        }

        public void setInitDate(String initDate) {
                this.initDate = initDate;
        }

        public String getEndDate() {
                return endDate;
        }

        public void setEndDate(String endDate) {
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
