// Achievement class
public class Achievement {
    private String achieveName;
    private boolean unlocked;

    public Achievement(String achieveName, boolean unlocked) {
        this.achieveName = achieveName;
        this.unlocked = unlocked;
    }

    public String getAchieveName() {
        return achieveName;
    }

    public boolean isUnlocked() {
        return unlocked;
    }


}
