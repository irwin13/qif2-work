package id.co.quadras.qif.engine.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.core.exception.QifException;
import id.co.quadras.qif.engine.ProcessRegister;
import id.co.quadras.qif.engine.TaskRegister;
import id.co.quadras.qif.engine.guice.module.*;
import id.co.quadras.qif.engine.jaxb.Qif;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 07/05/2014 17:41
 */
public final class EngineFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EngineFactory.class);

    private static Injector injector;

    private EngineFactory(String qifConfigFile) {

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        Qif qif = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Qif.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            qif = (Qif) unmarshaller.unmarshal(new File(qifConfigFile));
        } catch (JAXBException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        if (qif == null) {
            throw new QifException("FATAL : Failed to read QIF xml configuration file " + qifConfigFile);
        }

        LOGGER.info("=== Success load {} file ===", qifConfigFile);
        LOGGER.info("=== QIF Config ===");
        LOGGER.info(qif.toString());

        LOGGER.info("=== Starting scan Process and Task package ... ===");
        LOGGER.info("rootPackageProcess = {}", qif.getRootPackage().getProcessRootPackage());
        LOGGER.info("rootPackageTask = {}", qif.getRootPackage().getTaskRootPackage());
        TaskRegister.init(qif.getRootPackage().getTaskRootPackage());
        ProcessRegister.init(qif.getRootPackage().getProcessRootPackage());
        LOGGER.info("=== Starting scan Process and Task package complete ===");

        LOGGER.info("=== Starting Guice ... ===");
        List<AbstractModule> moduleList = new LinkedList<AbstractModule>();
        moduleList.add(new SharedModule(qif));
        moduleList.add(new DaoModule());
        moduleList.add(new ServiceModule());
        moduleList.add(new TaskModule(TaskRegister.getTaskSet()));
        moduleList.add(new ProcessModule(ProcessRegister.getProcessSet()));

        injector = Guice.createInjector(moduleList);
        LOGGER.info("=== Starting Guice complete ===");
    }

    public static void initEngine(String qifConfigFile) {
        if (injector == null) {
            new EngineFactory(qifConfigFile);
        }
    }

    public static Injector getInjector() {
        return injector;
    }
}
