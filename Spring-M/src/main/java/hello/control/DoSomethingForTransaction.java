package hello.control;

import hello.data.model.UCAreaDO;
import hello.data.service.AreaCodeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoSomethingForTransaction {
    private AreaCodeDao areaCodeDao;

    public void setAreaCodeDao(AreaCodeDao areaCodeDao) {
        this.areaCodeDao = areaCodeDao;
    }

    public DoSomethingForTransaction() {
        System.out.println("DoSomethingForTransaction FFF");
    }

    /*先调用transactionB再调用transactionA,观察控制台输出结果中transactionB接口的打印数据的变化*/
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public boolean dotransactionTXA(String name) {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                areaCodeDao.updateNameArea("231282022", name + "---" + i);
                areaCodeDao.insertArea("231282022", name + "-n-n-" + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("FFFFFF");
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public String dotransactionTXB() {
        /*
         * 1.READ_UNCOMMITTED时:脏读 > eg:getAreaName不停变化
         * 2.READ_COMMITTED时:  不可重复读:多次读数据时数据被修改则返回的数据不一致 > eg:getAreaName变化
         * 3.REPEATABLE_READ时: 幻读:多次读取不同数据集时返回的集合不一致 > eg:list.size变化
         * 4.SERIALIZABLE时:    效率低:写的过程不允许读取
         * */
        try {
            List<UCAreaDO> list = areaCodeDao.getArea("231282022");
            System.out.println(list.get(0).getAreaName() + " | " + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String dotransactionTXC() {
        try {
            for (int i = 0; i < 28; i++) {
                List<UCAreaDO> list = areaCodeDao.getArea("231282022");
                System.out.println(list.get(0).getAreaName() + " | " + list.size());
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void doTranTXX(String name) {
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println("XX++++ " + i);
                areaCodeDao.updateNameArea("232723004", name + "---" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doTranTXX_New(String name) {
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println("XX++++ " + i);
                areaCodeDao.updateNameArea("232723004", name + "---" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("FFFFFF");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void doTranTXX_Nested(String name) {
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println("XX++++ " + i);
                areaCodeDao.updateNameArea("232723004", name + "---" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("FFFFFF");
    }
}
