package com.hahsm.user.model;

public class User {
    private int id;
    private String name;
    private String address;

    public User() {}

    public User(int id, String name, String address) {
        setID(id);
        setName(name);
        setAddress(address);
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
