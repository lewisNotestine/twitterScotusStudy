package com.lnotes.twitterscotus.streaming;

import org.apache.commons.io.FilenameUtils;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Implements the basic StreamingListener for the project.
 * At this time we're just using this to cache the data on disk, but could be something more sophisticated in the future.
 * <p/>
 * Needs to handle high-throughput I/O so we use a BufferedWriter to write to file.
 * <p/>
 * Created by LN_1 on 3/9/14.
 */
public class ScotusStreamingListener implements StatusListener {

    private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String DELIMITER = "|||||";
    private static final String TWEET_DELIMITER = "|||***|||***|||";

    private File mDataOutputFile;
    private File mErrorLogFile;
    private File mDataDirectory;
    private File mErrorDirectory;
    private String mBaseFileName;
    private String mBaseFileExtension;

    private BufferedWriter mBufferedDataWriter;
    private BufferedWriter mBufferedErrorWriter;

    public ScotusStreamingListener(final String dataFilePath, final String errorFilePath) {
        setupDataOutputFile(dataFilePath);
        setupErrorOutputFile(errorFilePath);
    }

    /**
     * Writes the important status info to an output file.
     */
    @Override
    public void onStatus(Status status) {
        try {

            mBufferedDataWriter.write(status.getText()
                    .concat(DELIMITER)
                    .concat(status.getPlace() == null ? "" : status.getPlace().toString())
                    .concat(DELIMITER)
                    .concat(status.getCreatedAt().toString())
                    .concat(DELIMITER)
                    .concat(status.getIsoLanguageCode() == null ? "" : status.getIsoLanguageCode())
                    .concat(TWEET_DELIMITER)
                    .concat("\n")
            );

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Override
    public void onException(Exception e) {
        try {
            e.printStackTrace();
            mBufferedErrorWriter.write(e.getStackTrace().toString());
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        //Don't log. don't really care about this.
        System.out.println(statusDeletionNotice.toString());
    }

    @Override
    public void onTrackLimitationNotice(int i) {
        try {
            System.out.println(String.format("TRACK LIMITATION NOTICE: %2d", i));
            mBufferedErrorWriter.write(i);
        } catch (IOException ioEx) {

        }
    }

    @Override
    public void onScrubGeo(long l, long l2) {
        //No-op. Don't really care about this.
    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
        try {
            mBufferedErrorWriter.write(stallWarning.toString());
            System.out.println("STALL WARNING: " + stallWarning.toString());
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    /**
     * Method for setting up the output file. to be called in constructor, and as necessary on the file listener.
     * Does a number of checks because many fields will be null in constructor.
     *
     * @param fileName the file name including extension.
     */
    private synchronized void setupDataOutputFile(final String fileName) {
        try {


            mDataOutputFile = new File(fileName);
            mBufferedDataWriter = new BufferedWriter(new FileWriter(mDataOutputFile));

            if (mBaseFileName == null || mBaseFileName.isEmpty()) {
                mBaseFileName = FilenameUtils.getBaseName(mDataOutputFile.getName());
            }

            if (mBaseFileExtension == null || mBaseFileExtension.isEmpty()) {
                mBaseFileExtension = FilenameUtils.getExtension(mDataOutputFile.getName());
            }

            if (mDataDirectory == null) {
                mDataDirectory = mDataOutputFile.getParentFile();
            }

            if (!mDataOutputFile.exists()) {
                mDataOutputFile.createNewFile();
            }

        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private synchronized void setupErrorOutputFile(final String errorFilePath) {
        try {
            mErrorLogFile = new File(errorFilePath);
            if (!mErrorLogFile.exists()) {
                mErrorLogFile.createNewFile();
            }

            if (mErrorDirectory == null) {
                mErrorDirectory = mErrorLogFile.getParentFile();
            }


            mBufferedErrorWriter = new BufferedWriter(new FileWriter(mErrorLogFile));

        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

}
