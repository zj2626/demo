package hello.spring.scope;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class DemoService {
    private int num;

    public int getNum(){
        return num;
    }

    public void setNum(Integer n){
        this.num = n;

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
