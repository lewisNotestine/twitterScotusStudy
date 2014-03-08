package com.lnotes.twitterscotus.auth;


import com.sun.tools.javac.util.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Responsible for doing Twitter OAuth stuff...
 * NOTE: in order for this class to work, one must put an "authconfig.xml" file in the same source directory;
 * which for obvious reasons will not be checked into git...
 *
 * Created by LN_1 on 3/2/14.
 */
public class OAuthConfig {


    private static final String mXmlFilePath = "com/lnotes/twitterscotus/auth/authconfig.xml";
    private final String mAppPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

    //These are the nodes that the XML document should have.
    private static final String NODE_CONSUMER_KEY = "consumer-key";
    private static final String NODE_CONSUMER_SECRET = "consumer-secret";
    private static final String NODE_ACCESS_TOKEN = "access-token";
    private static final String NODE_ACCESS_TOKEN_SECRET = "access-token-secret";

    //Put these in an xml file that will be added to .gitignore, so that no auth credentials are shared.
    private final String mOAuthConsumerKey;
    private final String mOAuthConsumerSecret;
    private final String mOAuthAccessToken;
    private final String mOAuthAccessTokenSecret;

    public OAuthConfig() {
        final Document xmlDocument = loadConfigFromXML();
        xmlDocument.getDocumentElement().normalize();

        mOAuthConsumerKey = parseXMLNodeById(xmlDocument, NODE_CONSUMER_KEY);
        Assert.checkNonNull(mOAuthConsumerKey);
        assert(!mOAuthConsumerKey.isEmpty());

        mOAuthConsumerSecret = parseXMLNodeById(xmlDocument, NODE_CONSUMER_SECRET);
        assert(!mOAuthConsumerSecret.isEmpty());

        mOAuthAccessToken = parseXMLNodeById(xmlDocument, NODE_ACCESS_TOKEN);
        assert(!mOAuthAccessToken.isEmpty());

        mOAuthAccessTokenSecret = parseXMLNodeById(xmlDocument, NODE_ACCESS_TOKEN_SECRET);
        assert(!mOAuthAccessTokenSecret.isEmpty());
    }


    /**
     * Gets a preconfigured {@link twitter4j.Twitter} object which is already set up
     * with the configuration loaded here.
     * @return the configured Twitter object with the right OAuth credentials.
     */
    public Twitter getConfiguredTwitterAPI() {
        final ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true)
                .setOAuthConsumerKey(mOAuthConsumerKey)
                .setOAuthConsumerSecret(mOAuthConsumerSecret)
                .setOAuthAccessToken(mOAuthAccessToken)
                .setOAuthAccessTokenSecret(mOAuthAccessTokenSecret);

        final TwitterFactory factory = new TwitterFactory(builder.build());
        return factory.getInstance();
    }


    /**
     * Load the Config elements from an XML file so that we don't have to hardcode our auth credentials.
     * //TODO: clean this up and doc for xml document schema etc.
     */
    private Document loadConfigFromXML() {
        try {
            final File file = new File(mAppPath + mXmlFilePath);
            assert(file.exists() && file.canRead());
            DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = bf.newDocumentBuilder();

            return builder.parse(file);
        } catch (SAXException saxEx) {
            //TODO
            System.out.println(saxEx.getStackTrace());
        } catch (ParserConfigurationException pcEx){
            //TODO
            System.out.println(pcEx.getStackTrace());
        } catch (IOException ioEx) {
            //TODO
            System.out.println(ioEx.getStackTrace());
        }

        //TODO: don't return null
        return null;
    }

    /**
     * Helper method to get the inner value of an xml node.
     * Performs validation on the search to ensure that we only have one node of the given name,
     * that the node isn't null, and that it has an inner value.
     * @param document the xml document to search.
     * @param searchName the name to search for.
     * @return the inner value of the node with the tag searched for.
     */
    private String parseXMLNodeById(final Document document, final String searchName) {
        final NodeList list = document.getDocumentElement().getElementsByTagName(searchName);
        assert(list.getLength() == 1);

        Node el = document.getDocumentElement().getElementsByTagName(searchName).item(0);
        assert(el != null);

        final String nodeValue = el.getFirstChild().toString();
        assert(nodeValue != null);

        return nodeValue;
    }
}
