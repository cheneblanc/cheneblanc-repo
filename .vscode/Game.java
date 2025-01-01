import java.util.Scanner;

/**
 * Main class to run the game
 * Sets up the game, the board and the players, including placing them on the board
 * First turn handled separately to allow LOOTER to implicitly say HELLO on first turn
 * After that the game loops so long as it isn't lost (winning handled elsewhere)
 */
public class Game {
    
    /**
     * Main method to run the game
     * @param args - default main method parameter
    */
    public static void main(String[] args) {

        Game game = new Game();

        Scanner scanner = new Scanner(System.in);

        Board board = new Board();

        /* Populate the board from the game file; catch exceptions and ask for a new file is the file isn't valid */
        while (true) {
            try {
                GameFile file = new GameFile(scanner); // Create the file object
                file.readFile();
                System.out.println("Map Name: " + file.getMapName());
                board = new Board(file.getWidth(), file.getHeight()); 
                board.setBoard(file.getBoard());
                board.setWinningGold(file.getWinningGold());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try a different game file");
            }

        }
        Player player1 = new Player(board); // Create the player
        BotPlayer bot = new BotPlayer(board); // Create the bot
        
        player1.setStartLocation();
        board.setPlayer(player1);
        do {
            bot.setStartLocation();
        } while (player1.location.equals(bot.location));    
        board.setBot(bot);
        
        // Handle first turns separately to allow LOOTER to implicitly say HELLO on first turn; CHASER does't need this 
        // Human always plays first
        Input input = new Input(game, board, player1, scanner);
        input.getInput();
        if (!bot.getStrategy().equals("LOOTER")){
            bot.decideAction();
        }
        // Main game loop
        while (!game.isLost(player1, bot)) {
            input.getInput();
            bot.decideAction();
        }
        
        System.out.println("LOSE");
        System.exit(0);
    }

    /**
     * Check if the game has been won
     * @param player - the player object
     * @param board - the board object
     * @return - true if the game has been won, false otherwise
     */
    public boolean isGameWon(Player player, Board board) {
        return player.getGold() >= board.getWinningGold() && board.getTile(player.location.getLocation())==Board.EXIT;
    }

    /**
     * Check if the game has been lost
     * @param player - the player object
     * @param bot - the bot object
     * @return - true if the game has been lost, false otherwise
     */
    private boolean isLost(Player player, BotPlayer bot) {
        return bot.location.getLocation().equals(player.location.getLocation());
    }
}

