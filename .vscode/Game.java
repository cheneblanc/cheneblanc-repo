import java.util.Scanner;

public class Game {
    
    private static int winningGold;
    
    /* Main method - adds players and runs game */
    public static void main(String[] args) {

        Game game = new Game();

        Scanner scanner = new Scanner(System.in);

        /* Create the board */
        
        Board board = new Board(0, 0, null);

        /* Populate the board from the game file; catch exceptions and ask for a new file is the file isn't valid */
        while (true) {
            try {
                GameFile file = new GameFile(scanner); // Create the file object
                file.readFile();
                System.out.println("Map Name: " + file.getMapName());
                winningGold = file.getWinningGold();
                board = new Board(file.getWidth(), file.getHeight(), file.getBoard());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try a different game file");
                continue;
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

        while (!game.isLost(player1, bot)) {
            
            Input input = new Input(game, board, player1, scanner);
            input.getInput();
            System.out.println("Bot's turn");
            bot.decideAction(game); // Assume bot played first and said "HELLO"; then bot plays after player
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
        return player.getGold() >= winningGold && board.getTile(player.location.getLocation())==Board.EXIT;
    }

    /* Check if the game has been lost - bot and player are in the same location */
    private boolean isLost(Player player, BotPlayer bot) {
        return bot.location.getLocation().equals(player.location.getLocation());
    }
}

