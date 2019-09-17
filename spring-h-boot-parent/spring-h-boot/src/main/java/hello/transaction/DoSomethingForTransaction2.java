package hello.transaction;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void doTranTXY(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void doTranTXY_New(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void doTranTXY_Nested(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void doTranTXY_Mandatory(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void doTranTXY_Supports(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void doTranTXY_NotSupport(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.NEVER)
    public void doTranTXY_Never(String name) {
        try {
            System.out.println("XY---- ");
            areaCodeDao.updateNameArea("310105005", name + "---");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("FFFFFF");
    }

}
