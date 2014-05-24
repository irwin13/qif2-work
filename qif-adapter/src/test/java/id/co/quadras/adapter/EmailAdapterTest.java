package id.co.quadras.adapter;

import id.co.quadras.qif.adapter.EmailAdapter;
import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.entity.QifAdapterProperty;
import id.co.quadras.qif.model.vo.adapter.AdapterEmail;
import id.co.quadras.qif.model.vo.adapter.AdapterInterface;
import id.co.quadras.qif.model.vo.message.EmailMessage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author irwin Timestamp : 24/05/2014 20:59
 */
public class EmailAdapterTest {

    private QifAdapter getQifAdapter() {
        QifAdapter qifAdapter = new QifAdapter();

        qifAdapter.setName("EMAIL_TEST");
        qifAdapter.setAdapterInterface(AdapterInterface.EMAIL.getName());
        List<QifAdapterProperty> propertyList = new LinkedList<QifAdapterProperty>();

        QifAdapterProperty property1 = new QifAdapterProperty();
        property1.setPropertyKey(AdapterEmail.SMTP_HOST.getName());
        property1.setPropertyValue("74.125.25.109");
        //property1.setPropertyValue("smtp.gmail.com");
        propertyList.add(property1);

        QifAdapterProperty property2 = new QifAdapterProperty();
        property2.setPropertyKey(AdapterEmail.SMTP_PORT.getName());
        property2.setPropertyValue("465");
        propertyList.add(property2);

        QifAdapterProperty property3 = new QifAdapterProperty();
        property3.setPropertyKey(AdapterEmail.SMTP_USER.getName());
        property3.setPropertyValue("siak.konsolidasi@gmail.com");
        propertyList.add(property3);

        QifAdapterProperty property4 = new QifAdapterProperty();
        property4.setPropertyKey(AdapterEmail.SMTP_PASSWORD.getName());
        property4.setPropertyValue("multi123");
        propertyList.add(property4);

        QifAdapterProperty property5 = new QifAdapterProperty();
        property5.setPropertyKey(AdapterEmail.SMTP_METHOD.getName());
        property5.setPropertyValue(EmailAdapter.SmtpMethod.SSL.getName());
        propertyList.add(property5);

        qifAdapter.setQifAdapterPropertyList(propertyList);
        return qifAdapter;
    }

    @Test
    public void putFile() throws IOException {
        EmailAdapter emailAdapter = new EmailAdapter(getQifAdapter());

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFrom("siak.konsolidasi@gmail.com");
        emailMessage.setSubject("first email using simple-java-mail");
        emailMessage.setContent("it works man!!");
        emailMessage.setToList(Arrays.asList("muhammad.irwin@quadras.co.id", "smirk137@yahoo.com"));

        emailAdapter.sendEmail(emailMessage);
        Assert.assertTrue(true);
    }

}
