package id.co.quadras.qif.connector.event;

import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.event.EventFile;
import org.apache.commons.io.FileUtils;
import org.joda.time.Duration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;

/**
 * @author irwin Timestamp : 05/06/2014 14:31
 */
public abstract class BasicFileProcess extends QifProcess {

    @Override
    protected Object receiveEvent(QifEvent qifEvent, Object inputMessage) {
        List<Map<String, String>> fileList = getFiles(qifEvent);
        return (fileList.isEmpty()) ? null : fileList;
    }

    private List<Map<String, String>> getFiles(final QifEvent qifEvent) {
        List<Map<String, String>> fileList = new LinkedList<Map<String, String>>();

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

                    Map<String, String> fileMap = new WeakHashMap<String, String>();
                    fileMap.put("fileContent", fileContent);
                    fileMap.put("fileName", file.getName());
                    fileMap.put("fileSize", String.valueOf(file.length()));

                    fileList.add(fileMap);

                    if (Boolean.valueOf(deleteAfterRead)) {
                        file.delete();
                        logger.debug("delete file after read {}", file.getName());
                    }
                }
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }

        return fileList;
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
