package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest extends MyExcutor {
    private Integer count = 0;
    private AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference(count, 0);

    /**
     * 解决ABA问题:使用版本戳Stamp避免
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(300);
        excutorPool.futureGet();
        System.out.println("结果 " + atomicStampedReference.getReference() + " --- " + atomicStampedReference.getStamp());
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        for (; ; ) {
            Integer reference = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            boolean result = atomicStampedReference.compareAndSet(reference, reference + 2, stamp, stamp + 1);
            if (result) {
                break;
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException ignored) {
        }
        for (; ; ) {
            Integer reference = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            boolean result = atomicStampedReference.compareAndSet(reference, reference - 2, stamp, stamp + 1);
            if (result) {
                break;
            }
        }

        return null;
    }
}
