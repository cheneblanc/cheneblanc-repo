import java.util.Scanner;

public class Game {
    
    private static int winningGold;
    
    /* Main method - adds players and runs game */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        /* Create the board */

        File file = new File(scanner); // Create the file object
        file.readFile();
        System.out.println("Map Name: " + file.getMapName());
        winningGold = file.getWinningGold();

        Board board = new Board(file.getWidth(), file.getHeight());

        Game game = new Game(); // Create the game

        /* Populate the board */
        board.populateBoard(file.getMapFile());

        Location startLocation = new Location (0,0);
        Location botLocation = new Location(0, 0);
        Player player1 = new Player(board, startLocation,0,'P',10); // Create the player
        BotPlayer bot = new BotPlayer(board, botLocation, 0, 'B',2,"looter","search"); // Create the bot


        /* Set the player initial location */
               
        player1.setStartLocation(board);
        board.setPlayer(player1);
        bot.setStartLocation(board);
        board.setBot(bot);

        while (!game.isLost(player1, bot)) {
            
            Input input = new Input(game, board, scanner);
            input.getInput(player1);
            
            bot.decideAction(game);
            System.out.println("Bot Status: " + bot.getStatus());
            
        }
        System.out.println("LOSE");
        System.exit(0);
    }

    /* Accesssors */

    public int getWinningGold() {
        return winningGold;
    }

    /* Check if the game can be won on the quit command - player has enough gold and is on the exit tile  */

    public boolean isGameWon(Player player, Board board) {
        return player.getGold() >= winningGold && board.getTile(player.location.getLocation()).getDisplayCharacter()=='E';
    }

    /* Check if the game has been lost - bot and player are in the same location */
    private boolean isLost(Player player, BotPlayer bot) {
        return bot.location.getLocation().equals(player.location.getLocation());
    }
}

