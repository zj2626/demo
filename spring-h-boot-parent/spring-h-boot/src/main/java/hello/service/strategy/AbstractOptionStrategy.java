package hello.service.strategy;

public abstract class AbstractOptionStrategy {
    private String name;
    
    public String invoke() {
        String result = option();
        
        return result;
    }
    
    /**
     * 各个子类继承并实现的接口,具体的业务逻辑内容
     *
     * @return
     */
    public abstract String option();
    
    public String common() {
        System.out.println("公共方法, 提供给自身或者各个子类调用");
        
        return null;
    }
}
