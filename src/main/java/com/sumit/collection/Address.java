package com.sumit.collection;

public class Address {

    private String apartmentNo;
    private String building;
    private String streetName;
    private String city;
    private String province;
    private String country;

    public Address() {
    }

    public Address(String apartmentNo, String building, String streetName, String city, String province, String country) {
        this.apartmentNo = apartmentNo;
        this.building = building;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}