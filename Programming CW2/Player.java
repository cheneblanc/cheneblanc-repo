public class Player {
    
    public Location location;
    public int gold;
    public Board board;

/* Constructor for player */    
    public Player(Location location, int gold, Board board){ 
        this.location = location;
        this.gold = gold;
        this.board = board;
    }
/* No arguments constructor for player */
    public Player(){
        this.location = new Location (0,0);
        this.gold = 0;
        this.board = new Board(0,0);
    }

/* Accessors for player */

    public int getGold() {
        return gold;
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
        while (!(board.getTile(location.getLocation()).getDisplayCharacter().equals(".") || board.getTile(location.getLocation()).getDisplayCharacter().equals("E"))){
          location.setLocation((int) (Math.random() * board.getWidth()), (int) (Math.random() * board.getHeight()));
        }
    }

// Migrate the player movement methods from Commands.java to Player.java to allow for building of bot as a subclass of player

    public boolean moveNorth(){
        if (this.location.getLocation().getY() > board.getHeight()-2){    
            return false;
        }
        else{
            this.location.move("N");
        }   
        if (board.getTile(this.location.getLocation()).isWalkable() == false) {
            this.location.move("S");
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean moveSouth(){
        if (this.location.getLocation().getY() < 1){    
            return false;
        }
        else{
            this.location.move("S");
        }   
        if (board.getTile(this.location.getLocation()).isWalkable() == false) {
            this.location.move("N");
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean moveEast(){
        if (this.location.getLocation().getX() > board.getWidth()-2){    
            return false;
        }
        else{
            this.location.move("E");
        }   
        if (board.getTile(this.location.getLocation()).isWalkable() == false) {
            this.location.move("W");
            return false;
        }
        else{
            return true;
        }   
    }

    public boolean moveWest(){
        if (this.location.getLocation().getX() < 1){    
            return false;
        }
        else{
            this.location.move("W");
        }   
        if (board.getTile(this.location.getLocation()).isWalkable() == false) {
            this.location.move("E");
            return false;
        }
        else{
            return true;
        }   
    }
}