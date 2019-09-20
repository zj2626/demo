package com.demo.common.service.encryption;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
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
            
            // md5
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            
            //B617DE15978FBF4575EF1165182F02EF
            //byte[] resultByteArray = messageDigest.digest(result.getBytes());
            
            //B617DE15978FBF4575EF1165182F02EF
            messageDigest.update(result.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            
            System.out.println(byteArrayToHex(resultByteArray));
            
            // xml to object
            Person p = (Person) xmlToObject(Person.class, result);
            System.out.println(p);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
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
    
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        
        for (byte b : byteArray) {
            
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            
            resultCharArray[index++] = hexDigits[b & 0xf];
            
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
    
    @XmlRootElement(name = "Person")
    class Person {
        private String name;
        private Integer age;
        private List<String> scores;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Integer getAge() {
            return age;
        }
        
        public void setAge(Integer age) {
            this.age = age;
        }
        
        public List<String> getScores() {
            return scores;
        }
        
        public void setScores(List<String> scores) {
            this.scores = scores;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"").append(name).append('\"');
            sb.append(",\"age\":").append(age);
            sb.append(",\"scores\":").append(scores);
            sb.append('}');
            return sb.toString();
        }
    }
}

