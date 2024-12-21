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
        }
    }
    
    /**
     * Moves the bot in a random direction.
     * @deprecated - this method is not used in the final version of the game, but was built initially for the bot to move randomly.
     */

    private void moveBot(){
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                moveNorth();
                break;
            case 1:
                moveSouth();
                break;
            case 2:
                moveEast();
                break;
            case 3:
                moveWest();
                break;
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
        
        if (this.location.equals(target)){
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

    public Location getNewTarget(){
           
        switch (strategy){

            case "chaser":
                if (!(playerSeenLocation == null)){
                    target = playerSeenLocation;
                    targetType = Board.PLAYER;
                }
                else {
                    target = getRandomTarget();
                }
                break;
            // If the bot is in looter mode, it will move towards the nearest gold.    
            case "looter":
                System.out.println("Bot is in looter mode");
                if (targetType == Board.EXIT){
                    if (exitLocation == null){
                        if (target == null){
                            target = getRandomTarget();
                        }
                    }
                    else{
                        target = exitLocation;
                    }
                }
                
                if (nearestGoldLocation == null){
                System.out.println("Bot doesn't see any gold");
                    if (target == null){
                                System.out.println("Bot has no target");
                                target = getRandomTarget();
                            }
                        }
                    else{
                        target = nearestGoldLocation;
                        targetType = Board.GOLD;
                    }
                    break;
            default:
                System.out.println("Bot is in random strategy");
                target = getRandomTarget();    
                }
        return target;
    }

    public void moveToTarget(Location target){
        
        int xDelta = this.location.getLocation().getX() - target.getLocation().getX();
        int yDelta = this.location.getLocation().getY() - target.getLocation().getY();
        
        if (Math.abs(xDelta) > Math.abs(yDelta)){
            if (xDelta > 0){
                moveWest();
            }
            else{
                moveEast();
            }
        }
        else{
            if (yDelta > 0){
                moveSouth();
            }
            else{
                moveNorth();
            }
        }    
    }

    // If no better target, target moving to a corner of the current visble board.
    public Location getRandomTarget(){
        Location newTarget = null;
        // Try moving to one of the corners of the visible board if there's no better target
        do{
            int random = (int) (Math.random() * 4);
            switch (random) {
                case 0: 
                    newTarget = new Location (location.getX() + getSee(), location.getY()+getSee());
                    break;
                case 1:
                    newTarget = new Location (location.getX() - getSee(), location.getY()+getSee());
                    break;
                case 2:
                    newTarget = new Location (location.getX() + getSee(), location.getY()-getSee());
                    break;
                case 3:
                    newTarget = new Location (location.getX() - getSee(), location.getY()-getSee());
                    break;
            }
        // Keep within two squares of the edge (unless there's a better target)    
        } while (newTarget.getX() < 2 || newTarget.getX() >= board.getWidth()-2 || newTarget.getY() < 2 || newTarget.getY() >= board.getHeight()-2);
        // If this target is a wall, just get a random target within the current seen area that isn't a wall or the current square
        while (board.getTile(newTarget)==(Board.WALL) || newTarget.equals(this.location)) {
            newTarget = new Location ((int) (Math.random() * (2*getSee()+1))-getSee(), (int) (Math.random() * (2*getSee()+1))-getSee());
        }

        return newTarget;
    }
}    
