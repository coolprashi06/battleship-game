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
        }

        for (int i = 0; i < targets.size();) {
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
            break;
        }
    }

    /**
     * arrange the ships in battle area of player and also identify the total count for destruction.
     * TODO: observer type pattern could be used too here to observe on all 0 entries in the matrix as opposed to using the count to identify the loss.
     */
    public void arrangeShipsInBattleArea(Player player) throws BattleException {
        int[][] battleArea = new int[player.getBattleAreaWidth()][player.getBattleAreaHeight()];
        int hitsRequiredForCompleteDestruction = 0;
        for(Ship ship : player.getShips()){
            int x = ship.getLocation()[1];
            int y = ship.getLocation()[0];

            checkIfShipCanBePlaced(player, ship, x, y);

            for(int i = x; i < ship.getWidth() + x; i++){
                for(int j = y; j < ship.getHeight() + y; j++){
                    battleArea[i][j] = ship.getShipType().getNumberOfHitsForDestruction();
                    hitsRequiredForCompleteDestruction += ship.getShipType().getNumberOfHitsForDestruction();
                }
            }
        }
        player.setBattleArea(battleArea);
        player.setHitsRequiredForCompleteDestruction(hitsRequiredForCompleteDestruction);
    }

    private boolean fireMissile(Player player, Coordinate coordinate, Player opponent){
        boolean fireResult =  goFire(opponent, coordinate);
        System.out.println(player.getPlayerName() + " fires a missile with target "+ coordinate +" which got " + (fireResult? "hit" : "miss"));
        return fireResult;
    }

    private boolean goFire(Player opponent, Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (opponent.getBattleArea()[x][y] != 0) {
            opponent.getBattleArea()[x][y] = opponent.getBattleArea()[x][y] - 1;
            opponent.decrementHitsRequiredForDestruction();
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
}
