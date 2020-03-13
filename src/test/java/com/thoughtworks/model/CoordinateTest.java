package com.thoughtworks.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {

    public Coordinate coordinate;

    @Before
    public void setup(){
        coordinate = new Coordinate(1, 8);
    }

    @Test
    public void testX(){
        Assert.assertTrue(coordinate.getX() == 1);
    }

    @Test
    public void testY(){
        Assert.assertTrue(coordinate.getY() == 8);
    }

    @Test
    public void testEquals(){
        Coordinate coordinate1 = new Coordinate(1, 8);
        Assert.assertEquals(coordinate1, coordinate);
    }

    @Test
    public void testCompare(){
        Coordinate coordinate1 = new Coordinate(1, 8);
        Assert.assertEquals(coordinate1.compareTo(coordinate), 0);
    }
}
