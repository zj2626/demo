package hello.control;

public class BaseResult {
    private static final long serialVersionUID = 1L;
    private boolean success = true;
    private String info;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
