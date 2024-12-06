import java.util.Scanner;

// Figure out how to close the scanner

public class Commands {
    private Scanner scanner;
            
    public void getCommands(Game game, Board board, Player player, Location location, Scanner scanner) {
        
        System.out.println("Enter a command: ");

        String command = scanner.nextLine().toUpperCase();

        switch (command) {
            case "PICKUP":
                if (board.getTile(player.location.getLocation()).getDisplayCharacter().equals("G")) {
                    player.addGold();
                    board.setTile(player.location.getLocation(), new Tile(".", true));
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
                if (player.location.getLocation().getY() > board.getHeight()-2){    
                    System.out.println("Fail");
                    break;
                }
                else{
                    player.location.move("N");
                }   
                if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                    player.location.move("S");
                    System.out.println("Fail");
                    break;
                }
                else{
                    System.out.println("Success");
                    break;
                }
            case "MOVE S":
                if (player.location.getLocation().getY() < 1) {
                    System.out.println("Fail");
                    break;
                }
                else{
                    player.location.move("S");
                }
                if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                    player.location.move("N");
                    System.out.println("Fail");
                    break;
                }
                else{
                    System.out.println("Success");
                    break;
                }
            case "MOVE E":
                if (player.location.getLocation().getX() > board.getWidth()-2) {
                    System.out.println("Fail");
                    break;
                }
                else{
                    player.location.move("E");
                }
                if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                    player.location.move("W");
                    System.out.println("Fail");
                    break;
                }
                else{
                    System.out.println("Success");
                    break;
                }
            case "MOVE W":
                if (player.location.getLocation().getX() < 1) {
                    System.out.println("Fail");
                    break;
                }
                else{
                    player.location.move("W");
                }
                    if (board.getTile(player.location.getLocation()).isWalkable() == false) {
                    player.location.move("E");
                    System.out.println("Fail");
                    break;
                }
                else{
                    System.out.println("Success");
                    break;
                }

            case "LOOK":
                board.printBoard(player, player.location.getLocation(),2); // prints the board with a radius around the player given by the final value
                break;
            default:
                System.out.println("Invalid Command");
                break;
        }
    }
}
