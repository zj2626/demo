package hello.web;

import java.beans.ConstructorProperties;

public class Hello {
    private String name;
    private Long age;
    private Long sex;
    private SetMethod md;

    public Hello() {
    }

//    @ConstructorProperties({"name", "age", "sex"})
    public Hello(String name, Long age, Long sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public void tell(){
        System.out.println("HHHHHHHHHHHH");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public SetMethod getMd() {
        return md;
    }

    public void setMd(SetMethod md) {
        this.md = md;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"age\":")
                .append(age);
        sb.append(",\"sex\":")
                .append(sex);
        sb.append(",\"md\":")
                .append(md);
        sb.append('}');
        return sb.toString();
    }
}
