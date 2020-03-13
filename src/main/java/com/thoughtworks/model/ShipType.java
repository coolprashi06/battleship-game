package com.thoughtworks.model;

public enum ShipType {

    P(1),
    Q(2);

    int numberOfHitsForDestruction;

    ShipType(int numberOfHitsForDestruction){
        this.numberOfHitsForDestruction = numberOfHitsForDestruction;
    }

    public int getNumberOfHitsForDestruction() {
        return numberOfHitsForDestruction;
    }

    public static ShipType fromValue(String value){
        for(ShipType shipType : ShipType.values()){
            if(shipType.name().equalsIgnoreCase(value)){
                return shipType;
            }
        }
        return null;
    }
}
