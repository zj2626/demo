package hello.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockUtils {
    @Autowired
    private CuratorFramework client;

    public InterProcessMutex acquire(String lockStr) {
        InterProcessMutex lock = new InterProcessMutex(client, lockStr);

        try {
            lock.acquire(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lock;
    }

    public void release(InterProcessLock lock) {
        try {
            lock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
