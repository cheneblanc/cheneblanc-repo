import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameFile {
    private String file;
    private String line;
    private int winningGold;
    private int width;
    private int height;
    private char[][] mapFile;
    private String mapName;
    private Scanner scanner;

    public GameFile(Scanner scanner) {
        this.scanner = scanner;
    }

// Read the file     
    public void readFile() throws IOException {

        System.out.println("Enter the file path for the map file: ");
        file = scanner.nextLine();
        FileReader fileReader = new FileReader(file);
                
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)){
    
            String nameLine = bufferedReader.readLine();
            if (!nameLine.startsWith("name")) {
                throw new IOException("Map name not found");
            }
            mapName = nameLine.substring(4).trim();
                      
            String winLine = bufferedReader.readLine();

            if (!winLine.startsWith("win")) {
                throw new IOException("Winning Gold data not found");
            }

            if (!winLine.split("win ")[1].matches("\\d+")) {
                throw new IOException("Winning Gold data is not an integer");
            }
            winningGold = Integer.parseInt(winLine.split("win ")[1]);
                                    
            List <String> lines = new ArrayList<String>();

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }   
                    
            height = lines.size();
            
            width = lines.get(0).length(); // Assume all lines are the same length

            // Create a 2D array containing the map characters from the file

            mapFile = new char[width][height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    mapFile[x][y] = lines.get(height-1-y).charAt(x);
                }
            }            
        }
                
    }
    
    public int getWinningGold() {
        return winningGold;
    }

    public int getWidth() {
        return width;
    }   

    public int getHeight() {
        return height;
    }   

    public char[][] getMapFile() {
        return mapFile;
    }

    public String getMapName() {
        return mapName;
    }
}