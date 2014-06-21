package id.co.quadras.qif.ui.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.irwin13.winwork.basic.log.LogUtil;
import id.co.quadras.qif.ui.guice.module.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 23/09/13 17:36
 */
public final class GuiceQifUi {

    private static Injector injector;

    private GuiceQifUi() {}

    private static List<AbstractModule> getModuleList() {
        List<AbstractModule> moduleList = new LinkedList<AbstractModule>();
        moduleList.add(new SharedModule());
        moduleList.add(new DaoModule());
        moduleList.add(new ServiceModule());
        moduleList.add(new WebModule());
        moduleList.add(new ValidatorModule());
        return moduleList;
    }

    public static Injector getInjector() {
        LogUtil.setNodeNameMDC();
        if (injector == null) {
            injector = Guice.createInjector(getModuleList());
        }
        return injector;
    }

}
