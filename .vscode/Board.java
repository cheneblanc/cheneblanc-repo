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
     * Constants for the different types of tiles on the board
     */
    public static final char GOLD = 'G';
    public static final char WALL = '#';
    public static final char EMPTY = '.';
    public static final char EXIT = 'E';
    public static final char PLAYER = 'P';
    public static final char BOT = 'B';
    public static final char UNKNOWN = '?';
    
    private Player player;
    private BotPlayer bot;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[width][height];
    }

    public Board () {
        this.width = 0;
        this.height = 0;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
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
     * @return the amount of gold required to win the game
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

