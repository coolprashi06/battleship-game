package com.thoughtworks.service;

import com.thoughtworks.exception.BattleException;
import com.thoughtworks.model.Coordinate;
import com.thoughtworks.model.Player;
import com.thoughtworks.model.Ship;

import java.util.List;

public class PlayerService {

    /**
     * invoke targets on opponent until we miss the opponent in which case the opponent gets the chance to hit you
     */
    public void play(Player player, Player opponent){
        List<Coordinate> targets = player.getTargets();
        if(targets.size() == 0){
            System.out.println(player.getPlayerName() + " has no more missiles left to launch");
            return;
        }

        /**
         * gets first target coordinate and fire it
         * in case of hit, remove the first element from the list and subsequently element at index1 shifts to index 0
         * in case of hit, if all targets has been exhausted then break from the loop as we cannot fire targets anymore
         * in case of miss, remove the first element as it's been already fired.
         */
        int i = 0;
        Coordinate target = targets.get(i);
        while (fireMissile(player, target, opponent)) {
            targets.remove(i);
            if (i < targets.size()) {
                target = targets.get(i);
            } else{
                break;
            }
        }
        if (targets.size() > 0) {
            targets.remove(i);
        }
    }

    /** arrange the ships in battle area of player*/
    public void arrangeShipsInBattleArea(Player player) throws BattleException {
        int[][] battleArea = new int[player.getBattleAreaWidth()][player.getBattleAreaHeight()];
        for(Ship ship : player.getShips()){
            int x = ship.getLocation()[1];
            int y = ship.getLocation()[0];

            checkIfShipCanBePlaced(player, ship, x, y);

            for(int i = x; i < ship.getWidth() + x; i++){
                for(int j = y; j < ship.getHeight() + y; j++){
                    battleArea[i][j] = ship.getShipType().getNumberOfHitsForDestruction();
                }
            }
        }
        player.setBattleArea(battleArea);
    }

    private boolean fireMissile(Player player, Coordinate coordinate, Player opponent){
        if(checkIfAllShipsDestroyed(opponent.getBattleArea())){
            return false;
        }

        boolean fireResult =  goFire(opponent, coordinate);
        System.out.println(player.getPlayerName() + " fires a missile with target "+ coordinate +" which got " + (fireResult? "hit" : "miss"));
        return fireResult;
    }

    private boolean goFire(Player opponent, Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (opponent.getBattleArea()[x][y] != 0) {
            opponent.getBattleArea()[x][y] = opponent.getBattleArea()[x][y] - 1;
            return true;
        }else {
            return false;
        }
    }

    private void checkIfShipCanBePlaced(Player player, Ship ship, int x , int y) throws BattleException{
        if(ship.getWidth() + x > player.getBattleAreaWidth() || ship.getHeight() + y >player.getBattleAreaHeight()){
            System.err.println("Coordinate "+ x + "," + y + " for "+ player.getPlayerName() + " is not available.");
            throw new BattleException("Ship cannot be placed in this location");
        }
    }

    /** if targets are finished for each player and none of them has lost, it's a DRAW */
    public boolean targetsFinishedAndStillNotLost(Player player1, Player player2){
        return player1.getTargets().size() == 0 &&
                player2.getTargets().size() == 0 &&
                !hasLost(player1) &&
                !hasLost(player2);
    }

    public boolean hasLost(Player player){
        return checkIfAllShipsDestroyed(player.getBattleArea());
    }

    private boolean checkIfAllShipsDestroyed(int[][] battleArea){
        for(int i = 0 ; i< battleArea.length; i++){
            for(int j = 0; j < battleArea[i].length; j++){
                if(battleArea[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }
}
