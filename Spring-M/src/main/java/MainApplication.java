import hello.annotation.SimpleMovieLister;
import hello.lifecycle.BeanLevel;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class MainApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("【>>> main started <<<】");

        RedisTemplate<String, Object> throttlingRedisTemplate = (RedisTemplate<String, Object>) context.getBean("throttlingRedisTemplate");
        System.out.println(">>>>>>>>>>>>>" + throttlingRedisTemplate);

//        SetMethod setMethod = context.getBean(SetMethod.class);
//        setMethod.setName("FFF");
//        System.out.println(setMethod);
//
//        Hello hello = (Hello) context.getBean("hello");
//        hello.setAge(99L);
//        System.out.println(hello);
//        hello.setMd(new SetMethod("GGG"));
//        Hello helloF = (Hello) context.getBean("helloFuck");
//        System.out.println(helloF);
//        Hello hello2 = (Hello) context.getBean("hello2");
//        System.out.println(hello2);
////        hello = (Hello) context.getBean("hello233");
////        System.out.println(hello);
//
//        System.out.println("#########");
//        Greeting greeting = (Greeting) context.getBean("greeting");
//        System.out.println(greeting.getHello());
//        System.out.println(greeting.getHello2());
//        Greeting greeting2 = (Greeting) context.getBean("greeting");
//        System.out.println(greeting2);
//        System.out.println("#########");
//
//        Greeting greeting3 = (Greeting) context.getBean("greeting2");
//        greeting.setContent("GGGG");
//        System.out.println(greeting3);
//        Greeting greeting4 = (Greeting) context.getBean("greeting3");
//        System.out.println(greeting4);
//
////
//        greeting = (Greeting) context.getBean("greeting4");
//        greeting.setHello(new Hello("d", 10L, 2L));
//        System.out.println(greeting);
//        System.out.println("!!!!!");
//        greeting = (Greeting) context.getBean("greeting5");
//        greeting.setHello(new Hello("e", 10L, 2L));
//        System.out.println(greeting);
//        greeting = (Greeting) context.getBean("greeting5");
//        System.out.println(greeting);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//        CommandManager commandManager = (CommandManager) context.getBean("myManger");
//        commandManager.process(null);

        BeanLevel person = context.getBean(BeanLevel.class);
        System.out.println(person);

        System.out.println("【>>> main destroying <<<】");
        context.registerShutdownHook();

//        System.out.println("################################");
//        SimpleMovieLister simpleMovieLister = context.getBean(SimpleMovieLister.class);
//        simpleMovieLister.setSimpleName("s1");
//        simpleMovieLister.getMovieRecommender().setName("m1");
//        System.out.println(simpleMovieLister.toString());
//
//        SimpleMovieLister simpleMovieLister2 = context.getBean(SimpleMovieLister.class);
//        System.out.println(simpleMovieLister2.toString());
    }
}
