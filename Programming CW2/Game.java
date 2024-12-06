import java.util.Scanner;

public class Game {
    private static int WINNINGGOLD;
    private Location botLocation = new Location(10, 10);

    /* Main method - adds players and runs game */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        /* Create the game */
        Game game = new Game();
        
        /* Create the player */
        Player player1 = new Player();

        /* Read the game file */

        File file = new File(scanner);
        file.readFile();
        WINNINGGOLD = file.getWinningGold();

        /* Create the board */
        Board board = new Board(file.getWidth(), file.getHeight());

        /* Populate the board */
        board.populateBoard(file.getMapFile());

        /* Set the player initial location */
    
        player1.location.setLocation(1,1);



        while (!game.isLost(player1)) {
            Commands commands = new Commands();
            
            commands.getCommands(game, board, player1, player1.location.getLocation(), scanner);
        }
        System.out.println("LOSE");
        System.exit(0);
    }

/* Accesssors */

    public int getWinningGold() {
        return WINNINGGOLD;
    }

/* Condition for winning the game */

    public boolean isGameWon(Player player, Board board) {
        return player.getGold() >= WINNINGGOLD && board.getTile(player.location.getLocation()).getDisplayCharacter().equals("E");
    }

/* Condition for losing the game */

    private boolean isLost(Player player) {
        return botLocation.equals(player.location.getLocation());
    }
}

