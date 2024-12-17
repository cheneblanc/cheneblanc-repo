public class Board {
    private Tile[][] board;
    private int width;
    private int height;
    private Tile gold; 
    private Tile wall; 
    private Tile empty; 
    private Tile exit;
    private Tile player;
    private Tile bot;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        // Board is a 2D array of tiles
        this.board = new Tile[width][height];

        // Create the tile objects
        this.gold = new Tile('G', true);
        this.wall = new Tile('#', false);
        this.empty = new Tile('.', true);
        this.exit = new Tile('E', true);
        this.player = new Tile('P', true);
        this.bot = new Tile('B', true);
    }

// Set the tiles based on teh contents of the map file

    public void populateBoard(char[][] mapFile) {
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (mapFile[x][y]) {
                    case 'G':
                        board[x][y] = gold;
                        break;
                    case '#':
                        board[x][y] = wall;
                        break;
                    case '.':
                        board[x][y] = empty;
                        break;
                    case 'E':
                        board[x][y] = exit;
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

    public Tile[][] viewBoard(Location location, Integer see) {
        Tile [][] visibleBoard = new Tile[see*2+1][see*2+1];
        for (int y = 0; y <= see*2; y++) {
            for (int x = 0; x <= see*2; x++) {
                // If outside the board, set a wall character
                int boardX = location.getX() + x - see;
                int boardY = location.getY() + y - see;
                Location boardLocation = new Location(boardX, boardY);
                if (boardX < 0 || boardX >= width-1 || boardY < 0 || boardY >= height-1) {
                    visibleBoard[x][y] = wall;
                    continue;
                }
                // Print the player location
                if(boardLocation.equals(location)){
                    visibleBoard[x][y] = player;
                    continue;
                }

                // Add code here to print the bot location

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
        if (board[location.getX()][location.getY()] == empty){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean isExit (Location location){
        if (board[location.getX()][location.getY()] == exit){
            return true;
        }
        else{
            return false;
        }
    }   

    public Boolean isGold (Location location){
        if (board[location.getX()][location.getY()] == gold){
            return true;
        }
        else{
            return false;
        }
    }   

    public Boolean isWall (Location location){
        if (board[location.getX()][location.getY()] == wall){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean isBot (Location location){
        if (board[location.getX()][location.getY()] == bot){
            return true;
        }
        else{
            return false;
        }
    }    

    public Boolean isPlayer (Location location){
        if (board[location.getX()][location.getY()] == player){
            return true;
        }
        else{
            return false;
        }
    }
}    

