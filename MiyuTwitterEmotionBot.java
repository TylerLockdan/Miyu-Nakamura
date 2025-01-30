import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiyuTwitterEmotionBot {
    private Twitter twitter;
    private static final Map<String, String> emotionResponses = new HashMap<>();
    
    static {
        emotionResponses.put("happy", "I'm so happy to hear that! 😊");
        emotionResponses.put("sad", "I'm here for you. Things will get better! 💙");
        emotionResponses.put("angry", "Take a deep breath. Let's talk about it. 😠");
        emotionResponses.put("excited", "That sounds amazing! Tell me more! 🎉");
        emotionResponses.put("tired", "Maybe you should take a break. Rest is important! 😴");
    }
    
    public MiyuTwitterEmotionBot() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("YOUR_CONSUMER_KEY")
          .setOAuthConsumerSecret("YOUR_CONSUMER_SECRET")
          .setOAuthAccessToken("YOUR_ACCESS_TOKEN")
          .setOAuthAccessTokenSecret("YOUR_ACCESS_TOKEN_SECRET");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }
    
    public void replyToEmotionMentions() {
        try {
            List<Status> mentions = twitter.getMentionsTimeline();
            for (Status mention : mentions) {
                String text = mention.getText().toLowerCase();
                for (String emotion : emotionResponses.keySet()) {
                    if (text.contains(emotion)) {
                        String replyText = "@" + mention.getUser().getScreenName() + " " + emotionResponses.get(emotion);
                        twitter.updateStatus(new StatusUpdate(replyText).inReplyToStatusId(mention.getId()));
                        System.out.println("Replied with emotion response to: " + mention.getUser().getScreenName());
                        break;
                    }
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MiyuTwitterEmotionBot miyu = new MiyuTwitterEmotionBot();
        miyu.replyToEmotionMentions();
    }
}

