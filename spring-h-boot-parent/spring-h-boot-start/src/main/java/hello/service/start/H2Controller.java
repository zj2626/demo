package hello.service.start;

import hello.service.DoHSomething;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H2Controller {
    @Autowired
    private DoHSomething doHSomething;

    private Integer down = 2;


    @GetMapping("/ddd")
    public String test(String name) {
        String result = doHSomething.sayFuckShit(name);

        return result;
    }

    @GetMapping("/setdm")
    public String setDenominator(Integer down) {
        this.down = down;

        return "success";
    }

    @GetMapping("/message")
    public String getMessage(String message) {
        try {
            int total = (int) (1 + Math.random() * (100 - 1 + 1)); // 1-100
            System.out.println(message + " > " + total + " / " + down);
            if (total % down != 0) { // 有一定几率sleep down=1则为0% down=2则为50% down=3则为66.6% down=5则为75% down=10则为90%
                Thread.sleep(6000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "success";
    }
}
