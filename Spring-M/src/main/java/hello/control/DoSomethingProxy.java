package hello.control;

import hello.data.service.AreaCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoSomethingProxy {
    @Autowired
    private DoSomethingForTransaction doSomethingForTransaction;
    @Autowired
    private DoSomethingForTransaction2 doSomethingForTransaction2;

    private AreaCodeDao areaCodeDao;

    public void setAreaCodeDao(AreaCodeDao areaCodeDao) {
        this.areaCodeDao = areaCodeDao;
    }

    //  UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only

    /**
     * Propagation.REQUIRED: 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
     * <p>
     * 在外围方法开启事务的情况下Propagation.REQUIRED修饰的内部方法会加入到外围方法的事务中，所有Propagation.REQUIRED修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚
     */
    @Transactional
    public void tranA(String name) {
        doSomethingForTransaction.doTranTXX(name);

        try {
            doSomethingForTransaction2.doTranTXY(name);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

//        throw new RuntimeException("FFFFFF");
    }


    /**
     * Propagation.REQUIRES_NEW: 创建一个新的事务，如果当前存在事务，则把当前事务挂起。
     * <p>
     * 在外围方法开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法依然会单独开启独立事务，且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。
     */
    @Transactional
    public void tranB(String name) {
        doSomethingForTransaction.doTranTXX_New(name);

//        try {
        doSomethingForTransaction2.doTranTXY_New(name);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        throw new RuntimeException("FFFFFF");
    }

    /**
     * Propagation.NESTED: 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。
     * <p>
     * 在外围方法开启事务的情况下Propagation.NESTED修饰的内部方法属于外部事务的子事务，外围主事务回滚，子事务一定回滚，而内部子事务可以单独回滚而不影响外围主事务和其他子事务
     */
    @Transactional
    public void tranC(String name) {
        doSomethingForTransaction.doTranTXX_Nested(name);

        try {
            doSomethingForTransaction2.doTranTXY_Nested(name);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

//        throw new RuntimeException("FFFFFF");
    }
}
