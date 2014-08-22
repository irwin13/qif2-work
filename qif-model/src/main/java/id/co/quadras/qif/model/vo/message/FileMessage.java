package id.co.quadras.qif.model.vo.message;

import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.Date;

/**
 * @author irwin Timestamp : 06/08/2014 12:47
 */
public class FileMessage {

    private String fileName;
    private long fileSize;
    private Date fileTimestamp;
    private String fileContent;

    private String qifEventId;
    private String qifEventName;
    private String qifEventInterface;
    private String qifEventType;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getFileTimestamp() {
        return fileTimestamp;
    }

    public void setFileTimestamp(Date fileTimestamp) {
        this.fileTimestamp = fileTimestamp;
    }

    public String getQifEventId() {
        return qifEventId;
    }

    public void setQifEventId(String qifEventId) {
        this.qifEventId = qifEventId;
    }

    public String getQifEventName() {
        return qifEventName;
    }

    public void setQifEventName(String qifEventName) {
        this.qifEventName = qifEventName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getQifEventInterface() {
        return qifEventInterface;
    }

    public void setQifEventInterface(String qifEventInterface) {
        this.qifEventInterface = qifEventInterface;
    }

    public String getQifEventType() {
        return qifEventType;
    }

    public void setQifEventType(String qifEventType) {
        this.qifEventType = qifEventType;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
