package com.lnotes.twitterscotus.streaming;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.io.*;

/**
 * Implements the basic StreamingListener for the project.
 * At this time we're just using this to cache the data on disk, but could be something more sophisticated in the future.
 *
 * Needs to handle high-throughput stuff so we use a BufferedOutputStream to write to file.
 *
 * Created by LN_1 on 3/9/14.
 */
public class ScotusStreamingListener implements StatusListener {

    private File mDataOutputFile;
    private File mErrorLogFile;

    private FileOutputStream mDataOutputStream;
    private BufferedOutputStream mBufferedDataOutputStream;

    //TODO: implement error logging.
    private FileOutputStream mErrorOutputStream;
    private BufferedOutputStream mBufferedErrorOutputStream;

    public ScotusStreamingListener(final String dataFilePath, final String errorFilePath) {
        try {
            mDataOutputFile = new File(dataFilePath);

            mDataOutputStream = new FileOutputStream(mDataOutputFile);
            mBufferedDataOutputStream = new BufferedOutputStream(mDataOutputStream);

            mErrorLogFile = new File(errorFilePath);

            if (!mDataOutputFile.exists()) {
                mDataOutputFile.createNewFile();
            }

            if (!mErrorLogFile.exists()) {
                mErrorLogFile.createNewFile();
            }

        } catch(FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        } catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Override
    public void onStatus(Status status) {
        try {
        //TODO: write to a data file.
        byte[] bytes = status.toString().getBytes();

        mBufferedDataOutputStream.write(bytes);
        } catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Override
    public void onException(Exception e) {
        //TODO: log to a file.
        e.printStackTrace();
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        //TODO: log to a file.
        System.out.println(statusDeletionNotice.toString());
    }

    @Override
    public void onTrackLimitationNotice(int i) {
        //TODO: log to an error file.
        System.out.println(String.format("TRACK LIMITATION NOTICE: %2d", i));
    }

    @Override
    public void onScrubGeo(long l, long l2) {
        //TODO: log to a file.

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
        //TODO: log to an error file.
        System.out.println("STALL WARNING: " + stallWarning.toString());
    }
}
