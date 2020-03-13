package com.thoughtworks.model;

public class Ship {

    private ShipType shipType;
    private int width;
    private int height;
    private int[] location;

    public Ship(ShipType shipType, int width, int height, int[] location){
        this.shipType = shipType;
        this.width = width;
        this.height = height;
        this.location = location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public int[] getLocation() {
        return location;
    }


}
