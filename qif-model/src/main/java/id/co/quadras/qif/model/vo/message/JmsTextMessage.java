package id.co.quadras.qif.model.vo.message;

import com.google.common.base.Objects;

import java.util.Map;

/**
 * @author irwin Timestamp : 28/08/2014 17:30
 */
public class JmsTextMessage {

    private final String textContent;
    private final Map<String, Object> messageHeader;
    private int messagePriority = 4; // default

    public JmsTextMessage(String textContent, Map<String, Object> messageHeader) {
        this.textContent = textContent;
        this.messageHeader = messageHeader;
    }

    public String getTextContent() {
        return textContent;
    }

    public Map<String, Object> getMessageHeader() {
        return messageHeader;
    }

    public int getMessagePriority() {
        return messagePriority;
    }

    public void setMessagePriority(int messagePriority) {
        this.messagePriority = messagePriority;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("textContent", textContent)
                .add("messageHeader", messageHeader)
                .add("messagePriority", messagePriority)
                .toString();
    }
}
