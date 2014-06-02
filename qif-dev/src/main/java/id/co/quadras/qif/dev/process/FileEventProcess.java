package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.QifProcess;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventFile;
import org.apache.commons.io.FileUtils;
import org.joda.time.Duration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 25/05/2014 0:55
 */
public abstract class FileEventProcess extends QifProcess {

    @Override
    protected Object receiveEvent(QifEvent qifEvent, Object inputMessage) {
        List<String> fileContentList = getFiles(qifEvent);
        return fileContentList;
    }

    private List<String> getFiles(final QifEvent qifEvent) {
        List<String> result = new LinkedList<String>();
        String deleteAfterRead = getPropertyValue(qifEvent, EventFile.DELETE_AFTER_READ.getName());
        String folderName = getPropertyValue(qifEvent, EventFile.FOLDER.getName());
        final String endWith = getPropertyValue(qifEvent, EventFile.END_WITH.getName());
        int maxFetch = Integer.valueOf(getPropertyValue(qifEvent, EventFile.MAX_FETCH.getName()));
        logger.debug("deleteAfterRead = {}", deleteAfterRead);
        logger.debug("folderName = {}", folderName);
        logger.debug("endWith = {}", endWith);
        logger.debug("maxFetch = {}", maxFetch);

        File folder = new File(folderName);
        File[] files;

        if (folder.exists() && folder.isDirectory()) {
            File[] all = folder.listFiles(new FileFilter() {
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

            try {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    String fileContent = FileUtils.readFileToString(file);
                    result.add(fileContent);
                    if (Boolean.valueOf(deleteAfterRead)) {
                        file.delete();
                        logger.debug("delete file after read {}", file.getName());
                    }
                }
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }

        return result;
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
