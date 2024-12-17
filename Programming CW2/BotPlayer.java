public class BotPlayer extends Player{

    public BotPlayer(Board board, Location location, int gold, char displayCharacter) {
        super(board, location, gold, displayCharacter);
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
}
