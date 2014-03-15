package com.lnotes.twitterscotus.streaming;

import com.sun.tools.javac.util.Assert;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

/**
 * Builder for making a {@link com.lnotes.twitterscotus.streaming.StreamingListenerController}.
 *
 * Created by LN_1 on 3/9/14.
 */
public class StreamingListenerControllerBuilder {

    private Configuration mConfiguration;
    private Authorization mAuthorization;

    /**
     * sets the Configuration for the built object.
     */
    public StreamingListenerControllerBuilder setConfiguration(final Configuration configuration) {
        mConfiguration = configuration;
        return this;
    }

    /**
     * sets the authorization for the built object.
     */
    public StreamingListenerControllerBuilder setAuthorization(final Authorization authorization) {
        mAuthorization = authorization;
        return this;
    }

    /**
     * gets the product of the build.
     */
    public StreamingListenerController get() {
        //Assert.checkNonNull(mConfiguration);
        //Assert.checkNonNull(mAuthorization);

        return new StreamingListenerController(mConfiguration, mAuthorization);
    }

}
