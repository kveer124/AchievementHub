import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        
        AchievementTracker tracker = new AchievementTracker();
        GameStorage storage = new GameStorage();

        storage.loadGames(tracker);
        
        while (true) {
            try {
                IO.println("""
                ========================================
                            ACHIEVEMENTHUB
                ========================================

                1. View Game Library
                2. Add Game
                3. View Game Achievements
                4. Refresh Achievements
                5. Remove Game
                6. Exit

                ========================================
                Enter your choice (1-6):""");

                int choice = Integer.parseInt(IO.readln());

                if (choice == 1) { 
                    ArrayList<Game> games = tracker.getGames();
                    if (games.isEmpty()) {
                        IO.println("No Games Added");
                    } else {
                        IO.println("========== GAME LIBRARY ==========");

                        for (int i = 0; i < games.size(); i++) {
                            IO.println(games.get(i).getGameName());
                            IO.println("App ID: " + games.get(i).getId());
                        }

                        IO.println("==================================");
                    }
                } else if (choice == 2) {
                    String gameName = IO.readln("Enter Game Name:\n");
                    int appId = Integer.parseInt(IO.readln("Enter the Game's App Id:\n"));

                    boolean valid = tracker.addGame(gameName, appId);

                    if (valid) {
                        storage.saveGames(tracker);
                        IO.println("Game successfully added to your library");
                    } else {
                        IO.println("This game is already in your library");
                    }
                } else if (choice == 3) {
                    
                } else if (choice == 4) {
                    
                } else if (choice == 5) {
                    int appId = Integer.parseInt(IO.readln("Enter the Game's App Id:\n"));

                    boolean valid = tracker.removeGame(appId);

                    if (valid) {
                        storage.saveGames(tracker);
                        IO.println("Game successfully removed from your library");
                    } else {
                        IO.println("This game is not in your library");
                    }
                } else if (choice == 6) {
                    IO.println("Goodbye");
                    break;
                } else {
                    IO.println("Enter a number listed");

                }


            } catch (NumberFormatException ex) {
                IO.println("Enter a number listed");
            }
        }
    }
    
}
