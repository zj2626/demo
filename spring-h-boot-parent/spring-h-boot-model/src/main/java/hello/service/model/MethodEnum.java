package hello.service.model;

public enum MethodEnum {
    /**
     * nothing...
     */
    CREATE("create", "创建-建"),
    UPDATE("update", "更新-新"),
    DELETE("delete", "删除-除"),
    ;
    
    MethodEnum(String command, String desc) {
        this.command = command;
        this.desc = desc;
    }
    
    private String command;
    private String desc;
    
    public static MethodEnum get(String command) {
        for (MethodEnum value : MethodEnum.values()) {
            if (command.equals(value.command)) {
                return value;
            }
        }
        return null;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
