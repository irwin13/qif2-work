package id.co.quadras.qif.connector.event;

import com.google.common.base.Strings;
import id.co.quadras.qif.core.QifActivityMessage;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.event.EventFtp;
import id.co.quadras.qif.core.model.vo.message.QifMessageType;
import org.apache.commons.net.ftp.*;
import org.joda.time.Duration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author irwin Timestamp : 05/06/2014 14:32
 */
public abstract class BasicFtpStringContentProcess extends QifProcess {

    @Override
    protected QifActivityMessage receiveEvent(QifEvent qifEvent, Object inputMessage, QifMessageType messageType) {
        return getFile(qifEvent);
    }

    private QifActivityMessage getFile(final QifEvent qifEvent) {

        QifActivityMessage qifActivityMessage = null;

        String host = getPropertyValue(qifEvent, EventFtp.HOST.getName());
        int port = Integer.valueOf(getPropertyValue(qifEvent, EventFtp.PORT.getName()));
        String user = getPropertyValue(qifEvent, EventFtp.USER.getName());
        String password = getPropertyValue(qifEvent, EventFtp.PASSWORD.getName());
        logger.debug("FTP host = {}", host);
        logger.debug("FTP port = {}", port);
        logger.debug("FTP user = {}", user);

        String deleteAfterRead = getPropertyValue(qifEvent, EventFtp.DELETE_AFTER_READ.getName());
        String folderName = getPropertyValue(qifEvent, EventFtp.FOLDER.getName());
        final String endWith = getPropertyValue(qifEvent, EventFtp.END_WITH.getName());
        int maxFetch = Integer.valueOf(getPropertyValue(qifEvent, EventFtp.MAX_FETCH.getName()));
        logger.debug("FTP deleteAfterRead = {}", deleteAfterRead);
        logger.debug("FTP folderName = {}", folderName);
        logger.debug("FTP endWith = {}", endWith);
        logger.debug("FTP maxFetch [DEPRECATED] = {}", maxFetch);

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

                FTPFile ftpFile = ftpFiles[0]; // just take 1 file at a time
                logger.debug("ftpFile name = {}", ftpFile.getName());
                logger.debug("ftpFile size = {}", ftpFile.getSize());
                logger.debug("ftpFile timestamp = {}", ftpFile.getTimestamp().getTime());
                if (ftpFile.getName().endsWith(endWith) &&
                        isFileReady(qifEvent, ftpFile.getTimestamp().getTimeInMillis())) {

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ftpClient.retrieveFile(folderName + ftpFile.getName(), bos);
                    logger.debug("file content = {}", bos.toString());

                    Map<String, Object> messageHeader = new WeakHashMap<String, Object>();
                    messageHeader.put("fileName", ftpFile.getName());
                    messageHeader.put("fileSize", ftpFile.getSize());
                    messageHeader.put("fileTimestamp", ftpFile.getTimestamp());

                    qifActivityMessage = new QifActivityMessage(bos.toString(), QifMessageType.STRING);
                    qifActivityMessage.setMessageHeader(messageHeader);

                    if (Boolean.valueOf(deleteAfterRead)) {
                        ftpClient.deleteFile(folderName + ftpFile.getName());
                        logger.debug("delete file after read {}", folderName + ftpFile.getName());
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new QifException("Error : " + e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
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
                EventFtp.LAST_MODIFIED_INTERVAL_SECONDS.getName()));
        long period = duration.getStandardSeconds();
        logger.debug("lastModifiedIntervalSeconds = {} seconds", period);
        if (period > lastModifiedIntervalSeconds) {
            return true;
        } else {
            return false;
        }
    }

}
