import java.util.Scanner;

public class Game {
    private static final int WINNINGGOLD = 10;
    private Location botLocation = new Location(5, 5);
    private Location exitLocation = new Location(0, 0);
    private Location goldLocation = new Location(5, 5);

    /* Main method - adds players and runs game */
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public void run() {
        Player player1 = new Player();

        while (true) {
           
            /* Get Commands from player */
            /* Refactor this using an enum */
            /* Move this all into a seperate class */

            String command = getInput();

            if (command.equals("PICKUP") || command.equals("MOVE N") || command.equals("MOVE S") || command.equals("MOVE E") || command.equals("MOVE W") || command.equals("HELLO") || command.equals("GOLD") || command.equals("QUIT") || command.equals("LOOK")) {
            
                if (command.equals("PICKUP")){
                    if (player1.getLocation().equals(goldLocation)) {
                        player1.addGold();
                        System.out.println("Success. Gold Owned: " + player1.getGold());
                    }    
                    else {
                        System.out.println("Fail");
                    }
                }
                
                if (command.equals("HELLO")) {
                    System.out.println("Gold to win: " + getWinningGold());
                }

                if (command.equals("GOLD")) {
                    System.out.println("Gold Owned: " + player1.getGold());
                }

                if (command.equals("QUIT")) {
                    if (isGameWon(player1)) {
                        System.out.println("WON");
                        System.out.println("Well done - you escaped the Dungeon of Doom with " + player1.getGold() + " gold!");
                        System.exit(0);
                    } 
                    else {
                        System.out.println("LOST");
                        System.exit(0);
                    }
                }
                if (command.equals("MOVE N")) {
                    player1.moveNorth();
                    }
                if (command.equals("MOVE S")) {
                    player1.moveSouth();
                    }
                if (command.equals("MOVE E")) {
                    player1.moveEast(); 
                    }
                if (command.equals("MOVE W")) {
                    player1.moveWest();     

                }
                if (command.equals("LOOK")) {
                    player1.printLocation();   
                }
            }
                else {
                System.out.println("Invalid Command");
                }
            
            if (isGameLost(player1)) {
                System.out.println("LOSE");
                System.exit(0);
                }

        }
    }

/* Accesssors */

    private int getWinningGold() {
        return WINNINGGOLD;
    }

/* Get input from the player */

    private String getInput() {

        /* Add lots of validation into here so that it is only capable of returning valid values */

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a command: ");
        String command = scanner.nextLine().toUpperCase();
        return command;
    }

/* Condition for winning the game */

private boolean isGameWon(Player player) {
        return player.getGold() >= WINNINGGOLD && player.getLocation().equals(exitLocation);
    }

/* Condition for losing the game */

    private boolean isGameLost(Player player) {
        return botLocation.equals(player.getLocation());
    }
}

