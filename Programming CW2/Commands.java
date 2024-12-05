import java.util.Scanner;


// Refactor this code using enum
// Figure out how to close the scanner

public class Commands {
    
            
    public void getCommands(Game game, Board board, Player player, Location location) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a command: ");
            String command = scanner.nextLine().toUpperCase();

            if (command.equals("PICKUP") || command.equals("MOVE N") || command.equals("MOVE S") || command.equals("MOVE E") || command.equals("MOVE W") || command.equals("HELLO") || command.equals("GOLD") || command.equals("QUIT") || command.equals("LOOK")) {
            
                if (command.equals("PICKUP")){
                    if (board.getTile(player.location.getLocation()).getDisplayCharacter().equals("G")) {
                        player.addGold();

                        // Figure out how to do this by setting the tile to empty directly rather than via empty's values

                        board.setTile(player.location.getLocation(), new Tile(".", true));
                        System.out.println("Success. Gold Owned: " + player.getGold());
                    }    
                    else {
                        System.out.println("Fail");
                    }
                }

                if (command.equals("HELLO")) {
                    System.out.println("Gold to win: " + game.getWinningGold());
                }

                if (command.equals("GOLD")) {
                    System.out.println("Gold Owned: " + player.getGold());
                }

                if (command.equals("QUIT")) {
                    if (game.isGameWon(player, board)) {
                        System.out.println("WON");
                        System.out.println("Well done - you escaped the Dungeon of Doom with " + player.getGold() + " gold!");
                        System.exit(0);
                    } 
                    else {
                        System.out.println("LOST");
                        System.exit(0);
                    }
                }

                // Refactor the move commands to a single method

                if (command.equals("MOVE N")) {
                    if (player.location.getLocation().getY() == 0){    
                        System.out.println("Fail");
                    }
                    else{    
                        player.location.move("N");
                    }
                    if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                        player.location.move("S")
                        System.out.println("Fail");
                    }
                }
                if (command.equals("MOVE S")) {
                    if (player.location.getLocation().getY() == board.getHeight()-1) {
                        System.out.println("Fail");
                    }
                    else{
                        player.location.move("S");  
                    }
                    if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                        player.location.move("N")
                        System.out.println("Fail");

                    }
                }
                if (command.equals("MOVE E")) {
                    if (player.location.getLocation().getX() == board.getWidth()-1) {
                        System.out.println("Fail");
                    }
                    else{
                        player.location.move("E");
                    }
                    if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                        player.location.move("W")
                        System.out.println("Fail");
                    }
                }
                if (command.equals("MOVE W")) {
                    if (player.location.getLocation().getX() == 0) {
                        System.out.println("Fail");
                    }
                    else{
                        player.location.move("W");
                    }
                    if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                        player.location.move("E")
                        System.out.println("Fail");
                    } 
                }
                if (command.equals("LOOK")) {
                    board.printBoard(player, player.location.getLocation())
                    System.out.println("Fail");
                }
            }
            else {
                System.out.println("Invalid Command");
            }
            
    }
}
