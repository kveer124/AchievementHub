import java.util.ArrayList;
//Game class
public class Game {
    private int appId;
    private String gameName;
    private ArrayList<Achievement> achievements = new ArrayList<>();
    private boolean achievementsLoaded;

    public Game(int appId, String gameName) {
        this.appId = appId;
        this.gameName = gameName;
    }

    public int getId() {
        return appId;
    }

    public String getGameName() {
        return gameName;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
        achievementsLoaded = true;
    }

    public double getCompletion() {
        int unlockCounter = 0;

        double percent = 0;
        if (!achievements.isEmpty()) {

            for (int i = 0; i < achievements.size(); i++) {
                
                if (achievements.get(i).isUnlocked()) {
                    unlockCounter++;
                }
                
            }
            percent = ((double) (unlockCounter) / achievements.size()) * 100; 
            return percent;
        } else {
            return percent;
        }
    }

    public boolean getAchievementsLoaded() {
        return achievementsLoaded;
    }

}