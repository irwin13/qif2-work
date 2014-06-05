package id.co.quadras.qif.adapter;

import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.vo.adapter.AdapterEmail;
import id.co.quadras.qif.core.model.vo.message.EmailMessage;
import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;

/**
 * @author irwin Timestamp : 21/05/2014 18:38
 */
public class EmailAdapter extends AbstractAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAdapter.class);

    public EmailAdapter(QifAdapter qifAdapter) {
        super(qifAdapter);
    }

    public void sendEmail(EmailMessage emailMessage) {

        final Email email = new Email();

        email.setFromAddress(emailMessage.getFrom(), emailMessage.getFrom());
        email.setSubject(emailMessage.getSubject());
        email.setText(emailMessage.getContent());

        if (emailMessage.getToList() != null && !emailMessage.getToList().isEmpty()) {
            for (String to : emailMessage.getToList()) {
                email.addRecipient(to, to, Message.RecipientType.TO);
            }
        }

        if (emailMessage.getCcList() != null && !emailMessage.getCcList().isEmpty()) {
            for (String cc : emailMessage.getCcList()) {
                email.addRecipient(cc, cc, Message.RecipientType.CC);
            }
        }

        if (emailMessage.getBccList() != null && !emailMessage.getBccList().isEmpty()) {
            for (String bcc : emailMessage.getBccList()) {
                email.addRecipient(bcc, bcc, Message.RecipientType.BCC);
            }
        }

        String smtpHost = getPropertyValue(AdapterEmail.SMTP_HOST.getName());
        int smtpPort = Integer.valueOf(getPropertyValue(AdapterEmail.SMTP_PORT.getName()));
        String smtpUser = getPropertyValue(AdapterEmail.SMTP_USER.getName());
        String smtpPassword = getPropertyValue(AdapterEmail.SMTP_PASSWORD.getName());
        String smtpMethod = getPropertyValue(AdapterEmail.SMTP_METHOD.getName());
        TransportStrategy transportStrategy;

        if (smtpMethod.equalsIgnoreCase(SmtpMethod.PLAIN.getName())) {
            transportStrategy = TransportStrategy.SMTP_PLAIN;
        } else if (smtpMethod.equalsIgnoreCase(SmtpMethod.SSL.getName())) {
            transportStrategy = TransportStrategy.SMTP_SSL;
        } else if (smtpMethod.equalsIgnoreCase(SmtpMethod.TLS.getName())) {
            transportStrategy = TransportStrategy.SMTP_TLS;
        } else {
            transportStrategy = TransportStrategy.SMTP_PLAIN;
        }

        LOGGER.debug("smtpHost = {}", smtpHost);
        LOGGER.debug("smtpPort = {}", smtpPort);
        LOGGER.debug("smtpUser = {}", smtpUser);
        LOGGER.debug("smtpMethod = {}", smtpMethod);

        new Mailer(smtpHost, smtpPort, smtpUser, smtpPassword, transportStrategy)
                .sendMail(email);
    }

    public enum SmtpMethod {
        SSL("SSL"),
        TLS("TLS"),
        PLAIN("PLAIN");

        private final String name;

        SmtpMethod(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

    }
}
