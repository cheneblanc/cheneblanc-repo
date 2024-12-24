To run the game, ensure you are in the same directory as the game files
Compile the code by entering the below into terminal and pressing enter 
    javac Board.java BotPlayer.java Game.java GameFile.java Input.java Location.java Player.java
Run the code by pasting the entering the below into terminal and pressing enter
    java Game
The game implements the dungeons of doom game through object oriented methods as outlined below
The game details (the name, amount of gold required to win and map) are read from an external game file in the GameFile class
The Board class is used to handle the details of the board: the height, width and tiles within it
The Location class is used to simplify the tracking of 2D locations within the game (note that the lower left square has position 0,0)
The Player class handles the details of the player (their location and amount of gold) and the logic of the commands that the player may execute
The input of these commands, and the output of any associated text are handled in the Input class
The botPlayer extends the player class, and includes logic to determine which commands the bot chooses to execute
The Game class (where the main function sits) runs the overall game, taking turns between bot and player until the game is either won or lost
