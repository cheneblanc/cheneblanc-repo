/**
 * Imports the board data from the file, extracting the name, the winning gold amount and the board map.
 */

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
    private char[][] board;
    private String mapName;
    private Scanner scanner;

    public GameFile(Scanner scanner) {
        this.scanner = scanner;
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

    public char[][] getBoard() {
        return board;
    }

    public String getMapName() {
        return mapName;
    }

/**
 * Reads the map file and creates a 2D array of characters representing the game board.
 * The map file must contain the map name, the winning gold value, and the map data.
 * The map data must contain only the characters 'W' (wall), 'G' (gold), 'E' (exit), and ' ' (empty space).
 * The map data must contain at least one exit and enough gold to reach the winning gold value.
 * Assumes that the map data is rectangular.
 * @throws Exception if the file is not found
 * @throws Exception if the map name is not found
 * @throws Exception if the winning gold value is not found
 * @throws Exception if the winning gold value is not an integer
 * @throws Exception if the map data contains invalid characters
 * @throws Exception if the total gold in the map is less than the winning gold value
 * @throws Exception if there is no exit in the map
 */     
    public void readFile() throws Exception {

        System.out.println("Enter the file path for the map file: ");
        file = scanner.nextLine();
        FileReader fileReader = new FileReader(file);
                
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)){
    
            String nameLine = bufferedReader.readLine();
            if (!nameLine.startsWith("name")) {
                throw new Exception("Map name not found");
            }
            mapName = nameLine.substring(4).trim();
                      
            String winLine = bufferedReader.readLine();

            if (!winLine.startsWith("win")) {
                throw new Exception("Winning Gold data not found");
            }

            if (!winLine.split("win ")[1].matches("\\d+")) {
                throw new Exception("Winning Gold data is not an integer");
            }
            winningGold = Integer.parseInt(winLine.split("win ")[1]);
                                    
            List <String> mapLines = new ArrayList<String>();

            while ((line = bufferedReader.readLine()) != null) {
                mapLines.add(line);
            }   
                    
            height = mapLines.size();
            System.out.println("Height: " + height);
            
            width = mapLines.get(0).length(); // Assume all rows are the same length as the first row
            System.out.println("Width: " + width);
            // Create a 2D array containing the map characters from the file

            board = new char[width][height];
            int goldCount = 0;
            int exitCount = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    board[x][y] = mapLines.get(height-1-y).charAt(x);
                    if (board[x][y] != Board.WALL && board[x][y] != Board.EMPTY && board[x][y] != Board.GOLD && board[x][y] != Board.EXIT) {
                        throw new Exception("Invalid character in map file. Please ensure it only contains '#', 'G', 'E', or '.'");
                    }
                    if (board[x][y] == Board.GOLD) {
                        goldCount++;
                    }
                    if (board[x][y] == Board.EXIT) {
                        exitCount++;
                    }
                }
            }
            if (goldCount < winningGold) {
                throw new Exception("Total gold in map is less than winning gold value");
            }
            if (exitCount <1){
                throw new Exception("No exit found in map");
            }            
        }
                
    }
    
    
}