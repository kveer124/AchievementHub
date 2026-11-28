import java.util.ArrayList;
import java.io.Console;
import java.util.Comparator;

public class Main {
    public static void refreshAchievements(SteamAPI api, AchievementTracker tracker) {
        IO.println("REFRESH ACHIEVEMENTS");

        ArrayList<Game> games = tracker.getGames();
        if (games.isEmpty()) {
            IO.println("No Games Added");
        } else {
            for (int i = 0; i < games.size(); i++) {
                ArrayList<Achievement> newAchievements = api.getAchievements(games.get(i).getId());

                if (newAchievements != null) {
                    games.get(i).setAchievements(newAchievements);
                    IO.println(games.get(i).getGameName() + ": Successfully Refreshed");

                } else {
                    IO.println(games.get(i).getGameName() + ": Unable to retrieve achievements");
                }
            }
            IO.println("Refresh Complete");

        }
    }

    public static void main(String[] args) {
        
        AchievementTracker tracker = new AchievementTracker();
        GameStorage storage = new GameStorage();
        Console console = System.console();
        if (console == null) {
            IO.println("No console available. Please run AchievementHub in a terminal.");
            return;
        }

        

        storage.loadGames(tracker);
        char[] apiKey = console.readPassword("Enter your API Key: ");
        char[] userId = console.readPassword("Enter your User ID : ");
        String stringApiKey = new String(apiKey);
        String stringUserId = new String(userId);

        SteamAPI api = new SteamAPI(stringApiKey, stringUserId);
        refreshAchievements(api, tracker);

        while (true) {
            try {
                IO.println("""
                
                ACHIEVEMENTHUB
                1. View Game Library
                2. Add Game
                3. View Game Achievements
                4. Refresh Achievements
                5. Remove Game
                6. Exit

                Enter your choice (1-6):""");

                int choice = Integer.parseInt(IO.readln());

                if (choice == 1) { 
                    ArrayList<Game> games = new ArrayList<>(tracker.getGames());
                    if (games.isEmpty()) {
                        IO.println("No Games Added");
                    } else {
                        IO.println("""
                                SORT GAME LIBRARY

                                1. Default Order
                                2. Highest Completion First
                                3. Lowest Completion First
                                """);

                        int sortChoice = Integer.parseInt(IO.readln("Enter your choice: "));

                        if (sortChoice == 2) {
                            games.sort(Comparator.comparing(Game::getAchievementsLoaded)
                                    .reversed()
                                    .thenComparing(Comparator.comparingDouble(Game::getCompletion).reversed()));
                        } else if (sortChoice == 3) {
                            games.sort(Comparator.comparing(Game::getAchievementsLoaded)
                                    .reversed()
                                    .thenComparingDouble(Game::getCompletion));
                        } else if (sortChoice != 1) {
                            IO.println("Invalid option");
                            continue;
                        }
                        IO.println("GAME LIBRARY");

                        for (int i = 0; i < games.size(); i++) {
                            IO.println();
                            IO.println(games.get(i).getGameName());
                            IO.println("App ID: " + games.get(i).getId());
                            if (games.get(i).getAchievementsLoaded()) {
                                IO.println("Completion: " + String.format("%.2f%%", games.get(i).getCompletion()));
                            } else {
                                IO.println("Completion: Not Loaded");
                            }

                        }

                        IO.println();
                    }
                } else if (choice == 2) {
                    String gameName = IO.readln("Enter Game Name:\n");
                    int appId = Integer.parseInt(IO.readln("Enter the Game's App Id:\n"));

                    boolean valid = tracker.addGame(gameName, appId);

                    if (valid) {
                        storage.saveGames(tracker);
                        Game game = tracker.getGame(appId);
                        IO.println("Game successfully added to your library");
                        ArrayList<Achievement> newAchievements = api.getAchievements(appId);

                        if (newAchievements != null) {
                            game.setAchievements(newAchievements);
                        } else {
                            IO.println("Unable to retrieve achievements");
                        }

                    } else {
                        IO.println("This game is already in your library");
                    }
                } else if (choice == 3) {
                    int appId = Integer.parseInt(IO.readln("Enter the Game's App Id:\n"));
                    Game game = tracker.getGame(appId);
                    if (game != null) {
                        ArrayList<Achievement> newAchievements = api.getAchievements(appId);

                        if (newAchievements != null) {
                            game.setAchievements(newAchievements);
                        } else {
                            IO.println("Unable to retrieve achievements");
                            continue;
                        }
                        

                        IO.println("GAME ACHIEVEMENTS");
                        IO.println(game.getGameName());
                        IO.println("App ID: " + game.getId());
                        IO.println("Completion: " + String.format("%.2f%%", game.getCompletion()));

                        for (int i = 0; i < game.getAchievements().size(); i++) {
                            if (game.getAchievements().get(i).isUnlocked()) {
                                IO.println("[UNLOCKED] " + game.getAchievements().get(i).getAchieveName());
                            } else {
                                IO.println("[LOCKED] " + game.getAchievements().get(i).getAchieveName());
                            }
                        }
                    } else {
                        IO.println("The game is not in the library");
                    }
                } else if (choice == 4) {
                    refreshAchievements(api, tracker);
                    
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
