import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MiyuTwitterAPIIntegration {
    private Twitter twitter;
    private static final String WEATHER_API_KEY = "YOUR_WEATHER_API_KEY";
    
    public MiyuTwitterAPIIntegration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("YOUR_CONSUMER_KEY")
          .setOAuthConsumerSecret("YOUR_CONSUMER_SECRET")
          .setOAuthAccessToken("YOUR_ACCESS_TOKEN")
          .setOAuthAccessTokenSecret("YOUR_ACCESS_TOKEN_SECRET");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }
    
    public String getWeather(String city) {
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + WEATHER_API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return "Weather data: " + response.toString(); // Ideally, parse JSON response
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving weather information.";
        }
    }
    
    public void replyWithWeather() {
        try {
            for (Status mention : twitter.getMentionsTimeline()) {
                String text = mention.getText().toLowerCase();
                if (text.contains("weather")) {
                    String city = "Tokyo"; // Default city
                    String replyText = "@" + mention.getUser().getScreenName() + " " + getWeather(city);
                    twitter.updateStatus(new StatusUpdate(replyText).inReplyToStatusId(mention.getId()));
                    System.out.println("Replied with weather update to: " + mention.getUser().getScreenName());
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MiyuTwitterAPIIntegration miyu = new MiyuTwitterAPIIntegration();
        miyu.replyWithWeather();
    }
}
