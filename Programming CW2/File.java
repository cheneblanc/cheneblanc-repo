import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class File {
    private String file;
    private String line;
    private int winningGold;
    private int width;
    private int height;
    private char[][] mapFile;
    private String mapName;
    private Scanner scanner;

    public File(Scanner scanner) {
        this.scanner = scanner;
    }

// Read the file     
    public void readFile() {
        
        // Add ability for the user to input the file path

        Boolean validFile = false;

        while (!validFile) {
            try {
                System.out.println("Enter the file path for the map file: ");
                file = scanner.nextLine();
                FileReader fileReader = new FileReader(file);
                validFile = true;   

                try (BufferedReader bufferedReader = new BufferedReader(fileReader)){
                    try{
                        mapName = bufferedReader.readLine().substring(4).trim();
                        }
                    catch (Exception e) {
                        System.out.println("Error reading map name");
                        }  
            
                    try{
                        winningGold = Integer.parseInt(bufferedReader.readLine().split("win ")[1]);
                        }
                    catch (Exception e) {
                        System.out.println("Error reading value of gold required to win");
                    }

                    // Determine the width and height of the map
            
                    List <String> lines = new ArrayList<String>();

                    // Read the file line by line to determine the height

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
                catch (Exception e) {
                    System.out.println("Error reading file");
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("File not found");
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