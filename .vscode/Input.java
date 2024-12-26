/**
 * Handles terminal inputs and outputs from the human player during the game
 * Validates the inputs and converts them into the game commands
 * Prints out the relevant responses to these commands
 */

import java.util.Scanner;

public class Input {
        
    private Game game;
    private Board board;
    private Player player;
    private Scanner scanner;

    public Input(Game game, Board board, Player player, Scanner scanner) {
        this.game = game;   
        this.board = board;
        this.player = player;
        this.scanner = scanner;
    }
    
    /**
     * Gets the input from the player using the scanner
     * Converts the command to UPPPERCASE
     * Keeps asking for an input until a valid command is entered
     * Executes the command using the player methods
     * Prints out the relevant response to the command
     */

     public void getInput() {
        
        String command;
        Boolean validCommand = false;

        while (!validCommand){
            System.out.println("Enter a command: ");
            command = scanner.nextLine().toUpperCase();
        
            switch (command) {
            
                case "PICKUP":
                Boolean pickup = player.pickUp();    
                if (pickup) {
                    System.out.println("Success. Gold Owned: " + player.getGold());
                }    
                else {
                    System.out.println("Fail");
                }
                validCommand = true;
                break;
            
                case "HELLO":    
                    System.out.println("Gold to win: " + board.getWinningGold());
                    validCommand = true;
                    break;
            
                case "GOLD":
                    System.out.println("Gold Owned: " + player.getGold());
                    validCommand = true;
                    break;

                case "QUIT": 
                    if (game.isGameWon(player, board)) {
                        System.out.println("WON");
                        System.out.println("Well done - you escaped the Dungeon of Doom with " + player.getGold() + " gold!");
                        System.exit(0);
                    } 
                    else {
                        System.out.println("LOST");
                        System.exit(0);
                    }
                    break;

                case "MOVE N":
                case "MOVE S":
                case "MOVE E":
                case "MOVE W":
                    char direction = command.charAt(5);
                    Boolean moveResult = player.movePlayer(direction);
                    if (moveResult) {
                        System.out.println("Success");
                    } 
                    else {
                        System.out.println("Fail");
                    }
                    validCommand = true;
                    break;
            
                case "LOOK":
                    System.out.print("Looking");
                    char [][] visibleBoard = player.look();    
                    for (int y = visibleBoard.length-1; y > 0; y--) {
                        for (int x = 0; x < visibleBoard[y].length; x++) {
                            System.out.print(visibleBoard[x][y]);
                        }
                        System.out.println();
                    }
                    validCommand = true; 
                    break;
            
                default:
                    System.out.println("Invalid Command. Please enter HELLO, LOOK, GOLD, PICKUP, MOVE N, MOVE S, MOVE E, MOVE W or QUIT");
                    break;
            }
        }
    }
}
