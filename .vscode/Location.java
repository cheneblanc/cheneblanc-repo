/**
 * Class for representing a location on the board
 * (0,0) represents the lower left corner of the board
 */
public class Location {
    
    private int x;
    private int y;

    /**
     * Constructor for the location class
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    * Converts compass direction movements to changes in x and y coordinates in the location space
    * @param direction, one of the cardinal points of the compass
    */
    public void move(char direction) {
        switch (direction) {
            case 'N' -> this.y++;
            case 'S' -> this.y--;
            case 'W' -> this.x--;
            case 'E' -> this.x++;
            default -> {
            }
        }
    }
    
    /** 
     * Gets the location of the object
     * @return the location of the object
     */
    public Location getLocation() {
        return this;
    }           
    
    /** 
     * Gets the x coordinate of the location where (0,0) is the lower left corner of the board
     * @return int the x coordinate of the location
     */
    public int getX() {
        return x;
    }

    /** 
     * Gets the y coordinate of the location where (0,0) is the lower left corner of the board
     * @return int the y coordinate of the location
     */
    public int getY() {
        return y;
    }

    /** 
     * Set the location of the object
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }   

    /**
     * Print the location of the player
     * @deprecated only used for debugging
     */
    @Deprecated
    public void printLocation() {
        System.out.println("Location: " + x + ", " + y);
    }

    /**
     * Calculates the Manhattan distance to a given second location
     * Previously used by the bot for determing which target to move to in case of multiple potential targets
     * @deprecated as BotPlayer now calculates distance based on the length of the BFS path
     * @param location - the location to calculate the distance to
     * @return - the Manhattan distance to the given location
     */
    @Deprecated
    public int distanceFrom (Location location) {
        return Math.abs(this.x - location.x) + Math.abs(this.y - location.y);
    }
    
    /**
     * Overrides the default equals method to compare two locations
     * Checks that x and y coordinates are the same and returns true if they are
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Location location = (Location) obj;
        return x == location.x && y == location.y;
    }

    /**
     * Overrides the default hashCode method to generate a hash code for a location
     * @return int the hash code for the location
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

}