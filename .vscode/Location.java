/**
 * Class for representing a location on the board
 * (0,0) represents the lower left corner of the board
 */

public class Location {
    
    private int x;
    private int y;

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
            case 'N':
                this.y++;
                break;
            case 'S':
                this.y--;
                break;
            case 'W':
                this.x--;
                break;
            case 'E':
                this.x++;
                break;
            default:
                break;
        }
    }
    
    public Location getLocation() {
        return this;
    }           
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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
     * Used by the bot for determing which target to move to in case of multiple potential targets
     * @param location - the location to calculate the distance to
     * @return - the Manhattan distance to the given location
     */
    
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

}