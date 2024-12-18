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
        Tile [][] knownBoard = this.look();
        for (int x = 0; x < knownBoard.length; x++){
            for (int y = 0; y< knownBoard[x].length; y++){
                // If bot players sees the player, the exit or gold, store its location.
                if (knownBoard[x][y].equals(Board.PLAYER)){
                    playerFound = true;
                    playerLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                }
                if (knownBoard[x][y].equals(Board.EXIT)){
                    exitLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                }
                if (knownBoard[x][y].equals(Board.GOLD)){
                    nearestGoldLocation = new Location (x+this.location.getLocation().getX()-getSee(), y+this.location.getLocation().getY()-getSee());
                }
            }
        }
    }

    public void decideAction(){
        switch (status){
            case "search":
                botLook();
                status = "move";
                break;
            case "move":
                if (!playerFound){
                    moveBot();
                    status = "search";
                    break;
                }
                chasePlayer();
                status = "search";
                break;
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
        status = "search";    
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
}
