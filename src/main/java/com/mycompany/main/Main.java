package com.mycompany.main;

import java.io.*;
import java.util.*;

/**
 * The Main class serves as the entry point for the board game simulation. 
 * It handles the setup and configuration of the game by reading data from an input file,
 * initializing game fields and players, and triggering the gameplay simulation.
 */
public class Main {

    /**
     * The main method reads game configuration from a file and simulates the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        
        String filePath = "C:\\Users\\ASUS\\OneDrive\\Documents\\NetBeansProjects\\Main\\src\\main\\java\\com\\mycompany\\main\\input5.txt"; // You can provide an absolute path if necessary
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Game game = new Game();
            List<String> playerEntries = new ArrayList<>(); // List to store (name, strategy) pairs

            int numFields = Integer.parseInt(reader.readLine().trim());

            // Reading the fields and initializing them in the game
            for (int i = 0; i < numFields; i++) {
                String line = reader.readLine().trim();
                String[] fieldData = line.split(" ");
                
                // Determine the type of field to create and add it to the game
                if (fieldData[0].equals("Property")) {
                    game.addField(new PropertyField("Property " + (i + 1)));  // 1-based index for property field
                } else if (fieldData[0].equals("Lucky")) {
                    int bonus = Integer.parseInt(fieldData[1]);
                    if (bonus <= 0) { // the bonus should be always positive it cannot be negative 
                        throw new IllegalArgumentException("Lucky field bonus must be positive: " + bonus);
                    }
                    game.addField(new LuckyField("Lucky " + (i + 1), bonus));  
                } else if (fieldData[0].equals("Service")) {
                    int cost = Integer.parseInt(fieldData[1]);
                    if (cost <= 0) { // the payment to the bank service fee should be positive as well
                        throw new IllegalArgumentException("Service field cost must be positive: " + cost);
                    }
                    game.addField(new ServiceField("Service " + (i + 1), cost));  
                } else {
                    System.err.println("Unknown field type: " + fieldData[0]);
                }
            }

            // Reading the number of players
            int numPlayers = Integer.parseInt(reader.readLine().trim());

            // Reading player data and initializing players in the game
            for (int i = 0; i < numPlayers; i++) {
                String line = reader.readLine().trim();
                String[] dataOfPlayer = line.split(" ");
                if (dataOfPlayer.length != 2) {
                    throw new IllegalArgumentException("Invalid player format: " + line);
                }

                String name = dataOfPlayer[0];
                String strategy = dataOfPlayer[1];

                // Create a unique key for the player entry
                String playerKey = name + "-" + strategy;

                // Checking for unique player names and strategies
                // because I think in a game there are two guys with the same name is possible 
                // but if they are having the same strategy that means they are the same guy trying to cheat in the game 
                if (playerEntries.contains(playerKey)) {
                    throw new IllegalArgumentException("Duplicate player entry: " + playerKey);
                }
                playerEntries.add(playerKey); 

                // Add player based on their strategy
                if (strategy.equals("Greedy")) {
                    game.addPlayer(new GreedyPlayer(name));
                } else if (strategy.equals("Careful")) {
                    game.addPlayer(new CarefulPlayer(name));
                } else if (strategy.equals("Tactical")) {
                    game.addPlayer(new TacticalPlayer(name));
                } else {
                    throw new IllegalArgumentException("Undefined strategy in file: " + strategy);
                }
            }

            // Reading the dice rolls
            List<int[]> diceRolls = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] rolls = line.split(" ");
                int[] roundRolls = new int[rolls.length];

                // Ensuring dice rolls are within valid range
                for (int j = 0; j < rolls.length; j++) {
                    int roll = Integer.parseInt(rolls[j]);
                    if (roll < 1 || roll > 6) {
                        throw new IllegalArgumentException("Dice roll must be between 1 and 6: " + roll);
                    }
                    roundRolls[j] = roll;
                }
                diceRolls.add(roundRolls);
            }

            // Simulating the game using the dice rolls
            game.simulateGame(diceRolls);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Catch any other exceptions that may occur
        }
    }
}
