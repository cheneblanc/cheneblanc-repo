public class BotPlayer extends Player{
    private String status = "move";
    private String strategy;
    private Location playerSeenLocation;
    private Location exitLocation;
    private Location nearestGoldLocation;
    private Location target;

    public BotPlayer(Board board) {
        super(board);
    }

    public int getPlayerXSeen() {
        return playerSeenLocation.getX();
    }

    public int getPlayerYLocation() {
        return playerSeenLocation.getY();
    }

    public String getStatus() {
        return status;
    }
    
    public void setStrategy(){
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
    
    public void moveBot(){
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

    public void botLook(){
        
        Tile [][] seenBoard = this.look();
        for (int x = 0; x < seenBoard.length; x++){
            for (int y = 0; y< seenBoard[x].length; y++){
                // If bot players sees the player, the exit or gold, store its location.
                
                int realBoardX = x+this.location.getLocation().getX()-getSee();
                int realBoardY = y+this.location.getLocation().getY()-getSee();

                if (seenBoard[x][y].equals(Board.PLAYER)){
                    playerSeenLocation = new Location (realBoardX, realBoardY);
                }
                if (seenBoard[x][y].equals(Board.EXIT)){
                    exitLocation = new Location (realBoardX, realBoardY);
                }
                if (seenBoard[x][y].equals(Board.GOLD)){
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

    public void decideAction(Game game){
        if (target == null){
            botLook();
            return;
        }
        System.out.println("Bot is deciding action");
        System.out.println("Bot is at " + this.location.getLocation().getX() + ", " + this.location.getLocation().getY());
        
        if (this.location.equals(target)){
            System.out.println("Bot has reached target");
            target = null;
            playerSeenLocation = null;
            if (this.location.equals(exitLocation) && status.equals("moveToExit")){
                System.out.println("Bot has exited the dungeon with " + getGold() + " gold.");
                System.out.println("LOSE");
                System.exit(0);
            }
            if (this.location.equals(nearestGoldLocation) && status.equals("moveToGold")){
                System.out.println("Bot is picking up gold");
                pickUp();
                nearestGoldLocation = null;
                if (getGold()==game.getWinningGold()){
                    status = "moveToExit";
                }
                else status = "move";
            }
            else {
                System.out.println("Bot is searching");
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
                    status = "chase";
                }
                else {
                    target = getRandomTarget();
                }
                break;
            // If the bot is in looter mode, it will move towards the nearest gold.    
            case "looter":
                System.out.println("Bot is in looter mode");
                if (status == "moveToExit"){
                    if (exitLocation == null){
                        System.out.println("Bot doesn't see the exit");
                        if (target == null){
                            System.out.println("Bot has no target");
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
                        status = "moveToGold";
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
        while (board.getTile(newTarget).equals(Board.WALL) || newTarget.equals(this.location)) {
            newTarget = new Location ((int) (Math.random() * (2*getSee()+1))-getSee(), (int) (Math.random() * (2*getSee()+1))-getSee());
        }

        return newTarget;
    }
}    
