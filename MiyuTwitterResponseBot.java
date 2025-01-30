import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiyuTwitterResponseBot {
    private Twitter twitter;
    private static final Map<String, String> predefinedResponses = new HashMap<>();
    
    static {
        predefinedResponses.put("hello", "Hello there! How can I assist you today? 😊");
        predefinedResponses.put("who are you", "I'm Miyu Nakamura, your AI assistant! 🤖");
        predefinedResponses.put("what can you do", "I can chat with you, answer questions, and interact on Twitter! 🐦");
        predefinedResponses.put("thank you", "You're very welcome! 💙");
        predefinedResponses.put("goodbye", "Goodbye! Have a wonderful day! 🌸");
    }
    
    public MiyuTwitterResponseBot() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("YOUR_CONSUMER_KEY")
          .setOAuthConsumerSecret("YOUR_CONSUMER_SECRET")
          .setOAuthAccessToken("YOUR_ACCESS_TOKEN")
          .setOAuthAccessTokenSecret("YOUR_ACCESS_TOKEN_SECRET");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }
    
    public void replyToMentions() {
        try {
            List<Status> mentions = twitter.getMentionsTimeline();
            for (Status mention : mentions) {
                String text = mention.getText().toLowerCase();
                for (String keyword : predefinedResponses.keySet()) {
                    if (text.contains(keyword)) {
                        String replyText = "@" + mention.getUser().getScreenName() + " " + predefinedResponses.get(keyword);
                        twitter.updateStatus(new StatusUpdate(replyText).inReplyToStatusId(mention.getId()));
                        System.out.println("Replied with predefined response to: " + mention.getUser().getScreenName());
                        break;
                    }
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MiyuTwitterResponseBot miyu = new MiyuTwitterResponseBot();
        miyu.replyToMentions();
    }
}
