public class Board {
    private Tile[][] board;
    private int width;
    private int height;
    public static final Tile GOLD = new Tile('G', true); 
    public static final Tile WALL = new Tile('#', false); 
    public static final Tile EMPTY = new Tile('.', true); 
    public static final Tile EXIT = new Tile('E', true);
    public static final Tile PLAYER = new Tile('P', true);
    public static final Tile BOT = new Tile('B', true);
    private Player player;
    private BotPlayer bot;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        // Board is a 2D array of tiles
        this.board = new Tile[width][height];
    }

// Set the tiles based on teh contents of the map file

    public void setBot(BotPlayer bot){
        this.bot = bot;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void populateBoard(char[][] mapFile) {
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (mapFile[x][y]) {
                    case 'G':
                        board[x][y] = GOLD;
                        break;
                    case '#':
                        board[x][y] = WALL;
                        break;
                    case '.':
                        board[x][y] = EMPTY;
                        break;
                    case 'E':
                        board[x][y] = EXIT;
                        break;
                }
            }
        }   
    }
    
    public void setTile(Location location, Tile tile) {
        board[location.getX()][location.getY()] = tile;
    }

    public Tile getTile(Location location) {
        return board[location.getX()][location.getY()];
    }

    /* Need to modify this to only show board that is visible to player */

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
                if(boardLocation.equals(player.location.getLocation())){
                    visibleBoard[x][y] = PLAYER;
                    continue;
                }
                // Print the bot location
                if(boardLocation.equals(bot.location.getLocation())){
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

    public Boolean isEmpty (Location location){
        if (board[location.getX()][location.getY()] == EMPTY){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean isExit (Location location){
        if (board[location.getX()][location.getY()] == EXIT){
            return true;
        }
        else{
            return false;
        }
    }   

    public Boolean isGold (Location location){
        if (board[location.getX()][location.getY()] == GOLD){
            return true;
        }
        else{
            return false;
        }
    }   

    public Boolean isWall (Location location){
        if (board[location.getX()][location.getY()] == WALL){
            return true;
        }
        else{
            return false;
        }
    }
}    

