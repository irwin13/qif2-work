package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.dev.task.JsonToXml;
import id.co.quadras.qif.dev.task.PutToFtp;
import id.co.quadras.qif.dev.task.WriteToFile;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class TaskModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JsonToXml.class);
        bind(PutToFtp.class);
        bind(WriteToFile.class);
    }
}
