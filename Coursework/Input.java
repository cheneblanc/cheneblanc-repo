import java.util.Scanner;

/**
 * Handles terminal inputs and outputs from the human player during the game
 * Validates the inputs and converts them into the game commands
 * Prints out the relevant responses to these commands
 */
public class Input {
        
    private final Game game;
    private final Board board;
    private final Player player;
    private final Scanner scanner;

    /**
     * Constructor for the Input class
     * @param game the game object
     * @param board the board object
     * @param player the player object
     * @param scanner the scanner object for reading the input
     */
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
                case "PICKUP" -> {
                    Boolean pickup = player.pickUp();
                    if (pickup) {
                        System.out.println("Success. Gold Owned: " + player.getGold());
                    } else {
                        System.out.println("Fail");
                    }
                    validCommand = true;
                }
                case "HELLO" -> {
                    System.out.println("Gold to win: " + board.getWinningGold());
                    validCommand = true;
                }
                case "GOLD" -> {
                    System.out.println("Gold Owned: " + player.getGold());
                    validCommand = true;
                }
                case "QUIT" -> {
                    if (game.isGameWon(player, board)) {
                        System.out.println("WON");
                        System.out.println("Well done - you escaped the Dungeon of Doom with " + player.getGold() + " gold!");
                        System.exit(0);
                    } else {
                        System.out.println("LOST");
                        System.exit(0);
                    }
                }
                case "MOVE N", "MOVE S", "MOVE E", "MOVE W" -> {
                    char direction = command.charAt(5);
                    Boolean moveResult = player.movePlayer(direction);
                    if (moveResult) {
                        System.out.println("Success");
                    } else {
                        System.out.println("Fail");
                    }
                    validCommand = true;
                }
                case "LOOK" -> {
                    char[][] visibleBoard = player.look();
                    for (int y = visibleBoard.length-1; y >= 0; y--) {
                        for (int x = 0; x < visibleBoard[y].length; x++) {
                            System.out.print(visibleBoard[x][y]);
                        }
                        System.out.println();
                    }
                    validCommand = true;
                }
                default -> System.out.println("Invalid Command. Please enter HELLO, LOOK, GOLD, PICKUP, MOVE N, MOVE S, MOVE E, MOVE W or QUIT");
            }
        }
    }
}
