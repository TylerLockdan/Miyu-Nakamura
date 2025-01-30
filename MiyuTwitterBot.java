import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.List;

public class MiyuTwitterBot {
    private Twitter twitter;
    
    public MiyuTwitterBot() {
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
                String replyText = "@" + mention.getUser().getScreenName() + " Hello! How can I assist you?";
                twitter.updateStatus(new StatusUpdate(replyText).inReplyToStatusId(mention.getId()));
                System.out.println("Replied to: " + mention.getUser().getScreenName());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
    
    public void postTweet(String tweet) {
        try {
            twitter.updateStatus(tweet);
            System.out.println("Tweet posted: " + tweet);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MiyuTwitterBot miyu = new MiyuTwitterBot();
        miyu.postTweet("Hello Twitter! I am Miyu Nakamura, your interactive AI chatbot!");
        miyu.replyToMentions();
    }
}
