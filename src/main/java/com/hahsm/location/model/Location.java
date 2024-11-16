package com.hahsm.location.model;

public class Location {
    private final int id;
    private final String name;

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Location{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
