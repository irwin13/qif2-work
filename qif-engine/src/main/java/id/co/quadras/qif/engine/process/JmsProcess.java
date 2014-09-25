package id.co.quadras.qif.engine.process;

import com.google.common.base.Strings;
import id.co.quadras.qif.engine.QifEngineApplication;
import id.co.quadras.qif.engine.core.QifActivityMessage;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.model.vo.event.EventJms;
import id.co.quadras.qif.model.vo.message.QifMessageType;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author irwin Timestamp : 19/08/2014 20:50
 */
public abstract class JmsProcess extends DaemonProcess {

    @Override
    public Runnable createDaemon(final QifEvent qifEvent) throws Exception {

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, getPropertyValue(qifEvent, EventJms.INITIAL_CONTEXT_FACTORY.getName()));
        env.put(Context.PROVIDER_URL, getPropertyValue(qifEvent, EventJms.JNDI_URL_PROVIDER.getName()));
        env.put(Context.SECURITY_PRINCIPAL, getPropertyValue(qifEvent, EventJms.JNDI_USER.getName()));
        env.put(Context.SECURITY_CREDENTIALS, getPropertyValue(qifEvent, EventJms.JNDI_PASSWORD.getName()));

        logger.info("JmsProcess Properties : ");
        logger.info("INITIAL_CONTEXT_FACTORY = {}", getPropertyValue(qifEvent, EventJms.INITIAL_CONTEXT_FACTORY.getName()));
        logger.info("PROVIDER_URL = {}", getPropertyValue(qifEvent, EventJms.JNDI_URL_PROVIDER.getName()));
        logger.info("SECURITY_PRINCIPAL = {}", getPropertyValue(qifEvent, EventJms.JNDI_USER.getName()));

        final Context context = new InitialContext(env);
        logger.info("JNDI_CONNECTION_FACTORY = {}", getPropertyValue(qifEvent, EventJms.JNDI_CONNECTION_FACTORY.getName()));
        final ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(getPropertyValue(qifEvent, EventJms.JNDI_CONNECTION_FACTORY.getName()));

        return new Runnable() {

            @Override
            public void run() {

                try {
                    Connection connection = connectionFactory.createConnection();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                    logger.info("JNDI_DESTINATION = {}", getPropertyValue(qifEvent, EventJms.JNDI_DESTINATION.getName()));
                    Destination destination = (Destination) context.lookup(getPropertyValue(qifEvent, EventJms.JNDI_DESTINATION.getName()));

                    logger.info("MESSAGE_SELECTOR = {}", getPropertyValue(qifEvent, EventJms.MESSAGE_SELECTOR.getName()));
                    String messageSelector = getPropertyValue(qifEvent, EventJms.MESSAGE_SELECTOR.getName());
                    MessageConsumer consumer;
                    if (Strings.isNullOrEmpty(messageSelector)) {
                        consumer = session.createConsumer(destination);
                    } else {
                        consumer = session.createConsumer(destination, messageSelector);
                    }

                    connection.start();

                    while (QifEngineApplication.isActive()) {
                        try {
                            TextMessage textMessage = (TextMessage) consumer.receiveNoWait();
                            if (textMessage != null && !Strings.isNullOrEmpty(textMessage.getText())) {
                                QifActivityMessage qifActivityMessage = new QifActivityMessage(textMessage.getText(), QifMessageType.STRING);

                                Map<String, Object> messageHeader = new HashMap<String, Object>();

                                messageHeader.put("JMSCorrelationID", textMessage.getJMSCorrelationID());
                                messageHeader.put("JMSDeliveryMode", textMessage.getJMSDeliveryMode());
                                messageHeader.put("JMSExpiration", textMessage.getJMSExpiration());
                                messageHeader.put("JMSMessageID", textMessage.getJMSMessageID());
                                messageHeader.put("JMSPriority", textMessage.getJMSPriority());
                                messageHeader.put("JMSRedelivered", textMessage.getJMSRedelivered());
                                messageHeader.put("JMSTimestamp", textMessage.getJMSTimestamp());
                                messageHeader.put("JMSType", textMessage.getJMSType());

                                Enumeration enumeration = textMessage.getPropertyNames();
                                while (enumeration.hasMoreElements()) {
                                    String propertyName = (String) enumeration.nextElement();
                                    Object object = textMessage.getObjectProperty(propertyName);
                                    messageHeader.put(propertyName, object);
                                }

                                qifActivityMessage.setMessageHeader(messageHeader);
                                logger.debug("execute process with message header = {}", messageHeader);
                                logger.debug("execute process with message content = {}", textMessage.getText());

                                executeProcess(qifEvent, qifActivityMessage);
                            } else {
                                // if no message, sleep for 10 seconds
                                logger.info("No message, sleep for 10 seconds");
                                Thread.sleep(10000);
                            }
                        } catch (Exception e) {
                            logger.error(e.getLocalizedMessage(), e);
                        }
                    }

                    logger.info("Exit loop forever");
                    if (consumer != null) consumer.close();
                    logger.info("Close JMS MessageConsumer");
                    if (session != null) session.close();
                    logger.info("Close JMS Session");

                    connection.stop();
                    logger.info("Stop JMS Connection");
                    connection.close();
                    logger.info("Close JMS Connection");
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        };
    }

}
