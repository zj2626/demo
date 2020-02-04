package hello.database.model;

import java.util.List;

public class UCAreaDO {
    private static final long serialVersionUID = 1L;

    private String id;
    /**/
    private String areaCode;
    /**/
    private String areaName;

    private List<String> areaNames;
    /**/
    private Integer level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getAreaNames() {
        return areaNames;
    }

    public void setAreaNames(List<String> areaNames) {
        this.areaNames = areaNames;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"areaCode\":\"")
                .append(areaCode).append('\"');
        sb.append(",\"areaName\":\"")
                .append(areaName).append('\"');
        sb.append(",\"areaNames\":")
                .append(areaNames);
        sb.append(",\"level\":")
                .append(level);
        sb.append('}');
        return sb.toString();
    }
}