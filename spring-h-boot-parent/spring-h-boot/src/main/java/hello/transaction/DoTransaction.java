package hello.transaction;

import hello.data.service.AreaCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class DoTransaction {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AreaCodeDao areaCodeDao;

    /*编程式事务管理*/
    public boolean dotransaction(String codes) {

        String areaName = "北京";
        System.out.println(areaName + ">>" + areaCodeDao.queryCodeByName(areaName, 2, null));


        final String[] codeList = codes.split(",");
        try {
            transactionTemplate.execute((TransactionCallback<String>) transactionStatus -> {
                for (String code : codeList) {
                    Integer sum = areaCodeDao.removeArea(code);
                    if (sum <= 0) {
                        System.err.println("未删除");
                        return null;
                    }
                }

                return null;
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
//                    transactionStatus.setRollbackOnly();
        }
        return false;
    }

    /*声明式事务管理*/
    public boolean dotransactionTXWrite(String codes) {
        String[] codeList = codes.split(",");

        transactionTemplate.execute((TransactionCallback<String>) transactionStatus -> {
            for (String code : codeList) {
                Integer sum = areaCodeDao.updateNameArea(code, System.currentTimeMillis() + "");
                if (sum <= 0) {
                    System.err.println("未更新");
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("结束");

            return null;
        });

        return true;
    }

    /**
     * 读取到的数据是否是脏读的取决去[dotransactionTXRead]方法的事务隔离级别,而不是[dotransactionTXWrite]
     * @param codes
     * @return
     */
    public boolean dotransactionTXRead(String codes) {
        String[] codeList = codes.split(",");

        transactionTemplate.execute((TransactionCallback<String>) transactionStatus -> {
            for (String code : codeList) {
                System.out.println(areaCodeDao.getArea(code));
            }
            return null;
        });

        return true;
    }

    /*声明式事务管理*/
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean dotransactionAnnotation(String codes) {

        String[] codeList = codes.split(",");
        for (String code : codeList) {
            Integer sum = areaCodeDao.removeArea(code);
            if (sum <= 0) {
                System.err.println("未删除");
                return false;
            }
        }
        return true;
    }


}
