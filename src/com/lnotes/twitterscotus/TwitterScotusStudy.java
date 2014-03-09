package com.lnotes.twitterscotus;

import com.lnotes.twitterscotus.auth.OAuthConfig;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TwitterScotusStudy {

    public static void main(String ... args) {
        try {

            final OAuthConfig config = new OAuthConfig();
            final Twitter twitter = config.getConfiguredTwitterAPI();

            final Query query = new Query("#TimAndEric");

            final QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
            }
        } catch (TwitterException twEx) {
            twEx.printStackTrace();
        }

    }
}
