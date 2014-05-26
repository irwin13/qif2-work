package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.QifProcess;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventFile;
import org.joda.time.Duration;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

/**
 * @author irwin Timestamp : 25/05/2014 0:55
 */
public abstract class FileProcess extends QifProcess {

    @Override
    protected Object receiveEvent(QifEvent qifEvent, Object inputMessage) {
        File[] fileArray = getFiles(qifEvent);
        return fileArray;
    }

    private File[] getFiles(final QifEvent qifEvent) {
        File[] files = null;
        String folderName = getPropertyValue(qifEvent, EventFile.FOLDER.getName());
        final String endWith = getPropertyValue(qifEvent, EventFile.END_WITH.getName());
        int maxFetch = Integer.valueOf(getPropertyValue(qifEvent, EventFile.MAX_FETCH.getName()));
        logger.debug("folderName to read = {}", folderName);
        logger.debug("endWith = {}", endWith);
        logger.debug("maxFetch = {}", maxFetch);

        File file = new File(folderName);

        if (file.exists() && file.isDirectory()) {
            File[] all = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    logger.debug("fileName = {}", file.getName());
                    if (endWith.equalsIgnoreCase("*") && isFileReady(qifEvent, file.lastModified())) {
                        return true;
                    } else if (file.getName().endsWith(endWith) && isFileReady(qifEvent, file.lastModified())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            if (all.length < maxFetch) {
                files = Arrays.copyOf(all, all.length, File[].class);
            } else {
                files = Arrays.copyOf(all, maxFetch, File[].class);
            }
        }
        return files;
    }

    private boolean isFileReady(QifEvent qifEvent, long fileLastModified) {
        Duration duration = new Duration(System.currentTimeMillis() - fileLastModified);
        long lastModifiedIntervalSeconds = Long.valueOf(getPropertyValue(qifEvent,
                EventFile.LAST_MODIFIED_INTERVAL_SECONDS.getName()));
        long period = duration.getStandardSeconds();
        logger.debug("lastModifiedIntervalSeconds = {} seconds", period);
        if (period > lastModifiedIntervalSeconds) {
            return true;
        } else {
            return false;
        }
    }
}
