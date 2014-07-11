package id.co.quadras.qif.connector.event;

import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.event.EventFile;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import org.apache.commons.io.FileUtils;
import org.joda.time.Duration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author irwin Timestamp : 05/06/2014 14:31
 */
public abstract class BasicFileProcess extends QifProcess {

    @Override
    protected QifActivityMessage receiveEvent(QifEvent qifEvent, Object inputMessage) {
        return getFile(qifEvent);
    }

    private QifActivityMessage getFile(final QifEvent qifEvent) {
        QifActivityMessage qifActivityMessage = null;

        String deleteAfterRead = getPropertyValue(qifEvent, EventFile.DELETE_AFTER_READ.getName());
        String folderName = getPropertyValue(qifEvent, EventFile.FOLDER.getName());
        final String endWith = getPropertyValue(qifEvent, EventFile.END_WITH.getName());
        int maxFetch = Integer.valueOf(getPropertyValue(qifEvent, EventFile.MAX_FETCH.getName()));
        logger.debug("deleteAfterRead = {}", deleteAfterRead);
        logger.debug("folderName = {}", folderName);
        logger.debug("endWith = {}", endWith);
        logger.debug("maxFetch [DEPRECATED] = {}", maxFetch);
        maxFetch = 1; // always be 1

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

            Arrays.sort(all, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.valueOf(o1.lastModified())
                            .compareTo(o2.lastModified());
                }
            });

            if (files != null & files.length > 0) {
                try {
                    File file = files[0];
                    String fileContent = FileUtils.readFileToString(file);

                    Map<String, Object> messageHeader = new WeakHashMap<String, Object>();
                    messageHeader.put("fileLastModified", file.lastModified());
                    messageHeader.put("fileName", file.getName());
                    messageHeader.put("fileSize", file.length());

                    qifActivityMessage = new QifActivityMessage(fileContent.getBytes(), QifMessageType.TEXT, messageHeader);

                    if (Boolean.valueOf(deleteAfterRead)) {
                        file.delete();
                        logger.debug("delete file after read {}", file.getName());
                    }
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }

        return qifActivityMessage;
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
