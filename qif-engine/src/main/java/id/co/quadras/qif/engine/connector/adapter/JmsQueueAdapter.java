package id.co.quadras.qif.engine.connector.adapter;

import com.google.common.base.Preconditions;
import id.co.quadras.qif.engine.core.SingletonAdapter;
import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.vo.adapter.AdapterJmsQueue;
import id.co.quadras.qif.model.vo.message.JmsTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author irwin Timestamp : 28/08/2014 14:52
 */
public class JmsQueueAdapter extends SingletonAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsQueueAdapter.class);

    private static final String CONNECTION = "CONNECTION";
    private static final String SESSION = "SESSION";
    private static final String MESSAGE_PRODUCER = "MESSAGE_PRODUCER";

    public JmsQueueAdapter(QifAdapter qifAdapter) throws Exception {
        super(qifAdapter);
    }

    private static final ConcurrentHashMap<String, Object> CACHE = new ConcurrentHashMap<String, Object>();

    @Override
    protected void initSingletonObjects() throws Exception {
        Date lastUpdate = (Date) CACHE.get(qifAdapter.getName() + SEPARATOR + LAST_UPDATE);

        if (lastUpdate == null || !lastUpdate.equals(qifAdapter.getLastUpdateDate())) {
            cleanSingletonObjects();

            LOGGER.debug("initSingletonObjects");

            LOGGER.debug("INITIAL_CONTEXT_FACTORY = {}", getPropertyValue(AdapterJmsQueue.INITIAL_CONTEXT_FACTORY.getName()));
            LOGGER.debug("JNDI_URL_PROVIDER = {}", getPropertyValue(AdapterJmsQueue.JNDI_URL_PROVIDER.getName()));
            LOGGER.debug("JNDI_USER = {}", getPropertyValue(AdapterJmsQueue.JNDI_USER.getName()));
            LOGGER.debug("JNDI_CONNECTION_FACTORY = {}", getPropertyValue(AdapterJmsQueue.JNDI_CONNECTION_FACTORY.getName()));
            LOGGER.debug("JNDI_QUEUE = {}", getPropertyValue(AdapterJmsQueue.JNDI_QUEUE.getName()));

            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, getPropertyValue(AdapterJmsQueue.INITIAL_CONTEXT_FACTORY.getName()));
            env.put(Context.PROVIDER_URL, getPropertyValue(AdapterJmsQueue.JNDI_URL_PROVIDER.getName()));
            env.put(Context.SECURITY_PRINCIPAL, getPropertyValue(AdapterJmsQueue.JNDI_USER.getName()));
            env.put(Context.SECURITY_CREDENTIALS, getPropertyValue(AdapterJmsQueue.JNDI_PASSWORD.getName()));
            Context context = new InitialContext(env);

            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(getPropertyValue(AdapterJmsQueue.JNDI_CONNECTION_FACTORY.getName()));
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) context.lookup(getPropertyValue(AdapterJmsQueue.JNDI_QUEUE.getName()));

            MessageProducer messageProducer = session.createProducer(queue);
            connection.start();

            CACHE.put(qifAdapter.getName() + SEPARATOR + CONNECTION, connection);
            LOGGER.debug("put to cache = {}", qifAdapter.getName() + SEPARATOR + CONNECTION);

            CACHE.put(qifAdapter.getName() + SEPARATOR + SESSION, session);
            LOGGER.debug("put to cache = {}", qifAdapter.getName() + SEPARATOR + SESSION);

            CACHE.put(qifAdapter.getName() + SEPARATOR + MESSAGE_PRODUCER, messageProducer);
            LOGGER.debug("put to cache = {}", qifAdapter.getName() + SEPARATOR + MESSAGE_PRODUCER);

            CACHE.put(qifAdapter.getName() + SEPARATOR + LAST_UPDATE, qifAdapter.getLastUpdateDate());
        }
    }

    @Override
    protected void cleanSingletonObjects() throws Exception {
        LOGGER.debug("cleanSingletonObjects");

        MessageProducer messageProducer = (MessageProducer) CACHE.get(qifAdapter.getName() + SEPARATOR + MESSAGE_PRODUCER);
        if (messageProducer != null) {
            messageProducer.close();
        }
        CACHE.remove(qifAdapter.getName() + SEPARATOR + MESSAGE_PRODUCER);
        LOGGER.debug("removed from cache = {}", qifAdapter.getName() + SEPARATOR + MESSAGE_PRODUCER);

        Session session = (Session) CACHE.get(qifAdapter.getName() + SEPARATOR + SESSION);
        if (session != null) {
            session.close();
        }
        CACHE.remove(qifAdapter.getName() + SEPARATOR + SESSION);
        LOGGER.debug("removed from cache = {}", qifAdapter.getName() + SEPARATOR + SESSION);

        Connection connection = (Connection) CACHE.get(qifAdapter.getName() + SEPARATOR + CONNECTION);
        if (messageProducer != null) {
            connection.stop();
            connection.close();
        }
        CACHE.remove(qifAdapter.getName() + SEPARATOR + CONNECTION);
        LOGGER.debug("removed from cache = {}", qifAdapter.getName() + SEPARATOR + CONNECTION);

        CACHE.remove(qifAdapter.getName() + SEPARATOR + LAST_UPDATE);
        LOGGER.debug("removed from cache = {}", qifAdapter.getName() + SEPARATOR + LAST_UPDATE);
    }

    public void putMessage(List<JmsTextMessage> textMessageList) throws Exception {
        MessageProducer messageProducer = (MessageProducer) CACHE.get(qifAdapter.getName() + SEPARATOR + MESSAGE_PRODUCER);
        Session session = (Session) CACHE.get(qifAdapter.getName() + SEPARATOR + SESSION);

        Preconditions.checkNotNull(textMessageList);
        if (messageProducer == null || session == null) {
            initSingletonObjects();
        }

        for (JmsTextMessage jmsTextMessage : textMessageList) {
            messageProducer.setPriority(jmsTextMessage.getMessagePriority());

            TextMessage textMessage = session.createTextMessage();
            if (jmsTextMessage.getMessageHeader() != null) {
                for (Map.Entry<String, Object> entry : jmsTextMessage.getMessageHeader().entrySet()) {
                    textMessage.setObjectProperty(entry.getKey(), entry.getValue());
                    LOGGER.debug("set JMS header {} = {}", entry.getKey(), entry.getValue());
                }
            }

            LOGGER.debug("put content JMS {}", jmsTextMessage.getTextContent());
            textMessage.setText(jmsTextMessage.getTextContent());
            messageProducer.send(textMessage);
        }
    }
}
