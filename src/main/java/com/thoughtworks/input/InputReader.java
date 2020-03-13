package com.thoughtworks.input;

import com.thoughtworks.exception.BattleException;
import com.thoughtworks.model.Coordinate;
import com.thoughtworks.model.Ship;
import com.thoughtworks.model.ShipType;

import java.util.*;

public class InputReader {

    private Scanner sc;

    public InputReader(Scanner scanner){
        this.sc = scanner;
    }

    public int readBattleAreaWidth() throws BattleException{
        int areaWidth = sc.nextInt();
        Constraints.checkValidBattleAreaWidth(areaWidth);
        return areaWidth;
    }

    public int readBattleAreaHeight() throws BattleException{
        String areaHeightStr = sc.next();
        char ch = areaHeightStr.toUpperCase().charAt(0);
        int areaHeight = ch - 64;
        Constraints.checkValidBattleAreaHeight(areaHeight);
        return areaHeight;
    }

    public int getBattleShipsCountForEachPlayer(int areaWidth, int areaHeight) throws BattleException{
        int shipCount = sc.nextInt();
        Constraints.checkShipCount(shipCount, areaWidth, areaHeight);
        return shipCount;
    }

    /**
     * Read ship related info for number of battleships and segregate ships for both the players
     * @param shipCount
     * @param battleAreaWidth
     * @param battleAreaHeight
     * @throws BattleException
     */
    public Map<String, List<Ship>> getShipInfo(int shipCount, int battleAreaWidth, int battleAreaHeight) throws BattleException{
        Map<String, List<Ship>> ships = new HashMap<>();
        List<Ship> player1Ships = new ArrayList<>();
        List<Ship> player2Ships = new ArrayList<>();

        for (int j = 0; j < shipCount; j++) {
            char shipType = sc.next().toUpperCase().charAt(0);

            ShipType shipTypeObj = ShipType.fromValue(String.valueOf(shipType));

            if(shipTypeObj == null){
                throw new BattleException("please pass valid ship type object");
            }

            //get ship width
            int shipWidth = sc.nextInt();
            Constraints.checkShipWidth(shipWidth, battleAreaWidth);

            //get ship height
            int shipHeight = sc.nextInt();
            Constraints.checkShipHeight(shipHeight, battleAreaHeight);


            for (int i = 0; i <= 1; i++) {
                char[] locationPointArr = sc.next().toUpperCase().toCharArray();

                int[] loc = new int[2];
                loc[0] = locationPointArr[0] - 65;
                loc[1] = locationPointArr[1] - 49;
                Constraints.checkShipCoordinates(loc,battleAreaWidth, battleAreaHeight);
                Ship ship = new Ship(shipTypeObj, shipWidth, shipHeight, loc);

                if (i % 2 == 0){
                    player1Ships.add(ship);
                }else {
                    player2Ships.add(ship);
                }
            }
            sc.nextLine();
        }

        ships.put("Player-1", player1Ships);
        ships.put("Player-2", player2Ships);

        return ships;
    }

    public List<Coordinate> getTargetCoordinates() {
        String coordinateInput = sc.nextLine();
        List<Coordinate> coordinates = new ArrayList<>();
        String[] arr = coordinateInput.split("\\ ");
        Coordinate coordinate;
        for (String s : arr) {
            char[] charArr = s.toCharArray();
            coordinate = new Coordinate(charArr[1] - 49, charArr[0] - 65);
            coordinates.add(coordinate);
        }
        return coordinates;
    }

    static class Constraints{
        static void checkValidBattleAreaWidth(int battleAreaWidth) throws BattleException{
            if(battleAreaWidth > 9 || battleAreaWidth < 1){
                throw new BattleException("width passed is out of valid range. Should be between 1-9");
            }
        }

        static void checkValidBattleAreaHeight(int battleAreaHeight) throws BattleException{
            if(battleAreaHeight > 25 || battleAreaHeight < 0){
                throw new BattleException("height passed is out of valid range. Should be between A-Z");
            }
        }

        static void checkShipCount(int battleShipsCount, int battleAreaWidth, int battleAreaHeight) throws BattleException{
            if(battleShipsCount > battleAreaWidth*battleAreaHeight || battleShipsCount < 1){
                throw new BattleException("ship count must be between 1 and "+ battleAreaHeight*battleAreaWidth);
            }
        }

        static void checkShipWidth(int shipWidth, int battleAreaWidth) throws BattleException{
            if(shipWidth > battleAreaWidth || shipWidth < 0){
                throw new BattleException("width of ship passed is invalid");
            }
        }

        static void checkShipHeight(int shipHeight, int battleAreaHeight) throws BattleException{
            if(shipHeight > battleAreaHeight || shipHeight < 0){
                throw new BattleException("height of ship passed is invalid");
            }
        }

        static void checkShipCoordinates(int[] loc, int battleAreaWidth, int battleAreaHeight) throws BattleException{
            if(loc[0]> battleAreaHeight || loc[0] < 0 || loc[1] > battleAreaWidth || loc[1] < 0){
                throw new BattleException("ship coordinates provided is wrong");
            }
        }


    }
}
