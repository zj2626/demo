package hello.service.model;

public class OptionParam {
    private String name;
    private Integer sex;
    
    public OptionParam() {
    }
    
    public OptionParam(String name, Integer sex) {
        this.name = name;
        this.sex = sex;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getSex() {
        return sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"sex\":")
                .append(sex);
        sb.append('}');
        return sb.toString();
    }
}
