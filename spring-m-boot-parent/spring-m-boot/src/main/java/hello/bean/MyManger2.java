package hello.bean;

public class MyManger2 extends CommandManager {

    @Override
    protected Command createCommand() {
        System.out.println("MyManger2 createCommand");
        return null;
    }
}
