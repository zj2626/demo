package hello.service;

public interface DoHSomething {

    String remoteToDubboAsync(String name);

    String remoteToRabbitmq(String name);

    String remoteToDubboSync(String name);
    
    String remoteToActivemq(String name);
}
