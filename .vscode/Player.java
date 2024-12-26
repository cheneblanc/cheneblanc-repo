/** 
    * To represent a player in the game who move around the board, picking up gold before moving to the exit.
    * Note: BotPlayer extends this class
    * @author Nigel Whiteoak
*/
public class Player {
/**
    * @param board the board on which the player is located
    * @param location the location of the player on the board
    * @param gold the amount of gold the player has collected
    * @param displayCharacter the character that represents the player when the board is viewed
    * @param see the distance the player can see around them when they execute the LOOK command
 */    
    protected Board board;
    protected Location location;
    private int gold;
    private int see = 15;

    public Player(Board board){ 
        this.board = board;
    }

    public int getGold() {
        return gold;
    }

    /** 
     * @return the distance the player can see around them when they use the LOOK command
     */
    public int getSee(){
        return see;
    }

    public void addGold() {
        gold++;
    }
    
    /** 
     * Set the player's location to a random location on the board
     * Checks that the location is empty or an exit (not gold)
     * @throws Exception if no locations are available
     * @param board the board on which the player is located
     */

    public void setStartLocation(){
        location = new Location((int)(Math.random() * board.getWidth()), (int)(Math.random() * board.getHeight()));
        try{
            int tries = 0;
            int maxTries = board.getWidth() * board.getHeight();
            while ((board.isUnreachable(location) || board.getTile(location) == Board.EXIT) && tries < maxTries){
                System.out.println("Try " + tries + 
                             ": Location(" + location.getX() + "," + location.getY() + ")" +
                             " Unreachable: " + board.isUnreachable(location) +
                             " Is Exit: " + (board.getTile(location) == Board.EXIT));
                location = new Location((int)(Math.random() * board.getWidth()), (int)(Math.random() * board.getHeight()));
                tries++;
            }
            if (tries == maxTries){
                throw new Exception("No locations available");
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
        Location destination = location;
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