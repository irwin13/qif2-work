package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.dev.process.FileJsonToFtp;
import id.co.quadras.qif.dev.process.FtpXmlToFile;

/**
 * @author irwin Timestamp : 12/05/2014 17:15
 */
public class ProcessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FileJsonToFtp.class);
        bind(FtpXmlToFile.class);
    }
}
