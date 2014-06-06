package id.co.quadras.qif.process;

import com.irwin13.winwork.basic.config.WinWorkConfig;
import com.irwin13.winwork.basic.test.DbUnitCleanInsert;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.dev.process.FtpXmlToFile;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.engine.guice.GuiceFactory;
import id.co.quadras.qif.engine.service.EventService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author irwin Timestamp : 02/06/2014 14:28
 */
public class FtpXmlToFileTest extends DbUnitCleanInsert {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileJsonToFtpTest.class);

    @Override
    public void initTestClass() {
        GuiceFactory.getInjector();
    }

    @Override
    public List<String> datasetList() {
        return Arrays.asList("dataset/QifEvent.xml", "dataset/QifAdapter.xml");
    }

    @Override
    public String jdbcDriver() {
        return GuiceFactory.getInjector().getInstance(WinWorkConfig.class).getString("test.database.driver");
    }

    @Override
    public String jdbcUrl() {
        return GuiceFactory.getInjector().getInstance(WinWorkConfig.class).getString("test.database.url");
    }

    @Override
    public String jdbcUser() {
        return GuiceFactory.getInjector().getInstance(WinWorkConfig.class).getString("test.database.username");
    }

    @Override
    public String jdbcPassword() {
        return GuiceFactory.getInjector().getInstance(WinWorkConfig.class).getString("test.database.password");
    }

    @Test
    public void runProcess() throws Exception {
        QifProcess qifProcess = GuiceFactory.getInjector().getInstance(FtpXmlToFile.class);
        EventService eventService = GuiceFactory.getInjector().getInstance(EventService.class);
        QifEvent qifEvent = eventService.selectById("f9bd1632beb04e498f915a0975ae4832");
        LOGGER.debug("qifEvent = {}", qifEvent);
        QifActivityResult result = qifProcess.executeProcess(qifEvent, null, null);
        Assert.assertNotNull(result);
    }
}
