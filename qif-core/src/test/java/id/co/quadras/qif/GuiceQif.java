package id.co.quadras.qif;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 05/05/2014 15:57
 */
public class GuiceQif {

    private GuiceQif() {}
    private static Injector injector;

    private static List<AbstractModule> getModuleList() {
        List<AbstractModule> moduleList = new LinkedList<AbstractModule>();
        moduleList.add(new HelperModule());
        return moduleList;
    }

    public static Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(getModuleList());
        }
        return injector;
    }

}
