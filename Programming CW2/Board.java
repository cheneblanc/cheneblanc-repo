/**
 * Class to represent the game board
 * The board is a 2D array of tiles onto which the player and bot are placed
 * The board is populated from a map file which is read in using the GameFile class
 */

public class Board {
    private Tile[][] board;
    private int width;
    private int height;
    private GameFile gameFile; 
    public static final Tile GOLD = new Tile('G'); 
    public static final Tile WALL = new Tile('#'); 
    public static final Tile EMPTY = new Tile('.'); 
    public static final Tile EXIT = new Tile('E');
    public static final Tile PLAYER = new Tile('P');
    public static final Tile BOT = new Tile('B');
    private Player player;
    private BotPlayer bot;

    public Board(int width, int height, GameFile gameFile) {
        this.width = width;
        this.height = height;
        this.gameFile = gameFile;
        this.board = new Tile[width][height];
    }

    public void setBot(BotPlayer bot){
        this.bot = bot;
        bot.setStrategy();
    }

    public void setPlayer(Player player){
        this.player = player;
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
    
    public void populateBoard(char[][] mapFile) throws Exception {
        int exitCount = 0;
        int goldCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                switch (mapFile[x][y]) {
                    case 'G':
                        board[x][y] = GOLD;
                        goldCount ++;
                        break;
                    case '#':
                        board[x][y] = WALL;
                        break;
                    case '.':
                        board[x][y] = EMPTY;
                        break;
                    case 'E':
                        board[x][y] = EXIT;
                        exitCount ++;
                        break;
                    default:
                        throw new Exception("Invalid character in map file");
                }
            }  
        } 
        if (exitCount == 0){
            throw new Exception("No exit character E in map file");
        }    
        if (goldCount < gameFile.getWinningGold()){
            throw new Exception("Total number of Gold characters G in map file is less than the winning gold amount");
        }
    }
    
    /**
     * Set a tile at a location on the board
     * @param location the location of the tile to be set
     * @param tile the type of tile this location should be set to
     * @throws Exception if the location is out of bounds
     * @throws Exception if the tile type is invalid
     */
    
    public void setTile(Location location, Tile tile) {
        board[location.getX()][location.getY()] = tile;
    }


    public Tile getTile(Location location) {
        return board[location.getX()][location.getY()];
    }

    /**
     * Generates the portion of the board that is visible to the player
     * Overlays the player and bot on top of the board
     * @param location the location of the player
     * @param see the distance the player can see around them
     * @return a 2D array of tiles representing the visible portion of the board
     */

    public Tile[][] viewBoard(Location location, int see) {
        
        Tile [][] visibleBoard = new Tile[see*2+1][see*2+1];
        
        for (int y = 0; y <= see*2; y++) {
            for (int x = 0; x <= see*2; x++) {
                // If outside the board, set a wall character
                int boardX = location.getX() + x - see;
                int boardY = location.getY() + y - see;
                Location boardLocation = new Location(boardX, boardY);
                if (boardX < 0 || boardX >= width-1 || boardY < 0 || boardY >= height-1) {
                    visibleBoard[x][y] = WALL;
                    continue;
                }
                // Print the player location
                if(boardLocation.equals(player.location)){
                    visibleBoard[x][y] = PLAYER;
                    continue;
                }
                // Print the bot location
                if(boardLocation.equals(bot.location)){
                    visibleBoard[x][y] = BOT;
                    continue;
                }
                else{
                    visibleBoard[x][y] = getTile(boardLocation);
                }
            }
        }
        return visibleBoard;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}    

