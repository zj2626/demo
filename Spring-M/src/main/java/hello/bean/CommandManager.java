package hello.bean;

import java.util.Map;

public abstract class CommandManager {

    public Object process(Map commandState) {
        System.out.println("CommandManager process");
        Command command = createCommand();
        if (command != null) {
            command.setState(commandState);
            return command.execute();

        } else {
            return null;
        }
    }

    protected abstract Command createCommand();
}