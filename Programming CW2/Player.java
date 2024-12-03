public class Player {
    
    public Location location;
    public int gold;

/* Constructor for player */    
    public Player(Location location, int gold) {
        this.location = location;
        this.gold = gold;
    }
/* No arguments constructor for player */
    public Player(){
        this.location = new Location (0,0);
        this.gold = 0;
    }

/* Methods for player */

    public void printLocation() {
        System.out.println("Player location: " + location.getX() + ", " + location.getY());
    }

    public Location getLocation() {
        return location;
    }

    public int getGold() {
        return gold;
    }

    public void addGold() {
        gold++;
    }

    public void moveNorth() {
        location = new Location(location.getX(), location.getY() + 1);
    }

    public void moveSouth() {
        location = new Location(location.getX(), location.getY() - 1);
    }

    public void moveEast() {
        location = new Location(location.getX() + 1, location.getY());
    }   

    public void moveWest() {
        location = new Location(location.getX() - 1, location.getY());
    }

    
}