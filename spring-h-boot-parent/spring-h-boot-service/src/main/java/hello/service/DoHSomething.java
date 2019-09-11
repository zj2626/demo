package hello.service;

public interface DoHSomething {

    public String sayHello(String name);

    public String sayFuckToKafka(String name);

    public String sayFuckToRabbitmq(String name);

    public String sayShit(String name);
    
    String sayFuckToActivemq(String name);
    
    public String sayFuckShit(String name);
}
