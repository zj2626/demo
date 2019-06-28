package hello.transaction;

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
    //  IllegalTransactionStateException: No existing transaction found for transaction marked with propagation 'mandatory'

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

    /**
     * Propagation.MANDATORY: 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
     * <p>
     * 必须外围方法开启事务,并且
     * 在外围方法开启事务的情况下Propagation.MANDATORY修饰的内部方法会加入到外围方法的事务中，所有Propagation.MANDATORY修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚
     */
    @Transactional
    public void tranD(String name) {
        doSomethingForTransaction.doTranTXX_Mandatory(name);

//        try {
        doSomethingForTransaction2.doTranTXY_Mandatory(name);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

//        throw new RuntimeException("FFFFFF");
    }

    /**
     * Propagation.SUPPORTS: 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
     * <p>
     * 外围没有开启事务情况下,Propagation.SUPPORTS修饰的内部方法以非事务的方式运行
     * 在外围方法开启事务的情况下Propagation.SUPPORTS修饰的内部方法会加入到外围方法的事务中，所有Propagation.SUPPORTS修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚
     */
//    @Transactional
    public void tranE(String name) {
        doSomethingForTransaction.doTranTXX_Supports(name);

//        try {
        doSomethingForTransaction2.doTranTXY_Supports(name);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        throw new RuntimeException("FFFFFF");
    }

    /**
     * Propagation.NOT_SUPPORTED: 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
     * <p>
     * 等于不开事务
     */
    @Transactional
    public void tranF(String name) {
        doSomethingForTransaction.doTranTXX_NotSupport(name);

//        try {
        doSomethingForTransaction2.doTranTXY_NotSupport(name);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        throw new RuntimeException("FFFFFF");
    }

    /**
     * Propagation.NEVER: 以非事务方式运行，如果当前存在事务，则抛出异常。
     * <p>
     * 禁止开事务, 如果外围有事务则抛异常
     */
    @Transactional
    public void tranG(String name) {
        doSomethingForTransaction.doTranTXX_Never(name);

//        try {
//            doSomethingForTransaction2.doTranTXY_Never(name);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

//        throw new RuntimeException("FFFFFF");
    }

    /*****************************/

//     @Transactional  // 有效 和其他事务方法相同
    public void transactionFunctionA(String name) {
        doSomethingForTransaction.doTranTXW(name);

        transactionFunctionB(name);

//        throw new RuntimeException("FFFFFF");
    }

    /**
     * 我们知道，Spring之所以可以对开启@Transactional的方法进行事务管理，是因为Spring为当前类生成了一个代理类，
     * 然后在执行相关方法时，会判断这个方法有没有@Transactional注解，如果有的话，则会开启一个事务。
     * 但是，上面这种调用方式时，在调用baz()时，使用的并不是代理对象，从而导致this.bar()时也不是代码对象，
     * 从而导致@Transactional失败。
     */
    @Transactional
    public void transactionFunctionB(String name) {
        doSomethingForTransaction.doTranTXX(name);

//        try {
        doSomethingForTransaction2.doTranTXY(name);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

//        throw new RuntimeException("FFFFFF");
    }
}
