import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SteamAPI {
    private final String steamApiKey;
    private final String steamUserId;
    private final HttpClient client = HttpClient.newHttpClient();

    public SteamAPI(String steamApiKey, String steamUserId) {
        this.steamApiKey = steamApiKey;
        this.steamUserId = steamUserId;
    }

    public ArrayList<Achievement> getAchievements(int appId) {
        String url = "https://api.steampowered.com/ISteamUserStats/GetPlayerAchievements/v1/?key="
                + steamApiKey + "&steamid=" + steamUserId + "&appid=" + appId + "&l=english";

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();
        
        try {
            HttpResponse<String> response = client.send(
            request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                String json = response.body();
                JsonElement element = JsonParser.parseString(json);

                JsonObject object = element.getAsJsonObject();

                JsonObject playerstats = object.getAsJsonObject("playerstats");

                boolean success = playerstats.get("success").getAsBoolean();
                if (success == true) {

                    JsonArray achievements = playerstats.get("achievements").getAsJsonArray();
                    ArrayList<Achievement> achievementList = new ArrayList<>();
                    for (int i = 0; i < achievements.size(); i++) {
                        
                        JsonObject achievement = achievements.get(i).getAsJsonObject();

                        String name = achievement.get("name").getAsString();
                        int achieved = achievement.get("achieved").getAsInt();

                        Achievement gameAchievement = new Achievement(name, achieved == 1);

                        achievementList.add(gameAchievement);

                    }
                    return achievementList;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (IOException | InterruptedException e) {
            IO.println("Could Not Connect To Server");
            return null;
        }
    }
}
