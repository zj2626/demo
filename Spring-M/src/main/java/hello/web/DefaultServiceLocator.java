package hello.web;

public class DefaultServiceLocator {
    private static Greeting greeting = new Greeting();

    public Greeting getGreeting() {
        return greeting;
    }
}
