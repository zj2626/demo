package hello.service.model;

public class BaseResult {
    private static final long serialVersionUID = 1L;
    private boolean success = true;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
