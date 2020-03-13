package com.thoughtworks.model;
import java.util.List;

public class Player {

    private String playerName;
    private int battleAreaWidth;
    private int battleAreaHeight;
    private List<Ship> ships;
    private int[][] battleArea;
    private List<Coordinate> targets;

    public Player(String playerName, int battleAreaWidth, int battleAreaHeight, List<Ship> ships,List<Coordinate> targets) {
        this.playerName = playerName;
        this.battleAreaWidth = battleAreaWidth;
        this.battleAreaHeight = battleAreaHeight;
        this.ships = ships;
        this.battleArea = new int[battleAreaWidth][battleAreaHeight];
        this.targets = targets;
    }

    public List<Coordinate> getTargets() {
        return targets;
    }

    public int[][] getBattleArea() {
        return battleArea;
    }

    public void setBattleArea(int[][] battleArea) {
        this.battleArea = battleArea;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public int getBattleAreaWidth() {
        return battleAreaWidth;
    }

    public int getBattleAreaHeight() {
        return battleAreaHeight;
    }
}
