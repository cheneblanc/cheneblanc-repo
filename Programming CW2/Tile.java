// Add back name to make code more readable and not rely on display characters for logic

public class Tile {
    private String displayCharacter;
    private boolean isWalkable;

/* Constructor for tiles */

    public Tile(String displayCharacter, boolean isWalkable) {

        this.displayCharacter = displayCharacter;
        this.isWalkable = isWalkable;
    }

/* Accessors for tiles */    

    public String getDisplayCharacter() {
        return displayCharacter;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

/* Mutators for tiles*/    

    public void setDisplayCharacter(String displayCharacter) {
        this.displayCharacter = displayCharacter;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

}

    

    

    
