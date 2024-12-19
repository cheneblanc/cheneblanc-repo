// Add back name to make code more readable and not rely on display characters for logic

public class Tile {
    private char displayCharacter;

/* Constructor for tiles */

    public Tile(char displayCharacter, boolean isWalkable) {

        this.displayCharacter = displayCharacter;
    }

/* Accessors for tiles */    

    public char getDisplayCharacter() {
        return displayCharacter;
    }

/* Mutators for tiles*/    

    public void setDisplayCharacter(char displayCharacter) {
        this.displayCharacter = displayCharacter;
    }

}

    

    

    
