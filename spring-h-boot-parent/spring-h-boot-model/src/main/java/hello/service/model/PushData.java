package hello.service.model;

import java.util.Date;

public class PushData {
    private String id;

    private String msg;

    private String code;

    private Integer flag;

    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append(",\"code\":\"")
                .append(code).append('\"');
        sb.append(",\"flag\":")
                .append(flag);
        sb.append(",\"time\":\"")
                .append(time).append('\"');
        sb.append('}');
        return sb.toString();
    }
}