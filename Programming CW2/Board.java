public class Board {
    public Tile[][] board;
    private int width;
    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        // Board is a 2D array of tiles
        this.board = new Tile[width][height];
    }

    public void populateBoard() {
        Tile gold = new Tile("G", true);
        Tile wall = new Tile("#", false);
        Tile empty = new Tile(".", true);
        Tile exit = new Tile("E", true);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = empty;
            }
        }

        board[5][4] = exit;
        board[5][5] = gold;
        board[5][6] = gold;
        board[6][5] = gold;
        board[7][5] = wall;

    }
    
    public void setTile(Location location, Tile tile) {
        board[location.getX()][location.getY()] = tile;
    }

    public Tile getTile(Location location) {
        return board[location.getX()][location.getY()];
    }

    /* Need to modify this to only show board that is visible to player */
    /* Need to print location of player over the top */

    public void printBoard(Player player, Location location) {
        for (int y = location.getY() +10; y >= location.getY()-10; y--) {
            for (int x = location.getX()-10; x <= location.getX()+10; x++) {
                if (x < 0 || x >= width || y < 0 || y >= height) {
                    System.out.print("#");
                    continue;
                }
                // Modify this code to vary the character
                if(x == location.getX() && y == location.getY()){
                    System.out.print("P");
                    continue;
                }
                else{
                    System.out.print(board[x][y].getDisplayCharacter());
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
