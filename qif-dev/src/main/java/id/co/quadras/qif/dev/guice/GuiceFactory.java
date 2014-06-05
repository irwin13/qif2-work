package id.co.quadras.qif.dev.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import id.co.quadras.qif.core.QifProcess;
import id.co.quadras.qif.core.QifTask;
import id.co.quadras.qif.dev.guice.module.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 07/05/2014 17:41
 */
public final class GuiceFactory {

    private static Injector injector;
    private static List<AbstractModule> moduleList;

    private GuiceFactory() {
        injector = Guice.createInjector(getModuleList());
    }

    private List<AbstractModule> getModuleList() {
        if (moduleList == null) {
            moduleList = new LinkedList<AbstractModule>();
            moduleList.add(new SharedModule());
            moduleList.add(new DaoModule());
            moduleList.add(new ServiceModule());
            moduleList.add(new TaskModule(new HashSet<Class<? extends QifTask>>()));
            moduleList.add(new ProcessModule(new HashSet<Class<? extends QifProcess>>()));
        }
        return moduleList;
    }

    public static void setModuleList(List<AbstractModule> moduleList) {
        GuiceFactory.moduleList = moduleList;
    }

    public static Injector getInjector() {
        if (injector == null) {
            new GuiceFactory();
        }
        return injector;
    }
}
