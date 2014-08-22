package id.co.quadras.qif.engine.connector.event;

import com.google.common.base.Strings;
import id.co.quadras.qif.engine.QifException;
import id.co.quadras.qif.engine.core.QifEventHandler;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventFtp;
import id.co.quadras.qif.model.vo.message.FileMessage;
import org.apache.commons.net.ftp.*;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/06/2014 14:32
 */
public class FtpEventHandler extends QifEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpEventHandler.class);

    public FtpEventHandler(QifEvent qifEvent, Object eventMessage) {
        super(qifEvent, eventMessage);
    }

    public List<FileMessage> getFiles() {

        List<FileMessage> fileMessageList = new LinkedList<FileMessage>();

        String host = getPropertyValue(qifEvent, EventFtp.HOST.getName());
        int port = Integer.valueOf(getPropertyValue(qifEvent, EventFtp.PORT.getName()));
        String user = getPropertyValue(qifEvent, EventFtp.USER.getName());
        String password = getPropertyValue(qifEvent, EventFtp.PASSWORD.getName());
        LOGGER.debug("FTP host = {}", host);
        LOGGER.debug("FTP port = {}", port);
        LOGGER.debug("FTP user = {}", user);

        String deleteAfterRead = getPropertyValue(qifEvent, EventFtp.DELETE_AFTER_READ.getName());
        String folderName = getPropertyValue(qifEvent, EventFtp.FOLDER.getName());
        final String endWith = getPropertyValue(qifEvent, EventFtp.END_WITH.getName());
        int maxFetch = Integer.valueOf(getPropertyValue(qifEvent, EventFtp.MAX_FETCH.getName()));
        LOGGER.debug("FTP deleteAfterRead = {}", deleteAfterRead);
        LOGGER.debug("FTP folderName = {}", folderName);
        LOGGER.debug("FTP endWith = {}", endWith);
        LOGGER.debug("FTP maxFetch = {}", maxFetch);

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(host, port);
            ftpClient.login(user, password);

            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                throw new QifException("Fail to complete login process to FTP server");
            }

            FTPFile[] ftpFiles;
            FTPListParseEngine parseEngine;
            if (Strings.isNullOrEmpty(folderName)) {
                parseEngine = ftpClient.initiateListParsing();
                ftpFiles = parseEngine.getNext(maxFetch);
            } else {
                parseEngine = ftpClient.initiateListParsing(folderName);
                ftpFiles = parseEngine.getNext(maxFetch);
            }

            if (ftpFiles != null && ftpFiles.length > 0) {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                Arrays.sort(ftpFiles,  new Comparator<FTPFile>() {
                    @Override
                    public int compare(FTPFile o1, FTPFile o2) {
                        return Long.valueOf(o1.getTimestamp().getTimeInMillis())
                                .compareTo(o2.getTimestamp().getTimeInMillis());
                    }});

                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    LOGGER.debug("ftpFile name = {}", ftpFile.getName());
                    if (ftpFile.getName().endsWith(endWith) &&
                            isFileReady(ftpFile.getTimestamp().getTimeInMillis())) {

                        FileMessage fileMessage = new FileMessage();

                        fileMessage.setFileName(ftpFile.getName());
                        fileMessage.setFileSize(ftpFile.getSize());
                        fileMessage.setFileTimestamp(ftpFile.getTimestamp().getTime());

                        fileMessage.setQifEventId(qifEvent.getId());
                        fileMessage.setQifEventName(qifEvent.getName());
                        fileMessage.setQifEventInterface(qifEvent.getEventInterface());
                        fileMessage.setQifEventType(qifEvent.getEventType());

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ftpClient.retrieveFile(folderName + ftpFile.getName(), bos);
                        fileMessage.setFileContent(bos.toString());

                        LOGGER.debug("add fileMessage = {}", fileMessage);
                        fileMessageList.add(fileMessage);

                        if (Boolean.valueOf(deleteAfterRead)) {
                            ftpClient.deleteFile(folderName + ftpFile.getName());
                            LOGGER.debug("delete file after read {}", folderName + ftpFile.getName());
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new QifException("Error : " + e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
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
                EventFtp.LAST_MODIFIED_INTERVAL_SECONDS.getName()));
        long period = duration.getStandardSeconds();
        LOGGER.debug("lastModifiedIntervalSeconds = {} seconds", period);
        if (period > lastModifiedIntervalSeconds) {
            return true;
        } else {
            return false;
        }
    }

}
