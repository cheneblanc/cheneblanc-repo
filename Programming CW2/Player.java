public class Player {
    public Board board;
    public Location location;
    private int gold;
    public char displayCharacter;
    private int see = 2;


/* Constructor for player */    
    public Player(Board board, char displayCharacter){ 
        this.board = board;
        this.displayCharacter = displayCharacter;
    }

/* Accessors for player */

    public int getGold() {
        return gold;
    }

    public int getSee(){
        return see;
    }

/* Mutators for player */

    public void addGold() {
        gold++;
    }
    /** 
     * Set the player's start location at random. Check whether the tile on which the player is located is walkable, if not select a new random location.
     * @param board
     */
    
    public void setStartLocation(Board board){
        while (!(board.isEmpty(this.location) || board.isExit(this.location ))){
          location.setLocation((int) (Math.random() * board.getWidth()), (int) (Math.random() * board.getHeight()));
        }
    }

    public boolean moveNorth(){
        if (this.location.getLocation().getY() >= board.getHeight()-1){    
            return false;
        }
        else{
            this.location.move('N');
        }   
        if (board.getTile(this.location.getLocation()).equals(Board.WALL) == true) {
            this.location.move('S');
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean moveSouth(){
        if (this.location.getLocation().getY() <= 0){    
            return false;
        }
        else{
            this.location.move('S');
        }   
        if (board.getTile(this.location.getLocation()).equals(Board.WALL) == true) {
            this.location.move('N');
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean moveEast(){
        if (this.location.getLocation().getX() >= board.getWidth()-1){    
            return false;
        }
        else{
            this.location.move('E');
        }   
        if (board.getTile(this.location.getLocation()).equals(Board.WALL) == true) {
            this.location.move('W');
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean moveWest(){
        if (this.location.getLocation().getX() <= 0){    
            return false;
        }
        else{
            this.location.move('W');
        }   
        if (board.getTile(this.location.getLocation()).equals(Board.WALL) == true) {
            this.location.move('E');
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean pickUp(){
        if (board.isGold(this.location)){
            board.setTile(this.location.getLocation(), new Tile('.', true));
            addGold();
            return true;
        }
        else{
            return false;
        }
    }

    public Tile[][] look(){
        Tile[][] visibleBoard = board.viewBoard(location.getLocation(),this.see);
        return visibleBoard;
    }
}