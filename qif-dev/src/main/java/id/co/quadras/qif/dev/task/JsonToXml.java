package id.co.quadras.qif.dev.task;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import id.co.quadras.qif.core.QifTaskMessage;
import id.co.quadras.qif.core.model.vo.QifActivityResult;
import id.co.quadras.qif.dev.message.Book;
import id.co.quadras.qif.engine.AbstractTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> mapData = new HashMap<String, String>();

        try {
            Book book = jsonParser.parseToObject(false, json, Book.class);
            mapData.put("author", book.getAuthor());
            mapData.put("title", book.getTitle());
            mapData.put("isbn", book.getIsbn());
            result = xmlMapper.writeValueAsString(book);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        logger.debug("xml output = {}", result);
        return new QifActivityResult(SUCCESS, result, mapData);
    }
}
