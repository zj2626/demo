package hello.service;

public interface DoHSomething {

    public String remoteToDubboAsync(String name);

    public String remoteToKafka(String name);

    public String remoteToRabbitmq(String name);

    public String remoteToDubboSync(String name);
    
    String remoteToActivemq(String name);
    
    public String local(String name);
}
