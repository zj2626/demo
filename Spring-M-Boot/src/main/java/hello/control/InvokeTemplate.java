package hello.control;

public class InvokeTemplate {

    public InvokeTemplate() {
        super();
    }

    public void invoke(BaseResult result, InvokeCallback invokeCallback) {
        try {
            invokeCallback.checkParameters();
            invokeCallback.doInvoke();
        } catch (Exception ace) {
            ace.printStackTrace();
            result.setSuccess(false);
        } catch (Throwable t) {
            t.printStackTrace();
            result.setSuccess(false);
        } finally {
            invokeCallback.afterComplete();
        }
    }
}
