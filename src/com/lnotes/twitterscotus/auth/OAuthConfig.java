package com.lnotes.twitterscotus.auth;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Responsible for doing Twitter OAuth stuff...
 * Created by LN_1 on 3/2/14.
 */
public class OAuthConfig {

    private static final String OAUTH_CONSUMER_KEY = "my key";
    private static final String OAUTH_CONSUMER_SECRET = "my secret";
    private static final String OAUTH_ACCESS_TOKEN = "my access token";
    private static final String OAUTH_ACCESS_TOKEN_SECRET = "my access token secret";

    public Twitter getConfiguredTwitterAPI() {
        final ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true)
                .setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
                .setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
                .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);

        final TwitterFactory factory = new TwitterFactory(builder.build());
        return factory.getInstance();
    }


}
