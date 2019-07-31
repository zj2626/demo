package hello.service.model;

public class BatchEntity {
    private MethodEnum method;
    private String data;

    public MethodEnum getMethod() {
        return method;
    }

    public void setMethod(MethodEnum method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"method\":")
                .append(method);
        sb.append(",\"data\":\"")
                .append(data).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
