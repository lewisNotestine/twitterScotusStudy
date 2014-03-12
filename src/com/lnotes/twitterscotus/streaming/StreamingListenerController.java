package com.lnotes.twitterscotus.streaming;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

/**
 * Responsible for performing operations for setting up actions for {@link twitter4j} streaming APIs,
 * which then directs other classes responsible for caching and storing data.
 * Encapsulates a {@link twitter4j.TwitterStream} object and performs ops on that.
 *
 * Created by LN_1 on 3/9/14.
 */
public class StreamingListenerController {

    private TwitterStream mTwitterStream;


    /**
     * "Telescoped" constructor; meant to be used with a builder.
     */
    public StreamingListenerController(final Configuration configuration, final Authorization authorization) {
        mTwitterStream = createTwitterStream(configuration, authorization);
    }


    /**
     * Direct the {@link twitter4j.TwitterStream} to listen for a Random sample of Tweets.
     */
    public void listenForRandomSample(final String dataFilePath, final String errorFilePath) {
        //TODO implement.

        mTwitterStream.addListener(new ScotusStreamingListener(dataFilePath, errorFilePath));

        mTwitterStream.sample();
    }

    /**
     * Starts listening for tweets that have the given hashtag(s)
     * @param hashTags array or vararg set of strings representing distinct hashtags.
     */
    public void listenForHashTag(final String... hashTags) {
        //TODO implement.

    }

    /**
     * sets the path for the output file.
     */
    public void setLogFilePath(final String filePath) {

    }

    /**
     * Initializes the {@link twitter4j.TwitterStream object}, injecting the config elements.
     * @param configuration the configuration to use to create the TwitterStream.
     * @param authorization the auth to use to create the TwitterStream.
     * @return the created TwitterStream object.
     */
    private TwitterStream createTwitterStream(final Configuration configuration, final Authorization authorization) {
        final TwitterStreamFactory factory = new TwitterStreamFactory(configuration);
        return factory.getInstance(authorization);
    }

}
