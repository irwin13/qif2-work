package id.co.quadras.qif.dev;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 07/05/2014 17:41
 */
public final class GuiceQifDev {

    private static Injector injector;
    private static List<AbstractModule> moduleList;
    private GuiceQifDev() {
        injector = Guice.createInjector(getModuleList());
    }

    private List<AbstractModule> getModuleList() {
        if (moduleList == null) {
            moduleList = new LinkedList<AbstractModule>();
        }
        return moduleList;
    }

    public static void setModuleList(List<AbstractModule> moduleList) {
        GuiceQifDev.moduleList = moduleList;
    }

    public static Injector getInjector() {
        if (injector == null) {
            new GuiceQifDev();
        }
        return injector;
    }

}
