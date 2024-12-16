public class Board {
    public Tile[][] board;
    private int width;
    private int height;
    private Tile gold; 
    private Tile wall; 
    private Tile empty; 
    private Tile exit;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        // Board is a 2D array of tiles
        this.board = new Tile[width][height];

        // Create the tile objects
        this.gold = new Tile("G", true);
        this.wall = new Tile("#", false);
        this.empty = new Tile(".", true);
        this.exit = new Tile("E", true);
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

    public void printBoard(Player player, Location location, Integer see) {
        for (int y = location.getY() +see; y >= location.getY()-see; y--) {
            // If outside the board, print a wall character
            for (int x = location.getX()-see; x <= location.getX()+see; x++) {
                if (x < 0 || x >= width-1 || y < 0 || y >= height-1) {
                    System.out.print(wall.getDisplayCharacter());
                    continue;
                }
                // Print the player location
                if(x == location.getX() && y == location.getY()){
                    System.out.print("P");
                    continue;
                }

                // Add code here to print the bot location

                else{
                    System.out.print(board[x][y].getDisplayCharacter()); // Prints the board tile-by-tile
                }
            }
            System.out.println();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}    

