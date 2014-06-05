package id.co.quadras.qif.dev.task;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import id.co.quadras.qif.core.QifTaskMessage;
import id.co.quadras.qif.dev.message.Book;
import id.co.quadras.qif.core.model.vo.QifActivityResult;

import java.io.IOException;

/**
 * @author irwin Timestamp : 25/05/2014 0:39
 */
public class JsonToXml extends AbstractTask {

    @Override
    protected QifActivityResult implementTask(QifTaskMessage qifTaskMessage) {
        String result = null;
        String json = (String) qifTaskMessage.getMessage();
        logger.debug("json input = {}", json);
        XmlMapper xmlMapper = new XmlMapper();

        try {
            Book book = jsonParser.parseToObject(false, json, Book.class);
            result = xmlMapper.writeValueAsString(book);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        logger.debug("xml output = {}", result);
        return new QifActivityResult(SUCCESS, result, null);
    }

    @Override
    public String activityName() {
        return getClass().getCanonicalName();
    }
}
