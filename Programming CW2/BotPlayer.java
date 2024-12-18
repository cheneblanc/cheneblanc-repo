public class BotPlayer extends Player{
    private String status;
    private Boolean playerFound = false;
    private Location playerLocation = new Location(0,0);
    private Location exitLocation;
    private Location nearestGoldLocation;

    public BotPlayer(Board board, Location location, int gold, char displayCharacter, int see, String status) {
        super(board, location, gold, displayCharacter, see);
        this.status = status;
    }

    public int getPlayerXLocation() {
        return playerLocation.getX();
    }

    public int getPlayerYLocation() {
        return playerLocation.getY();
    }

    public String getStatus() {
        return status;
    }

    public Boolean getPlayerFound() {
        return playerFound;
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
                if (seenBoard[x][y].equals(Board.PLAYER)){
                    playerFound = true;
                    playerLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                }
                if (seenBoard[x][y].equals(Board.EXIT)){
                    exitLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                }
                if (seenBoard[x][y].equals(Board.GOLD)){
                    if (nearestGoldLocation == null){
                        nearestGoldLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                    }
                    else if (Math.abs(this.location.getLocation().getX()-nearestGoldLocation.getX())+Math.abs(this.location.getLocation().getY()-nearestGoldLocation.getY()) > Math.abs(this.location.getLocation().getX()-x)+Math.abs(this.location.getLocation().getY()-y)){
                    nearestGoldLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                    }
                }
            }
        }
    }

    public void decideAction(Game game){
        String strategy = "chaser"; // Change this to "chaser" to make the bot chase the player or to "random" to make the bot move randomly.
        
        switch (status){
            case "search":
                botLook();
                status = "move";
                break;
            case "move":
                switch (strategy){
                    case "random":
                        moveBot();
                        status = "search";
                        break;
                    case "chaser":
                        if (playerFound){
                            chasePlayer();
                            status = "search";
                            break;
                        }
                        else moveBot();
                        status = "search";
                        break;
                    case "looter":
                        if (nearestGoldLocation == null){
                            moveBot();
                            status = "search";
                        }
                        else moveToGold();
                        if (this.location.getLocation().equals(nearestGoldLocation)){
                            status = "pickup";
                        }
                        break;
                    default:
                        status = "search";
                        break;
                }
                break;    
            case "loot":
                if (nearestGoldLocation == null){
                    moveBot();
                    status = "search";
                }   
                else moveToGold();
                if (this.location.getLocation().equals(nearestGoldLocation)){
                    status = "pickup";
                }
                break;
            case "pickup":
                pickUp();
                if (getGold()==game.getWinningGold()){
                    status = "exit";
                }
                else status = "search";
                break;
            case "exit":
                if (this.location.getLocation().equals(exitLocation)){
                    System.out.println("Bot has exited the dungeon");
                    System.exit(0);
                }
                else{
                    int xDelta = this.location.getLocation().getX() - exitLocation.getLocation().getX();
                    int yDelta = this.location.getLocation().getY() - exitLocation.getLocation().getY();
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
                break;
                }    
            default:
                status = "search";;
            
        }
    }

    public void chasePlayer(){
        int xDelta = this.location.getLocation().getX() - playerLocation.getLocation().getX();
        
        int yDelta = this.location.getLocation().getY() - playerLocation.getLocation().getY();
  
        
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
    
    public void moveToGold(){
        int xDelta = this.location.getLocation().getX() - nearestGoldLocation.getLocation().getX();
        
        int yDelta = this.location.getLocation().getY() - nearestGoldLocation.getLocation().getY();
        
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
        status = "search";    
    }

}
