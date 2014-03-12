package com.lnotes.twitterscotus;

import com.lnotes.twitterscotus.auth.OAuthConfig;
import com.lnotes.twitterscotus.streaming.StreamingListenerController;
import com.lnotes.twitterscotus.streaming.StreamingListenerControllerBuilder;
import twitter4j.Twitter;

public class TwitterScotusStudy {

    public static void main(String... args) {
        if (args.length == 3) {
            final OAuthConfig config = new OAuthConfig(args[0]);
            final Twitter twitter = config.getConfiguredTwitterAPI();
            final StreamingListenerController listenerController = new StreamingListenerControllerBuilder()
                    .setConfiguration(twitter.getConfiguration())
                    .setAuthorization(twitter.getAuthorization())
                    .get();

            final String dataFileOutput = args[1];
            final String errorFileOutput = args[2];
            listenerController.listenForRandomSample(dataFileOutput, errorFileOutput);

        } else {
            System.out.println("USAGE: TwitterScotusStudy.jar configFile.xml dataLogFile.Out errorLogFile.out");
        }
    }
}
