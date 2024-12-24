/**
 * Extends the Player class to create a bot that can play the game autonomously.
 */

public class BotPlayer extends Player{
    private char targetType;
    private String strategy;
    private Location playerSeenLocation;
    private Location exitLocation;
    private Location nearestGoldLocation;
    private Location target;

    public BotPlayer(Board board) {
        super(board);
        setStrategy();
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
        if (target == null){
            botLook();
            return;
        }
        
        if (location.equals(target)){
            target = null;
            playerSeenLocation = null;
            if (this.location.equals(exitLocation) && targetType == Board.EXIT){
                System.out.println("Bot has exited the dungeon with " + getGold() + " gold.");
                System.out.println("LOSE");
                System.exit(0);
            }
            if (this.location.equals(nearestGoldLocation) && targetType == Board.GOLD){
                pickUp();
                nearestGoldLocation = null;
                if (getGold()==game.getWinningGold()){
                    targetType = Board.EXIT;
                }
                else targetType = 'X';
            }
            else {
                botLook();
            }
        }
        else {
            moveToTarget(target);
        }
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
                // If bot players sees the player, the exit or gold, store its location.
                
                int realBoardX = x+this.location.getLocation().getX()-getSee();
                int realBoardY = y+this.location.getLocation().getY()-getSee();

                if (seenBoard[x][y]==(Board.PLAYER)){
                    playerSeenLocation = new Location (realBoardX, realBoardY);
                }
                if (seenBoard[x][y]==(Board.EXIT)){
                    if (exitLocation == null){
                        exitLocation = new Location (realBoardX, realBoardY);
                    }
                    else if (this.location.distanceFrom(nearestGoldLocation) > this.location.distanceFrom(new Location (realBoardX, realBoardY))){
                        nearestGoldLocation = new Location (realBoardX, realBoardY);
                    }
                }
                if (seenBoard[x][y]==(Board.GOLD)){
                    if (nearestGoldLocation == null){
                        nearestGoldLocation = new Location (realBoardX, realBoardY);
                    }
                    else if (this.location.distanceFrom(nearestGoldLocation) > this.location.distanceFrom(new Location (realBoardX, realBoardY))){
                        nearestGoldLocation = new Location (realBoardX, realBoardY);
                    }
                }
            }
        }
        getNewTarget();
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
                if (!(playerSeenLocation == null)){
                    target = playerSeenLocation;
                    targetType = Board.PLAYER;
                } else {
                    target = getDefaultTarget();
                }
                break;
            
            case "looter":
                if (targetType == Board.EXIT){
                    if (exitLocation == null){
                        if (target == null){
                            target = getDefaultTarget();
                        }
                    } else {
                        target = exitLocation;
                        break;
                    }
                }
                if (nearestGoldLocation == null){
                    if (target == null){
                        target = getDefaultTarget();
                    }
                } else {
                    target = nearestGoldLocation;
                    targetType = Board.GOLD;
                }
                break;
                 
            default:
                target = getDefaultTarget();    
                }
        return target;
    }

    /**
     * Moves the bot to the target location via the shortest route.
     * NOTE: walls within the game board will block the bot's path as currently implemented.
     * @param target - the target location
     */
    
    public void moveToTarget(Location target){
        
        int xDelta = this.location.getX() - target.getX();
        int yDelta = this.location.getY() - target.getY();
        
        if (Math.abs(xDelta) > Math.abs(yDelta)){
            if (xDelta > 0){
                movePlayer('W');
            } else {
                movePlayer('E');
            }
        }
        else{
            if (yDelta > 0){
                movePlayer('S');
            } else {
                movePlayer('E');
            }
        }    
    }

    /** 
     * If no better target, try to target moving to edge of the current visble board
     * If the target is a wall, get a random target within the current seen area that isn't a wall or the current square.
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
        System.out.println("Default target: " + newTarget);
        return newTarget;
    }
}    
