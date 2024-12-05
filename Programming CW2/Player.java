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

/* Accessors for player */

    public int getGold() {
        return gold;
    }

/* Mutators for player */

    public void addGold() {
        gold++;
    }
}