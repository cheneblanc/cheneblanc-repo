import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;


    // This class reads a file containing a map showing the locations of walls, gold, and the exit, as weel as the player's starting location and the winning gold amount.
    // The file is a .txt file
    // This is an example of the file:
    // Use BufferedReader to read the file
    /* name Example DoD Map
win 3
####################
#...............E..#
#...G..............#
#.........G........#
#..................#
#..................#
#...G..............#
#.............G....#
####################

*/

public class File {
    private String fileName;
    private String line;
    private String[] splitLine;
    private int winningGold;
    private Location playerLocation;
    private Location exitLocation;
    private int width;
    private int height;

    
    public File(String fileName) {
        this.fileName = fileName;
    }

// Read the file     
    public void readFile() {
        
        String filepath = fileName;
        String mapName = new String();
        
        try {
            FileReader fileReader = new FileReader("Programming CW2/DoD.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            try{
                mapName = bufferedReader.readLine();
            }
            catch (Exception e) {
                System.out.println("Error reading map name");
            }
            
            System.out.println(mapName);
            
            try{
                winningGold = Integer.parseInt(bufferedReader.readLine().split("win ")[1]);
            }
            catch (Exception e) {
                System.out.println("Error reading value of gold required to win");
            }
            System.out.println("Winning gold: " + winningGold);


            while ((line = bufferedReader.readLine()) != null) {
                splitLine = line.split("");
                for (int i = 0; i < splitLine.length; i++) {
                    if (splitLine[i].equals("#")) {
                        // Create a wall tile
                    }
                    else if (splitLine[i].equals(".")) {
                        // Create an empty tile
                    }
                    else if (splitLine[i].equals("G")) {
                        // Create a gold tile
                    }
                    else if (splitLine[i].equals("E")) {
                        // Create an exit tile
                    }
                    else if (splitLine[i].equals("P")) {
                        // Create a player tile
                    }
                }
                System.out.println();
            }


        }
        catch (Exception e) {
            System.out.println("Error reading file");
        }
    }  
}