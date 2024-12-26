/**
 * Extends the Player class to create a bot that can play the game autonomously.
 */ 

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BotPlayer extends Player{
    private String strategy; // LOOTER (tries to get gold, then exit) or CHASER (looks for player then chases them)
    private char targetType; //(the type of tile the bot is currently targeting)
    private Location target; // the (x,y) location of the bot's target
    private Board knownBoard; // the bot's knowledge of the game board
    private ArrayList<Character> path; // the path the bot will take to reach its target
    

    public BotPlayer(Board board) {
        super(board);
        this.knownBoard = new Board(board.getWidth(), board.getHeight());
        setStrategy();
        initialiseKnownBoard();
        System.out.println("Bot strategy: " + strategy);
    }
    
    /**
     * Sets the bot's strategy between chaser (tries to find the player and chase them) and looter (tries to find enough gold to win the game and then exit).
     * The strategy is chosen randomly.
     */
    
    private void setStrategy(){
        int random = (int) (Math.random() * 2);
        switch (random) {
            case 0:
                strategy = "chaser";
                break;
            case 1:
                strategy = "looter";
                break;
            default:
                strategy = "chaser";
        }
    }

    /**
     * Initialises the knownBoard to be the same size as the game board, but with all tiles set to unknown.
     */
    private void initialiseKnownBoard(){
        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getHeight(); y++){
                Location tileLocation = new Location(x,y);
                knownBoard.setTile(tileLocation, Board.UNKNOWN);
            }
        }
    }
    
    /**
     * Moves the bot in a random direction.
     * @deprecated - this method is not used in the final version of the game, but was built initially for the bot to move randomly.
     */
    @Deprecated
    private void moveBot(){
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                movePlayer('N');
                break;
            case 1:
                movePlayer('S');
                break;
            case 2:
                movePlayer('E');
                break;
            case 3:
                movePlayer('W');
                break;
            default:
                break;
        }
    }
    /**
     * Determines the logic for the action that the bot will take
     * @param game the game the bot is playing
     * Works using the concept of a target
     * If the bot has no target, it will look to try to find one
     * 
     */

     public void decideAction(Game game){
        System.out.println("Bot is deciding action");
        if (target == null){
            botLook();
        } else if (location.equals(target)){
            System.out.println("Bot has reached target");
            target = null;
            if (board.getTile(this.location) == Board.EXIT && targetType == Board.EXIT){
                System.out.println("Bot has exited the dungeon with " + getGold() + " gold.");
                System.out.println("LOSE");
                System.exit(0);
            } else if (board.getTile(this.location) == Board.GOLD && targetType == Board.GOLD){
                System.out.println("Bot has picked up gold");
                pickUp();
            } else if (board.getTile(this.location) == Board.EMPTY && targetType == Board.PLAYER){
                knownBoard.setTile(this.location, Board.EMPTY);
                botLook();
            } else {
                botLook();
            }
        } else {
            moveToTarget();
        }
        System.out.println("Bot's location: " + this.location.getX() + "," + this.location.getY());
        System.out.println("Bot's target: " + target.getX() + "," + target.getY());
        System.out.println("Bot's target type: " + targetType);
    }

    
    
    /**
     * Allows the bot to perform the LOOK action and remember the locations of the player, the nearest gold and the nearest exit (if seen).
     * Calls the getNewTarget method to decide where to move next at the end of the method, based on the information found in the LOOK action.
     */
    private void botLook(){
        System.out.println("Bot is looking");
        char [][] seenBoard = this.look();
        for (int x = 0; x < seenBoard.length; x++){
            for (int y = 0; y< seenBoard[x].length; y++){
                int realBoardX = x+this.location.getLocation().getX()-getSee();
                int realBoardY = y+this.location.getLocation().getY()-getSee();
                
                if (board.isOutOfBounds(new Location(realBoardX, realBoardY))){
                    continue;
                } else knownBoard.setTile(new Location(realBoardX, realBoardY), seenBoard[x][y]);
            }
        }    
        getNewTarget();
    }

    /**
     * Finds the nearest location of a given type on the knownBoard.
     * @param targetType - the type of tile to find the nearest location of
     * @return the Location of the nearest tile of the given type
     */

    private Location findNearest(char targetType){
        System.out.println("Bot is finding nearest " + targetType);
        Location nearest = null;
        for (int x = 0; x < knownBoard.getWidth(); x++){
            for (int y = 0; y < knownBoard.getHeight(); y++){
                if (knownBoard.getTile(new Location(x,y)) == targetType){
                    Location newNearest = new Location(x,y);
                    if (nearest == null){
                        nearest = newNearest;
                    } else if ((this.location.distanceFrom(newNearest)) < this.location.distanceFrom(nearest)){
                        nearest = newNearest;
                    }
                }
            }
        }
        return nearest;
    }

    /**
     * Determines the bot's next target based on its strategy.
     * Chasers will target a player's location if they have been seen, otherwise will move to a default target.
     * Looters will target the nearest gold if they see any, otherwise will move to a default target.
     * Looters will target the exit if they have enough gold to win the game.
     * @return the Location of the bot's next target
     */

    public Location getNewTarget(){
        System.out.println("Bot is getting a new target");
        switch (strategy){

            case "chaser":
                    targetType = Board.PLAYER;
                    break;
            
            case "looter":
                if (getGold() < board.getWinningGold()){
                    targetType = Board.GOLD;
                } else targetType = Board.EXIT;
                 
            default:
                targetType = 'X';    
        }
        target = findNearest(targetType);
        if (target == null){
            target = getDefaultTarget();
        }
        return target;
    }

    /**
     * Moves the bot to the target location via the shortest route.
     * NOTE: walls within the game board will block the bot's path as currently implemented.
     * @param target - the target location
     */
    
    public void moveToTarget(){
        
        if (path.isEmpty()) {
            return;
        }

        int xDelta = this.location.getX() - target.getX();
        System.out.println("xDelta: " + xDelta);
        int yDelta = this.location.getY() - target.getY();
        System.out.println("yDelta: " + yDelta);
        
        if (Math.abs(xDelta) > Math.abs(yDelta)){
            if (xDelta > 0){
                movePlayer('W');
                System.out.println("Bot is moving west");
            } else {
                movePlayer('E');
                System.out.println("Bot is moving east");
            }
        }
        else{
            if (yDelta > 0){
                movePlayer('S');
                System.out.println("Bot is moving south");
            } else {
                movePlayer('N');
                System.out.println("Bot is moving north");
            }
        }    
    }

    /**
     * Create a two dimensional array of locations, with each location storing a String.
     * The string reprsents the set of directions to get to that location from the bot's current location.
     * Work outwards from the bot's current location, checking N, S, E and W directions from that location
     * If the direction is reachable, append the direction required to reach it to the current location's string and store it in the location
     * If the location is the target, return the string of directions to get there
     * Move on to the next location in the queue
     * @return the path to the target
     */
    public String findPath(){
        String [][] directions = new String [knownBoard.getWidth()][knownBoard.getHeight()];
        Queue<Location> queue = new LinkedList<>();
    
        queue.add(this.location);
        directions[this.location.getX()][this.location.getY()] = "";
    
        while (!queue.isEmpty()) {
            Location current = queue.poll();
        
            if (current.equals(target)) {
                return directions[current.getX()][current.getY()];
            }
        
            char[] possibleMoves = {'W', 'E', 'N', 'S'};
        
            for (char direction : possibleMoves) {
                Location newLoc = new Location(current.getX(), current.getY());
                newLoc.move(direction);
            
                // Check bounds
                if (board.isUnreachable(newLoc)) {
                    continue;
                }
            
                if (!board.isUnreachable(newLoc) && directions[newLoc.getX()][newLoc.getY()] == null) {
                    directions[newLoc.getX()][newLoc.getY()] = directions[current.getX()][current.getY()] + direction;
                    queue.add(newLoc);
                }
        }
    }
    return null;
}


    /** 
     * If no better target, try to target moving to edge of the current visble board
     * If the target is a wall, just get a random target within the current seen area that isn't a wall or the current square.
     * @return the Location of the bot's default target
    */
    public Location getDefaultTarget(){
        Location newTarget = null;
        // Try moving to one of the corners of the visible board if there's no better target
        
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0: 
                newTarget = new Location (location.getX() + getSee(), location.getY());
                break;
            case 1:
                newTarget = new Location (location.getX() - getSee(), location.getY());
                break;
            case 2:
                newTarget = new Location (location.getX(), location.getY() - getSee());
                break;
            case 3:
                newTarget = new Location (location.getX(), location.getY() + getSee());
                break;
            }
        // If possible, only go close     
        // If this target is a wall, just get a random target within the current seen area that isn't a wall or the current square
        while (board.isUnreachable(newTarget) || newTarget.equals(this.location)) {
            newTarget = new Location ((int) (Math.random() * (2*getSee()+1))-getSee(), (int) (Math.random() * (2*getSee()+1))-getSee());
        }
        return newTarget;
    }
}    
