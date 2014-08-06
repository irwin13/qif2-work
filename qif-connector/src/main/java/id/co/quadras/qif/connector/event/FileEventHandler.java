package id.co.quadras.qif.connector.event;

import id.co.quadras.qif.core.QifEventHandler;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.event.EventFile;
import id.co.quadras.qif.core.model.vo.message.FileMessage;
import org.apache.commons.io.FileUtils;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;

/**
 * @author irwin Timestamp : 05/06/2014 14:31
 */
public class FileEventHandler extends QifEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileEventHandler.class);

    public FileEventHandler(QifEvent qifEvent, Object eventMessage) {
        super(qifEvent, eventMessage);
    }

    public List<FileMessage> getFiles() {
        List<FileMessage> fileMessageList = new LinkedList<FileMessage>();

        String deleteAfterRead = getPropertyValue(qifEvent, EventFile.DELETE_AFTER_READ.getName());
        String folderName = getPropertyValue(qifEvent, EventFile.FOLDER.getName());
        final String endWith = getPropertyValue(qifEvent, EventFile.END_WITH.getName());
        int maxFetch = Integer.valueOf(getPropertyValue(qifEvent, EventFile.MAX_FETCH.getName()));
        LOGGER.debug("deleteAfterRead = {}", deleteAfterRead);
        LOGGER.debug("folderName = {}", folderName);
        LOGGER.debug("endWith = {}", endWith);
        LOGGER.debug("maxFetch  = {}", maxFetch);

        File folder = new File(folderName);
        File[] files;

        if (folder.exists() && folder.isDirectory()) {
            File[] all = folder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    LOGGER.debug("fileName = {}", file.getName());
                    if (endWith.equalsIgnoreCase("*") && isFileReady(file.lastModified())) {
                        return true;
                    } else if (file.getName().endsWith(endWith) && isFileReady(file.lastModified())) {
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
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        LOGGER.debug("process file {}", file.getName());
                        FileMessage fileMessage = new FileMessage();

                        fileMessage.setFileName(file.getName());
                        fileMessage.setFileSize(file.length());
                        fileMessage.setFileTimestamp(new Date(file.lastModified()));

                        fileMessage.setQifEventId(qifEvent.getId());
                        fileMessage.setQifEventName(qifEvent.getName());
                        fileMessage.setQifEventInterface(qifEvent.getEventInterface());
                        fileMessage.setQifEventType(qifEvent.getEventType());

                        fileMessage.setFileContent(FileUtils.readFileToString(file));

                        LOGGER.debug("add fileMessage = {}", fileMessage);
                        fileMessageList.add(fileMessage);
                        if (Boolean.valueOf(deleteAfterRead)) {
                            file.delete();
                            LOGGER.debug("delete file after read {}", file.getName());
                        }
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            }
        }

        return fileMessageList;
    }

    private boolean isFileReady(long fileLastModified) {
        Duration duration = new Duration(System.currentTimeMillis() - fileLastModified);
        long lastModifiedIntervalSeconds = Long.valueOf(getPropertyValue(qifEvent,
                EventFile.LAST_MODIFIED_INTERVAL_SECONDS.getName()));
        long period = duration.getStandardSeconds();
        LOGGER.debug("lastModifiedIntervalSeconds = {} seconds", period);
        if (period > lastModifiedIntervalSeconds) {
            return true;
        } else {
            return false;
        }
    }
}
