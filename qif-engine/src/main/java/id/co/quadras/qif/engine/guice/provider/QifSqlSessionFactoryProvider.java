package id.co.quadras.qif.engine.guice.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.sun.org.apache.xpath.internal.XPathAPI;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author irwin Timestamp : 06/06/2014 18:38
 */
public class QifSqlSessionFactoryProvider implements Provider<SqlSessionFactory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifSqlSessionFactoryProvider.class);

    private final String qifConfigFile;

    @Inject
    public QifSqlSessionFactoryProvider(@Named("qifConfigFile") String qifConfigFile) {
        this.qifConfigFile = qifConfigFile;
    }

    @Override
    public SqlSessionFactory get() {

        FileInputStream fisConfiguration = null;

        try {
            File fileConfig = new File(qifConfigFile);
            if (!(fileConfig.exists())) {
                throw new FileNotFoundException("FATAL ERROR : File " + qifConfigFile + " not found");
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            fisConfiguration = new FileInputStream(fileConfig);
            Document document = builder.parse(fisConfiguration);

            LOGGER.info("File {} loaded successfully", qifConfigFile);

            Node nodeJetty = XPathAPI.selectSingleNode(document, "qif-config//jetty");
            Node nodeConcurrency = XPathAPI.selectSingleNode(document, "qif-config//concurrency");

//            configMap.put(QifConstants.JETTY_PORT, getStringValue(nodeJetty, "@port"));
//
//            configMap.put(QifConstants.CONCURRENCY_PROVIDER_CLASS, getStringValue(nodeConcurrency, "@providerClass"));
//            configMap.put(QifConstants.CONCURRENCY_JDK_THREAD_POOL, getStringValue(nodeConcurrency, "@jdkThreadPool"));

        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getLocalizedMessage());
        } catch (SAXException e) {
            LOGGER.error(e.getLocalizedMessage());
        } catch (TransformerException e) {
            LOGGER.error(e.getLocalizedMessage());
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        } finally {
            try {
                if (fisConfiguration != null) fisConfiguration.close();
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        }

        DataSource dataSource = null;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return null;
    }

    private String getStringValue(Node node, String attribute) throws TransformerException {
        return XPathAPI.eval(node, attribute).toString().trim();
    }

}
