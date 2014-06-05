package id.co.quadras.qif.adapter;

import com.google.common.base.Strings;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.adapter.AdapterFtp;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author irwin Timestamp : 21/05/2014 18:38
 */
public class FtpAdapter extends AbstractAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpAdapter.class);

    public FtpAdapter(QifAdapter qifAdapter) {
        super(qifAdapter);
    }

    private FTPClient ftpClient;

    public void connect() throws IOException {
        ftpClient = new FTPClient();
        String host = getPropertyValue(AdapterFtp.HOST.getName());
        int port = Integer.valueOf(getPropertyValue(AdapterFtp.PORT.getName()));
        String user = getPropertyValue(AdapterFtp.USER.getName());
        String password = getPropertyValue(AdapterFtp.PASSWORD.getName());
        LOGGER.debug("FTP host = {}", host);
        LOGGER.debug("FTP port = {}", port);
        LOGGER.debug("FTP user = {}", user);
        ftpClient.connect(host, port);
        ftpClient.login(user, password);
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            throw new QifException("Fail to complete login process to FTP server");
        }
    }

    public boolean storeFile(String fileName, InputStream file) throws IOException {
        boolean result;
        String folderName = getPropertyValue(AdapterFtp.FOLDER.getName());
        LOGGER.debug("fileName to save = {}", fileName);
        LOGGER.debug("folderName to save = {}", folderName);
        LOGGER.debug("inputStream = {}", file);

        if (Strings.isNullOrEmpty(folderName)) {
            result = ftpClient.storeFile(fileName, file);
        } else {
            ftpClient.makeDirectory(folderName);
            result = ftpClient.storeFile(folderName + "/" + fileName, file);
        }

        return result;
    }

    public String[] listFile() throws IOException {
        return ftpClient.listNames();
    }

    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
