package id.co.quadras.adapter;

import id.co.quadras.qif.connector.adapter.FtpAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifAdapterProperty;
import id.co.quadras.qif.core.model.vo.adapter.AdapterFtp;
import id.co.quadras.qif.core.model.vo.adapter.AdapterInterface;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 24/05/2014 20:59
 */
public class FtpAdapterTest {

    private QifAdapter getQifAdapter() {
        QifAdapter qifAdapter = new QifAdapter();

        qifAdapter.setName("IRWIN_FTP");
        qifAdapter.setAdapterInterface(AdapterInterface.FTP.getName());
        List<QifAdapterProperty> propertyList = new LinkedList<QifAdapterProperty>();

        QifAdapterProperty property1 = new QifAdapterProperty();
        property1.setPropertyKey(AdapterFtp.HOST.getName());
        property1.setPropertyValue("localhost");
        propertyList.add(property1);

        QifAdapterProperty property2 = new QifAdapterProperty();
        property2.setPropertyKey(AdapterFtp.PORT.getName());
        property2.setPropertyValue("21");
        propertyList.add(property2);

        QifAdapterProperty property3 = new QifAdapterProperty();
        property3.setPropertyKey(AdapterFtp.USER.getName());
        property3.setPropertyValue("irwin");
        propertyList.add(property3);

        QifAdapterProperty property4 = new QifAdapterProperty();
        property4.setPropertyKey(AdapterFtp.PASSWORD.getName());
        property4.setPropertyValue("irwin");
        propertyList.add(property4);

        qifAdapter.setQifAdapterPropertyList(propertyList);
        return qifAdapter;
    }

    @Test
    public void putFile() throws IOException {
        FtpAdapter ftpAdapter = new FtpAdapter(getQifAdapter());
        ftpAdapter.connect();
        ftpAdapter.storeFile("qif2.txt", new FileInputStream("E:\\PindahHeader.txt"));
        boolean fileExists = false;
        String[] listFile = ftpAdapter.listFile();
        for (int i = 0; i < listFile.length; i++) {
            String fileName = listFile[i];
            if ("qif2.txt".equals(fileName)) {
                fileExists = true;
            }
        }
        ftpAdapter.disconnect();
        Assert.assertTrue(fileExists);
    }
}
