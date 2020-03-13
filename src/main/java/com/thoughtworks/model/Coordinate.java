package com.thoughtworks.model;

import java.util.Objects;

public class Coordinate implements Comparable<Coordinate> {

    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Coordinate o) {
        if(this.x == o.x && this.y == o.y){
            return 0;
        }else if(this.x < o.x && this.y < o.y){
            return -1;
        }
        return 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return ((char)(y+65) + ""+ (x+1));
    }
}
