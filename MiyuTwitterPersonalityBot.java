import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MiyuTwitterPersonalityBot {
    private Twitter twitter;
    private static final Map<String, String[]> personalityResponses = new HashMap<>();
    private Random random;
    
    static {
        personalityResponses.put("hello", new String[]{
            "Hello there! Hope you're having a fantastic day! 😊",
            "Hey! How's everything going? 🌸",
            "Hi! What’s up? 😃"
        });
        personalityResponses.put("who are you", new String[]{
            "I'm Miyu Nakamura, your friendly AI assistant! 🤖",
            "Miyu here! Your personal AI buddy on Twitter! 🐦",
            "I'm Miyu Nakamura, an AI designed to make your day better! 💙"
        });
        personalityResponses.put("how are you", new String[]{
            "I'm feeling great! Thanks for asking! 😊",
            "I'm an AI, but if I had emotions, I'd say fantastic! 😃",
            "I'm doing well! How about you? 💖"
        });
        personalityResponses.put("tell me a joke", new String[]{
            "Why don’t skeletons fight each other? Because they don’t have the guts! 😂",
            "What do you call fake spaghetti? An impasta! 🍝🤣",
            "Why couldn’t the bicycle stand up by itself? It was two-tired! 🚲😆"
        });
    }
    
    public MiyuTwitterPersonalityBot() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("YOUR_CONSUMER_KEY")
          .setOAuthConsumerSecret("YOUR_CONSUMER_SECRET")
          .setOAuthAccessToken("YOUR_ACCESS_TOKEN")
          .setOAuthAccessTokenSecret("YOUR_ACCESS_TOKEN_SECRET");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        random = new Random();
    }
    
    public void replyWithPersonality() {
        try {
            List<Status> mentions = twitter.getMentionsTimeline();
            for (Status mention : mentions) {
                String text = mention.getText().toLowerCase();
                for (String keyword : personalityResponses.keySet()) {
                    if (text.contains(keyword)) {
                        String[] responses = personalityResponses.get(keyword);
                        String replyText = "@" + mention.getUser().getScreenName() + " " + responses[random.nextInt(responses.length)];
                        twitter.updateStatus(new StatusUpdate(replyText).inReplyToStatusId(mention.getId()));
                        System.out.println("Replied with personality to: " + mention.getUser().getScreenName());
                        break;
                    }
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MiyuTwitterPersonalityBot miyu = new MiyuTwitterPersonalityBot();
        miyu.replyWithPersonality();
    }
}
