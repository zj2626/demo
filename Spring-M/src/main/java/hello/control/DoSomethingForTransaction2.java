package hello.control;

import hello.data.service.AreaCodeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoSomethingForTransaction2 {
    private AreaCodeDao areaCodeDao;

    public void setAreaCodeDao(AreaCodeDao areaCodeDao) {
        this.areaCodeDao = areaCodeDao;
    }

    public DoSomethingForTransaction2() {
        System.out.println("DoSomethingForTransaction FFF");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void doTranTXY(String name) {
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println("XY---- " + i);
                areaCodeDao.updateNameArea("310105005", name + "---" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void doTranTXY_New(String name) {
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println("XY---- " + i);
                areaCodeDao.updateNameArea("310105005", name + "---" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void doTranTXY_Nested(String name) {
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println("XY---- " + i);
                areaCodeDao.updateNameArea("310105005", name + "---" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }
}
