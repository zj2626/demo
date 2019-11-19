package com.demo.common.service.xml;

import com.demo.common.service.encryption.entity.Person;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MarshallerTest {
    @Test
    public void main() {
        List<String> scores = new ArrayList<>();
        scores.add("57");
        scores.add("98");
        Person person = new Person();
        person.setAge(20);
        person.setName("zj2626");
        person.setScores(scores);

        try {
            // object to xml
            String result = objectToXML(Person.class, person);
            System.out.println(result);

            // xml to object
            Person p = (Person) xmlToObject(Person.class, result);
            System.out.println(p);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static String objectToXML(Class clazz, Object object) throws JAXBException {
        String xml;
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Writer w = new StringWriter();
        m.marshal(object, w);
        xml = w.toString();
        return xml;
    }

    public static Object xmlToObject(Class clazz, String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller um = context.createUnmarshaller();
        return um.unmarshal(new StringReader(xml));
    }
}

