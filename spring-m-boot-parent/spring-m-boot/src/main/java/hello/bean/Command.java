package hello.bean;

public class Command {
    Command command = new Command();

    private Object state;

    public Command createCommand() {
        return command;
    }

    public Object execute() {
        System.out.println("Command execute");
        return null;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }
}
