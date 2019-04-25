package hello.lock;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockServiceB {
    String lockFStr = "/lock_fuck";
    String lockSStr = "/lock_shit";

    @Autowired
    private LockUtils lockUtils;

    public void invokeF() {
        System.out.println("LockServiceB");

        InterProcessMutex lock = lockUtils.acquire(lockFStr);

        System.out.println("LockServiceB in " + lockFStr);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockUtils.release(lock);
        }
    }

    public void invokeS() {
        System.out.println("LockServiceB");

        InterProcessMutex lock = lockUtils.acquire(lockSStr);

        System.out.println("LockServiceB in " + lockSStr);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockUtils.release(lock);
        }
    }
}
