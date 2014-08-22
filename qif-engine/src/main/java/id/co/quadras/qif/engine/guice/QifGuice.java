package id.co.quadras.qif.engine.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.guice.module.*;
import id.co.quadras.qif.engine.process.ProcessRegister;
import id.co.quadras.qif.engine.task.TaskRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 07/05/2014 17:41
 */
public final class QifGuice {

    private static final Logger LOGGER = LoggerFactory.getLogger(QifGuice.class);

    private static Injector injector;

    private QifGuice(QifConfig qifConfig, List<AbstractModule> moduleList) {

        String nodeName = System.getProperty("nodeName");
        LOGGER.info("nodeName = {}", nodeName);
        MDC.put("nodeName", WinWorkUtil.getNodeName());

        LOGGER.info("=== QIF Config ===");
        LOGGER.info(qifConfig.toString());

        LOGGER.info("=== Starting scan Process and Task package ... ===");
        LOGGER.info("rootPackageProcess = {}", qifConfig.getRootPackage().getProcess());
        LOGGER.info("rootPackageTask = {}", qifConfig.getRootPackage().getTask());
        TaskRegister.init(qifConfig.getRootPackage().getTask());
        ProcessRegister.init(qifConfig.getRootPackage().getProcess());
        LOGGER.info("=== Starting scan Process and Task package complete ===");

        LOGGER.info("=== Starting Guice ... ===");
        List<AbstractModule> internalModule = new LinkedList<AbstractModule>();
        internalModule.add(new QifSharedModule(qifConfig));
        internalModule.add(new DaoModule());
        internalModule.add(new ServiceModule());
        internalModule.add(new TaskModule(TaskRegister.getTaskSet()));
        internalModule.add(new ProcessModule(ProcessRegister.getProcessSet()));

        if (moduleList != null) {
            for (AbstractModule module : moduleList) {
                internalModule.add(module);
            }
        }
        injector = Guice.createInjector(internalModule);
        LOGGER.info("=== Starting Guice complete ===");
    }

    public static void initEngine(QifConfig qifConfig, List<AbstractModule> moduleList) {
        if (injector == null) {
            new QifGuice(qifConfig, moduleList);
        }
    }

    public static Injector getInjector() {
        return injector;
    }
}
