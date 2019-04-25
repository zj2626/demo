package hello.transaction;

import hello.data.service.AreaCodeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class DoTransaction {

    private TransactionTemplate transactionTemplate;
    private AreaCodeDao areaCodeDao;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setAreaCodeDao(AreaCodeDao areaCodeDao) {
        this.areaCodeDao = areaCodeDao;
    }

    public DoTransaction() {
        System.out.println("构造造 DoTransaction");
    }

    /*编程式事务管理*/
    public boolean dotransaction(String codes) {

        String areaName = "北京";
        System.out.println(areaName + ">>" + areaCodeDao.queryCodeByName(areaName, 2, null));


        final String[] codeList = codes.split(",");
        try {
            transactionTemplate.execute(new TransactionCallback<String>() {

                @Override
                public String doInTransaction(TransactionStatus transactionStatus) {
                    for (String code : codeList) {
                        Integer sum = areaCodeDao.removeArea(code);
                        if (sum <= 0) {
                            throw new RuntimeException("未删除");
                        }
                    }

                    return null;
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
//                    transactionStatus.setRollbackOnly();
        }
        return false;
    }

    /*声明式事务管理*/
    public boolean dotransactionTX(String codes) {

        String[] codeList = codes.split(",");
        for (String code : codeList) {
            Integer sum = areaCodeDao.removeArea(code);
            if (sum <= 0) {
                throw new RuntimeException("未删除");
            }
        }
        return true;
    }

    /*声明式事务管理*/
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean dotransactionAnnotation(String codes) {

        String[] codeList = codes.split(",");
        for (String code : codeList) {
            Integer sum = areaCodeDao.removeArea(code);
            if (sum <= 0) {
                throw new RuntimeException("未删除");
            }
        }
        return true;
    }


}
