/** 
    * To represent a player in the game who move around the board, picking up gold before moving to the exit.
    * Note: BotPlayer extends this class
*/
public class Player { 
    /** The board on which the player is located */
    protected Board board;
    /** The location of the player on the board */
    protected Location location;
    private int gold;
    private final int see = 2;

    /** 
     * Constructor for the player class
     * @param board the board on which the player is located
     */
    public Player(Board board){ 
        this.board = board;
    }

    
    /** 
     * @return int the amout of gold the player has collected
     */
    public int getGold() {
        return gold;
    }

    /** 
     * @return the distance the player can see around them when they use the LOOK command
     */
    public int getSee(){
        return see;
    }

    /** 
     * Increment the player's gold count by 1
     */
    public void addGold() {
        gold++;
    }
    
    /** 
     * Set the player's location to a random location on the board
     * Checks that the location is empty or an exit (not gold)
     */
    public void setStartLocation(){
        location = new Location((int)(Math.random() * board.getWidth()), (int)(Math.random() * board.getHeight()));
        try{
            int tries = 0;
            int maxTries = board.getWidth() * board.getHeight();
            while ((board.isUnreachable(location) || board.getTile(location) == Board.EXIT) && tries < maxTries){
                location = new Location((int)(Math.random() * board.getWidth()), (int)(Math.random() * board.getHeight()));
                tries++;
            }
            if (tries == maxTries){
                System.out.println("No valid starting location found");
                System.exit(0);
            }
        }
        catch (Exception e){
            System.out.println(e);
            System.exit(0);
        }
       
    }

    /** 
     * Move the player one square north on the board
     * Checks that the player is not at the top edge of the board
     * If not, moves the player one square north
     * If the move lands the player on wall, moves the player back to their original location
     * @return true if the move is successful, false if the player is at the edge of the board or there is a wall in the way
     */
    
    public boolean movePlayer(char direction){
        Location destination = new Location (location.getX(), location.getY());
        destination.move(direction);
        if (board.isUnreachable(destination)){    
            return false;
        } else{
            location = destination;
            return true;
        }
    }

    /** 
     * If the player is on a gold tile, picks up the gold and sets the tile to empty
     * @return true if the player picks up gold, false if there is no gold on the tile
     */

    public boolean pickUp(){
        if (board.getTile(location)==(Board.GOLD)){
            board.setTile(location, Board.EMPTY);
            addGold();
            return true;
        }
        else{
            return false;
        }
    }

    /** 
     * Let's the player look around them on the board
     * @return the visible board around the player as a 2D array of tiles governed by the player's see distance
     */

    public char[][] look(){
        char[][] visibleBoard = board.viewBoard(location,see);
        return visibleBoard;
    }
}