import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameStorage {
    private String fileName = "games.txt";

    public void saveGames(AchievementTracker tracker) {

        try (PrintWriter writer = new PrintWriter(fileName)) {
            ArrayList<Game> games = tracker.getGames();

            if (!games.isEmpty()) {
                for (int i = 0; i < games.size(); i++) {
                    writer.println(games.get(i).getId() + "|" + games.get(i).getGameName());
                }
            }


        } catch (FileNotFoundException ex) {
            IO.println("Unable to save game library");
        }
    }
    public void loadGames(AchievementTracker tracker) {
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] list = line.split("\\|");
                    tracker.addGame(list[1], Integer.parseInt(list[0]));
                }

            } catch (IOException ex) {
                IO.println("Unable to read file");
            }
        }   
    }
}
