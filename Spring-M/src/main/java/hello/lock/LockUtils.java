package hello.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

public class LockUtils implements InitializingBean {
    private CuratorFramework curatorFramework;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.curatorFramework.start();
    }

    public void setCuratorFramework(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public InterProcessMutex acquire(String lockStr) {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, lockStr);

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
