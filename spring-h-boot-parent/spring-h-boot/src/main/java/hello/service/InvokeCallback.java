package hello.service;

public abstract class InvokeCallback {
    public void checkParameters() {
        // Nothing to do.
    }

    public abstract void doInvoke();

    public void afterComplete() {
        // Nothing to do.
    }
}
