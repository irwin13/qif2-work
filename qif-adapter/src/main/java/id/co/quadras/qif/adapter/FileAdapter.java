package id.co.quadras.qif.adapter;

import com.google.common.base.Strings;
import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.vo.adapter.AdapterFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author irwin Timestamp : 21/05/2014 18:38
 */
public class FileAdapter extends AbstractAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileAdapter.class);

    public FileAdapter(QifAdapter qifAdapter) {
        super(qifAdapter);
    }

    public void writeCharacter(String fileName, String content) throws IOException {
        FileWriter fileWriter = null;

        String fullPath;
        if (!Strings.isNullOrEmpty(getPropertyValue(AdapterFile.FOLDER.getName()))) {
            fullPath = getPropertyValue(AdapterFile.FOLDER.getName()) + File.separatorChar + fileName;
        } else {
            fullPath = fileName;
        }

        LOGGER.debug("fileName = {}", fileName);
        LOGGER.debug("fullPath = {}", fullPath);
        LOGGER.debug("content = {}", content);

        try {
            fileWriter = new FileWriter(fullPath);
            fileWriter.write(content);
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
}
