package id.co.quadras.adapter;

import id.co.quadras.qif.connector.adapter.FileAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.core.model.vo.adapter.AdapterFile;
import id.co.quadras.qif.core.model.vo.adapter.AdapterInterface;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 24/05/2014 20:59
 */
public class FileAdapterTest {

    private QifAdapter getQifAdapter() {
        QifAdapter qifAdapter = new QifAdapter();

        qifAdapter.setName("LOCAL_FILE");
        qifAdapter.setAdapterInterface(AdapterInterface.FILE.getName());
        List<QifAdapterProperty> propertyList = new LinkedList<QifAdapterProperty>();

        QifAdapterProperty property1 = new QifAdapterProperty();
        property1.setPropertyKey(AdapterFile.FOLDER.getName());
        property1.setPropertyValue("E:\\");
        propertyList.add(property1);

        qifAdapter.setQifAdapterPropertyList(propertyList);
        return qifAdapter;
    }

    @Test
    public void putFile() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getQifAdapter());
        fileAdapter.writeCharacter("fileAdapterTest.txt", "this is the content");
        File file = new File("E:\\fileAdapterTest.txt");
        Assert.assertTrue(file.exists());
    }

}
