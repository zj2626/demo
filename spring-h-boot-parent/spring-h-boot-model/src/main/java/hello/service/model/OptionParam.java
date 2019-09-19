package hello.service.model;

public class OptionParam {
    private String name;
    private Long time;
    
    public OptionParam() {
    }
    
    public OptionParam(String name, Long time) {
        this.name = name;
        this.time = time;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getTime() {
        return time;
    }
    
    public void setTime(Long time) {
        this.time = time;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"time\":")
                .append(time);
        sb.append('}');
        return sb.toString();
    }
}
