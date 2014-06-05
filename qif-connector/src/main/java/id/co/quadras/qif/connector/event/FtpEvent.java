package id.co.quadras.qif.connector.event;

import com.google.common.base.Strings;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.event.EventFtp;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.joda.time.Duration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/06/2014 14:32
 */
public abstract class FtpEvent extends QifProcess {

    @Override
    protected Object receiveEvent(QifEvent qifEvent, Object inputMessage) {
        List<String> stringList = getFiles(qifEvent);
        return (stringList.isEmpty()) ? null : stringList;
    }

    private List<String> getFiles(final QifEvent qifEvent) {
        List<String> result = new LinkedList<String>();

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
        logger.debug("FTP maxFetch = {}", maxFetch);

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(host, port);
            ftpClient.login(user, password);

            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                throw new QifException("Fail to complete login process to FTP server");
            }

            FTPFile[] ftpFiles;
            if (Strings.isNullOrEmpty(folderName)) {
                folderName = "";
                ftpFiles = ftpClient.listFiles();
            } else {
                ftpFiles = ftpClient.listFiles(folderName);
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            int fileCounter = 1;
            fileLoop:
            for (int i = 0; i < ftpFiles.length; i++) {
                if (fileCounter > maxFetch) {
                    break fileLoop;
                }
                FTPFile ftpFile = ftpFiles[i];
                logger.debug("ftpFile name = {}", ftpFile.getName());
                logger.debug("ftpFile size = {}", ftpFile.getSize());
                logger.debug("ftpFile timestamp = {}", ftpFile.getTimestamp().getTime());
                if (ftpFile.getName().endsWith(endWith) &&
                        isFileReady(qifEvent, ftpFile.getTimestamp().getTimeInMillis())) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ftpClient.retrieveFile(folderName + ftpFile.getName(), bos);
                    logger.debug("file content = {}", bos.toString());
                    result.add(bos.toString());
                    fileCounter++;
                    logger.debug("ftpFile fileCounter = {}", fileCounter);
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
        return result;
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
