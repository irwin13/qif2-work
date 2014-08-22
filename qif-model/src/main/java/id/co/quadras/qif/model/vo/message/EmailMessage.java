package id.co.quadras.qif.model.vo.message;

import com.irwin13.winwork.basic.utilities.PojoUtil;

import java.util.List;

/**
 * @author irwin Timestamp : 22/05/2014 18:54
 */
public class EmailMessage {

    private String subject;
    private String content;
    private String from;
    private List<String> toList;
    private List<String> ccList;
    private List<String> bccList;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public List<String> getCcList() {
        return ccList;
    }

    public void setCcList(List<String> ccList) {
        this.ccList = ccList;
    }

    public List<String> getBccList() {
        return bccList;
    }

    public void setBccList(List<String> bccList) {
        this.bccList = bccList;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}
