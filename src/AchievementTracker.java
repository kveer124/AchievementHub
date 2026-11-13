import java.util.ArrayList;
public class AchievementTracker {

    private ArrayList<Game> games = new ArrayList<>();

    public boolean addGame(String gameName, int appId) {
        boolean inList = false;
        for (int i = 0; i < games.size(); i++) {
            if (appId == games.get(i).getId()) {
                inList = true;
                break;
            }
        }
        if (inList == true) {
            return false;
        }
        Game game = new Game(appId, gameName);
        games.add(game);
        return true;

    }
    public boolean removeGame(int appId) {
        int index = -1;

        for (int i = 0; i < games.size(); i++) {
            if (appId == games.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        games.remove(index);
        return true;

    }

    public Game getGame(int appId) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId() == appId) {

                return games.get(i);            
            }
        }

        return null;
    }

}
