package id.co.quadras.qif.dev.process;

import id.co.quadras.qif.model.vo.QifActivityResult;

import java.util.List;

/**
 * @author irwin Timestamp : 25/05/2014 0:40
 */
public class FtpXmlToEmail extends FtpEventProcess {

    @Override
    protected QifActivityResult implementProcess(Object processInput) {
        List<String> bosList = (List<String>) processInput;
        for (String bos : bosList) {
            logger.debug(bos);
        }
        return new QifActivityResult(SUCCESS, null, null);
    }

    @Override
    public String activityName() {
        return getClass().getSimpleName();
    }
}
