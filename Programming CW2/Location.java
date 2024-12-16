public class Location {
    
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(char direction) {
        if (direction == 'N') {
            this.y++;
        } else if (direction =='S') {
            this.y--;
        } else if (direction == 'W') {
            this.x--;
        } else if (direction == 'E') {
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

}