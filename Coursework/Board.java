/**
 * Class to represent the game board
 * The board is a 2D array of tiles onto which the player and bot are placed
 * The board is populated from a map file which is read in using the GameFile class
 */
public class Board {
    private char[][] board;
    private int winningGold;
    private final int width;
    private final int height;
    /**
     * Constant for tile to represent gold
     */
    public static final char GOLD = 'G';
    /**
     * Constant for tile to represent wall
     */
    public static final char WALL = '#';
    /**
     * Constant for tile to represent empty tiles
     */
    public static final char EMPTY = '.';
    /**
     * Constant for tile to represent an exit
     */
    public static final char EXIT = 'E';
    /**
     * Constant for tile to represent the player
     */
    public static final char PLAYER = 'P';
    /**
     * Constant for tile to represent the bot
     */
    public static final char BOT = 'B';
    /**
     * Constant for tile to represent an uknown tile (used by the bot in generating their knowledge of the board)
     */
    public static final char UNKNOWN = '?';
    
    private Player player;
    private BotPlayer bot;

    /**
     * Constructor for the board
     * @param width - the width of the game board in number of tiles
     * @param height - the height of the game board in number of tiles
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[width][height];
    }

    /**
     * Zero agruments constructor for the board
     * Note as default size is set to 0, the board will need to be set using the setBoard method
     */
    public Board () {
        this.width = 0;
        this.height = 0;
    }
    
    /**
     * Gets the width of the board
     * @return the width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the board
     * @return the height of the board
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Gets the tile type of te board at the specificed location
     * @param location the location to get the tile type of
     * @return char of the board at the location
     */
    public char getTile(Location location) {
        return board[location.getX()][location.getY()];
    }

    /**
     * Set a tile at a location on the board
     * @param location the location of the tile to be set
     * @param tile the type of tile this location should be set to
     */
     public void setTile(Location location, char tile) {
        board[location.getX()][location.getY()] = tile;
    }

    /**
    * Sets the quantity of gold required to win the game
    * @param winningGold the quantity of gold required to win the game
    */        
    public void setWinningGold(int winningGold) {
        this.winningGold = winningGold;
    }

    /**
     * Set the entire board
     * @param board the 2D array of tiles to set the board to
     */
    public void setBoard(char[][] board) {
        this.board = board;
    }

    /**
     * Set the bot player on the board
     * @param bot the bot player to set
     */
    public void setBot(BotPlayer bot){
        this.bot = bot;
    }

    /**
     * Set the human player on the board
     * @param player the player to set
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Returns the amount of gold required to win the game
     * @return the amount of gold
     */
    public int getWinningGold() {
        return winningGold;
    }   

    /**
     * Generates the portion of the board that is visible to the player
     * Overlays the player and bot on top of the board
     * @param location the location of the player
     * @param see the distance the player can see around them in each direction
     * @return a 2D array of tiles representing the visible portion of the board
     */
    public char[][] viewBoard(Location location, int see) {
        
        int viewDistance = see*2+1; // Width and height of the viewable board

        char [][] visibleBoard = new char[viewDistance][viewDistance];
        
        for (int y = 0; y < viewDistance; y++) {
            for (int x = 0; x < viewDistance; x++) {
                
                // Translate the x and y co-ordinates of the visible board array to the x and y co-ordinates of the actual board
                int boardX = location.getX() + x - see;
                int boardY = location.getY() + y - see;

                Location boardLocation = new Location(boardX, boardY);

                if (isUnreachable(boardLocation)) {
                    visibleBoard[x][y] = WALL;
                } else if(boardLocation.equals(player.location)){ 
                    visibleBoard[x][y] = PLAYER;
                } else if(boardLocation.equals(bot.location)){
                    visibleBoard[x][y] = BOT;
                } else{
                    visibleBoard[x][y] = getTile(boardLocation);
                }
            }
        }
        return visibleBoard;
    }

    /**
     * Check if a location is off the board
     * @param location the location to check
     * @return true if the location is off the board, false otherwise
     */
    public boolean isOutOfBounds (Location location){
        return (location.getX() < 0 || location.getX() >= width || location.getY() < 0 || location.getY() >= height);
    }
    
    /**
     * Check if a location is unreachable, either because it is off the board or because it is a wall
     * @param location the location to check
     * @return true if the location is unreachable, false otherwise
     */
     public boolean isUnreachable (Location location){
        return (isOutOfBounds(location) || getTile(location) == WALL);
    }

    
}    

