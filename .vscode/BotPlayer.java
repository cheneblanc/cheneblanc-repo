import java.util.LinkedList;
import java.util.Queue;

/**
 * Extends the Player class to create a bot that can play the game autonomously.
 */ 
public class BotPlayer extends Player{
    private String strategy; // LOOTER (tries to get gold, then exit) or CHASER (looks for player then chases them)
    private char targetType; //(the type of tile the bot is currently targeting)
    private Location target; // the (x,y) location of the bot's target
    private final Board knownBoard; // the bot's knowledge of the game board
    private String path; // the path the bot will take to reach its target
    
    /**
     * Constructor for the BotPlayer class.
     * Has an additional board object to represent the bot's knowledge of the game board.
     * Sets the bot's strategy randomly between chaser and looter.
     * @param board - the game board
     */
    public BotPlayer(Board board) {
        super(board);
        this.knownBoard = new Board(board.getWidth(), board.getHeight());
        setStrategy();
        initialiseKnownBoard();
    }
    
    /**
     * Sets the bot's strategy between chaser (tries to find the player and chase them) and looter (tries to find enough gold to win the game and then exit).
     * The strategy is chosen randomly.
     */
    private void setStrategy(){
        int random = (int) (Math.random() * 2);
        this.strategy = switch (random) {
            case 0 -> "chaser";
            case 1 -> "looter";
            default -> "chaser";
        };
    }

    
    /** 
     * Get's the bot player's strategy
     * @return String containing the strategy of the bot
     */
    public String getStrategy(){
        return strategy;
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
            case 0 -> movePlayer('N');
            case 1 -> movePlayer('S');
            case 2 -> movePlayer('E');
            case 3 -> movePlayer('W');
            default -> {}
        }
    }
    /**
     * Determines the logic for the action that the bot will take
     * Works using the concept of a target
     * If the bot has no target, it will look to try to find one
     */
     public void decideAction(){
        if (target == null){
            planRoute();   
        } else if (location.equals(target)){
            target = null;
            if (board.getTile(this.location) == Board.EXIT && targetType == Board.EXIT){
                System.out.println("Bot has exited the dungeon with " + getGold() + " gold.");
                System.out.println("LOSE");
                System.exit(0);
            } else if (board.getTile(this.location) == Board.GOLD && targetType == Board.GOLD){
                pickUp();
            } else if (board.getTile(this.location) == Board.EMPTY && targetType == Board.PLAYER){
                knownBoard.setTile(this.location, Board.EMPTY); // May actually be Gold (unlikely) or Exit, but safest for bot to assume empty (we know it's not wall)
                planRoute();
            } else {
                planRoute();
            }
        } else {
            moveToTarget();
        }
    }

    
    
    /**
     * Allows the bot to perform the LOOK action and remember the locations of the player, the nearest gold and the nearest exit (if seen).
     * Calls the getNewTarget method to decide where to move next at the end of the method, based on the information found in the LOOK action.
     */
    private void botLook(){
        char [][] seenBoard = this.look();
        for (int x = 0; x < seenBoard.length; x++){
            for (int y = 0; y< seenBoard[x].length; y++){
                int realBoardX = x+this.location.getLocation().getX()-getSee();
                int realBoardY = y+this.location.getLocation().getY()-getSee();
                
                if (!board.isOutOfBounds(new Location(realBoardX, realBoardY))){
                    knownBoard.setTile(new Location(realBoardX, realBoardY), seenBoard[x][y]);
                } 
            }
        }    
    }

    /**
     * Finds the nearest reachable location of a given type on the knownBoard.
     * Runs through board and finds tiles of the target type.
     * Checks if there is a path to a found tile.
     * If there is a path, calculates the distance to the tile using the length of the path String.
     * If the distance is less than the distance to the current nearest tile, it updates the nearest tile.
     * @param targetType - the type of tile to find the nearest location of
     * @return the Location of the nearest tile of the given type
     */

    private Location findNearest(char targetType){
        Location nearest = null;
        int distance = board.getHeight() * board.getWidth(); // Maximum possible distance
        int newDistance = board.getHeight() * board.getWidth();
        for (int x = 0; x < knownBoard.getWidth(); x++){
            for (int y = 0; y < knownBoard.getHeight(); y++){
                if (knownBoard.getTile(new Location(x,y)) == targetType){
                    Location newNearest = new Location(x,y);
                    String newPath = findPath(newNearest);
                    if (newPath != null){
                        newDistance = newPath.length();
                    }
                    if ((nearest == null && path != null) || (newDistance < distance)){
                        nearest = newNearest;
                        distance = newDistance;
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
        targetType = switch (strategy) {
            case "chaser" -> Board.PLAYER;
            case "looter" -> {
                if (getGold() < board.getWinningGold()) {
                    yield Board.GOLD;
                } else {
                    yield Board.EXIT;
                }
            }
            default -> 'X';
        };
        target = findNearest(targetType);
        if (target == null){
            target = getDefaultTarget();
        }
        return target;
    }

    /**
     * Method to find a path to the target, if there is one on the bot's knownBoard - simple version of breadth first search
     * Create a two dimensional array of strings.
     * Each string reprsents the set of directions needed to get to that location from the bot's current location.
     * Work outwards from the bot's current location, checking N, S, E and W directions from that location
     * If the cell in that direction is reachable, append the direction required to reach it to the current location's string and store it in that cell
     * If the location is the target, return the string of directions to get there
     * Move on to the next location in the queue
     * @param destination - the location to find a path to
     * @return the String containing the set of directions to reach the target from the current location if the target is found, otherwise null
     */
    public String findPath(Location destination) {
        String[][] directions = new String[knownBoard.getWidth()][knownBoard.getHeight()];
        Queue<Location> queue = new LinkedList<>();
        
        queue.add(this.location);
        directions[this.location.getX()][this.location.getY()] = "";
    
        while (!queue.isEmpty()) {
            Location current = queue.poll();
            
            if (current.equals(destination)) {
                return directions[current.getX()][current.getY()];
            }
            
            char[] possibleMoves = {'W', 'E', 'N', 'S'};
            for (char direction : possibleMoves) {
                Location newLoc = new Location(current.getX(), current.getY());
                newLoc.move(direction);
                                
                if (board.isUnreachable(newLoc)) {
                    continue;
                }
                
                if (directions[newLoc.getX()][newLoc.getY()] == null && knownBoard.getTile(newLoc) != Board.UNKNOWN) {
                    directions[newLoc.getX()][newLoc.getY()] = directions[current.getX()][current.getY()] + direction;
                    queue.add(newLoc);
                }
            }
        }
        
        //System.out.println("No path found!");
        return null;
    }

    /** 
     * If no better target, try moving two squares in a random direction from the bot's current location.
     * If the target is a wall, just get a random target within the current seen area that isn't a wall or the current square.
     * @return the Location of the bot's default target
    */
    public Location getDefaultTarget(){
        Location newTarget;
        
        int random = (int) (Math.random() * 4);
        newTarget = switch (random) {
            case 0 -> new Location(location.getX() + getSee(), location.getY());
            case 1 -> new Location(location.getX() - getSee(), location.getY());
            case 2 -> new Location(location.getX(), location.getY() - getSee());
            case 3 -> new Location(location.getX(), location.getY() + getSee());
            default -> new Location(location.getX(), location.getY());
        };
        // If this target is a wall, just get a random target within the current seen area that isn't a wall or the current square
        while (board.isUnreachable(newTarget) || newTarget.equals(this.location)) {
            newTarget = new Location ((int) (Math.random() * (2*getSee()+1))-getSee(), (int) (Math.random() * (2*getSee()+1))-getSee());
        }
        targetType = Board.UNKNOWN; // Could actually be anything but wall (and definitely isn't unknown), but doesn't matter.
        return newTarget;
    }

    /**
     * Executes a look and uses the information to identify a target and plan a route to it.
     */
    
    private void planRoute(){
        botLook();
        getNewTarget();
        path = findPath(target);
        while (this.path == null){
            target = getDefaultTarget();
            path=findPath(target);
        }       }


    /**
     * Moves the bot to the target location via the path found by the findPath method.
     * Move one turn at a time, removing the direction from the path String each time.
     */
    public void moveToTarget(){
        char direction = path.charAt(0);
        path = path.substring(1);
        movePlayer(direction);
    }
}    