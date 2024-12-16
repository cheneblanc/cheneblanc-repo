import java.util.Scanner;

// Figure out how to close the scanner

public class Commands {
    
    // Refactor Commmands so it has a constructor and we're passing fewer parameters to the getCommands method
    
    private Game game;
    private Scanner scanner;

    public Commands(Game game, Scanner scanner) {
        this.game = game;   
        this.scanner = scanner;
    }
            
    public void getCommands(Board board, Player player, Location location) {
        
        System.out.println("Enter a command: ");

        String command = scanner.nextLine().toUpperCase();

        switch (command) {
            case "PICKUP":
                Boolean pickup = player.pickUp();    
                if (pickup) {
                    System.out.println("Success. Gold Owned: " + player.getGold());
                }    
                else {
                    System.out.println("Fail");
                }
                break;
            case "HELLO":    
                System.out.println("Gold to win: " + game.getWinningGold());
                break;
            case "GOLD":
                System.out.println("Gold Owned: " + player.getGold());
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

                // MOVE commands
                // Check if the player is at the edge of the board and trying to move off it
                // Move the player if they're not trying to move off it
                // Check if that move results in the player being on a wall
                // If so, move them back and print "Fail"
                // If not, print "Success"

            case "MOVE N":
                Boolean moveN = player.moveNorth();
                if (moveN) {
                    System.out.println("Success");
                } 
                else {
                    System.out.println("Fail");
                }
                break;
            
            case "MOVE S":
                Boolean moveS = player.moveSouth();
                if (moveS) {
                    System.out.println("Success");
                } else {
                    System.out.println("Fail");
                }
                break;
            
            case "MOVE E":
                Boolean moveE = player.moveEast();
                if (moveE) {
                    System.out.println("Success");
                } else {
                    System.out.println("Fail");
                }
                break;
            
            case "MOVE W":
                Boolean moveW = player.moveWest();
                if (moveW) {
                    System.out.println("Success");
                } else {
                    System.out.println("Fail");
                }
                break;
            
            case "LOOK":
                board.printBoard(player, player.location.getLocation(),2); // prints the board with a radius around the player given by the final value
                break;
            
            default:
                System.out.println("Invalid Command");
                break;
        }
    }
}
