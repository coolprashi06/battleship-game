package com.thoughtworks.main;

import com.thoughtworks.exception.BattleException;
import com.thoughtworks.input.InputReader;
import com.thoughtworks.model.Coordinate;
import com.thoughtworks.model.Player;
import com.thoughtworks.model.Ship;
import com.thoughtworks.service.PlayerService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameLauncher {

    private Player player1;
    private Player player2;
    private boolean draw;

    private PlayerService playerService;
    private InputReader inputReader;

    public void configureAndStartGame() throws BattleException {
        System.out.println("Starting Battle ship game");

        Scanner scanner = new Scanner(System.in);

        inputReader = new InputReader(scanner);
        playerService = new PlayerService();

        configureGame();
        startGame();
        concludeResult();
    }

    /**
     * takes input from user via console to configure game
     * @throws BattleException
     */
    private void configureGame() throws BattleException{
        int battleAreaWidth = inputReader.readBattleAreaWidth();
        int battleAreaHeight = inputReader.readBattleAreaHeight();
        int shipCount = inputReader.getBattleShipsCountForEachPlayer(battleAreaWidth, battleAreaHeight);

        Map<String, List<Ship>> ships = inputReader.getShipInfo(shipCount, battleAreaWidth, battleAreaHeight);
        List<Coordinate> player1Missiles = inputReader.getTargetCoordinates();
        List<Coordinate> player2Missiles = inputReader.getTargetCoordinates();
        List<Ship> player1Ships = ships.get("Player-1");
        List<Ship> player2Ships = ships.get("Player-2");

        initializePlayer1(battleAreaWidth, battleAreaHeight, player1Ships, player1Missiles);
        initializePlayer2(battleAreaWidth, battleAreaHeight, player2Ships, player2Missiles);
    }

    private void initializePlayer1(int battleAreaWidth, int battleAreaHeight, List<Ship> player1Ships, List<Coordinate> targets) throws BattleException{
        player1 = new Player("Player-1", battleAreaWidth, battleAreaHeight, player1Ships, targets);
        playerService.arrangeShipsInBattleArea(player1);
    }

    private void initializePlayer2(int battleAreaWidth, int battleAreaHeight, List<Ship> player2Ships, List<Coordinate> targets) throws BattleException{
        player2 = new Player("Player-2", battleAreaWidth, battleAreaHeight, player2Ships, targets);
        playerService.arrangeShipsInBattleArea(player2);
    }

    private void startGame(){
        //play until one player's ships are destroyed
        while (!player1.hasLost() && !player2.hasLost()) {
            playerService.play(player1, player2);
            playerService.play(player2, player1);

            if(targetsFinishedAndStillNotLost()){
                draw = true;
                break;
            }
        }
    }

    /**
     * if targets are finished for each player and none of them has lost, it's a DRAW
     * @return
     */
    private boolean targetsFinishedAndStillNotLost(){
        return player1.getTargets().size() == 0 &&
                player2.getTargets().size() == 0 &&
                !player1.hasLost() &&
                !player2.hasLost();
    }

    private void concludeResult(){
        String result;
        if(draw){
            result = "it's a draw";
        }else {
            StringBuilder sb = new StringBuilder();
            sb.append(player1.hasLost()? player2.getPlayerName() : player1.getPlayerName());
            sb.append(" won the battle");
            result = sb.toString();
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws BattleException{
        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.configureAndStartGame();

        /*
         * tested the code for following inputs:
         *
         * tested for following inputs using System.in console:
         *
         *
         * 5 E
         * 2
         * Q 1 1 A1 B2
         * P 2 1 D4 C3
         * A1 B2 B2 B3
         * A1 B2 B3 A1 D1 E1 D4 D4 D5 D5
         *
         *
         *  this results in Player2 winning the game
         *
         *
         * 5 E
         * 2
         * Q 1 1 A1 B2
         * P 2 1 D4 C3
         * A1 B2 B2 C3 C4
         * A1 B2 B3 A1 D1 E1 D4 D4 D5 D5
         *
         *
         * this results in Player1 winning the game
         */
    }
}
