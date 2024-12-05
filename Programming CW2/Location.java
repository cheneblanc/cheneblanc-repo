import java.util.Objects;

public class Location {
    
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(String direction) {
        if (direction.equals("N")) {
            this.y++;
        } else if (direction.equals("S")) {
            this.y--;
        } else if (direction.equals("W")) {
            this.x--;
        } else if (direction.equals("E")) {
            this.x++;
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

    public void printLocation() {
        System.out.println("Location: " + x + ", " + y);
    }
    
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

   @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}