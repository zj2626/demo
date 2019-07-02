package hello.web;

import org.springframework.beans.factory.annotation.Autowired;

public class Greeting {
    private static Greeting greeting = new Greeting();

    private long id;
    private String content;
//    @Autowired
    private Hello hello;
    @Autowired
    private Hello hello2;

    public static Greeting getInstance(){
        return greeting;
    }

    public static Greeting getInstance(long id, String content){
        return greeting;
    }

    public static Greeting getInstance2(){
        return null;
    }

    public Greeting() {
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Hello getHello() {
        return hello;
    }

    public void setHello(Hello hello) {
        this.hello = hello;
    }

    public Hello getHello2() {
        return hello2;
    }

    public void setHello2(Hello hello2) {
        this.hello2 = hello2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"content\":\"")
                .append(content).append('\"');
        sb.append(",\"hello\":")
                .append(hello);
        sb.append(",\"hello2\":")
                .append(hello2);
        sb.append('}');
        return sb.toString();
    }
}