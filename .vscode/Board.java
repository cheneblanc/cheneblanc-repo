/**
 * Class to represent the game board
 * The board is a 2D array of tiles onto which the player and bot are placed
 * The board is populated from a map file which is read in using the GameFile class
 */

public class Board {
    private char[][] board;
    private int winningGold;
    private int width;
    private int height;
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

    public Board ()
    {
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
     * @throws Exception if the location is out of bounds
     * @throws Exception if the tile type is invalid
     */
     public void setTile(Location location, char tile) {
        board[location.getX()][location.getY()] = tile;
    }

    public void setWinningGold(int winningGold) {
        this.winningGold = winningGold;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void setBot(BotPlayer bot){
        this.bot = bot;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public int getWinningGold() {
        return winningGold;
    }   

    /**
     * Populate the board with the contents of the map file
     * The map file is a 2D array of characters
     * The characters are converted to game tiles
     * @throws Exception if the map file contains invalid characters
     * @throws Exception if there is no exit
     * @throws Exception if there is not enough gold
     * @param mapFile
     */

    /**
     * Generates the portion of the board that is visible to the player
     * Overlays the player and bot on top of the board
     * @param location the location of the player
     * @param see the distance the player can see around them
     * @return a 2D array of tiles representing the visible portion of the board
     */
    public char[][] viewBoard(Location location, int see) {
        
        char [][] visibleBoard = new char[see*2+1][see*2+1];
        
        for (int y = 0; y <= see*2; y++) {
            for (int x = 0; x <= see*2; x++) {
                
                // Translate the x and y co-ordinates of the visible board array to the x and y co-ordinates of the actual board
                int boardX = location.getX() + x - see;
                int boardY = location.getY() + y - see;

                Location boardLocation = new Location(boardX, boardY);

                // If outside the board, set a wall character
                if (isUnreachable(boardLocation)) {
                    visibleBoard[x][y] = WALL;
                    continue;
                } else if(boardLocation.equals(player.location)){ 
                    visibleBoard[x][y] = PLAYER;
                    continue;
                } else if(boardLocation.equals(bot.location)){
                    visibleBoard[x][y] = BOT;
                    continue;
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

    public Boolean isOutOfBounds (Location location){
        return (location.getX() < 0 || location.getX() >= width || location.getY() < 0 || location.getY() >= height);
    }
    
     public Boolean isUnreachable (Location location){
        return (isOutOfBounds(location) || getTile(location) == WALL);
    }

    
}    

