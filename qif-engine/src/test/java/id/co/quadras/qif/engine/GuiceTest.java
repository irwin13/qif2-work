package id.co.quadras.qif.engine;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import id.co.quadras.qif.engine.module.TestModule;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 22/08/2014 14:50
 */
public class GuiceTest {

    private GuiceTest() {}
    private static Injector injector;

    private static List<AbstractModule> getModuleList() {
        List<AbstractModule> moduleList = new LinkedList<AbstractModule>();
        moduleList.add(new TestModule());
        return moduleList;
    }

    public static Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(getModuleList());
        }
        return injector;
    }

}
