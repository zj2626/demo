package hello.annotation;


public enum LogInfoTypeEmnu {

    /**
     * 生产台接单开关
     */
    PRODUCE_RECEIPT_SWITCH("produceReceiptSwitch","生产台接单开关"),
    /**
     * 生产台调度开关
     */
    PRODUCE_DISPATCH_SWITCH("produceDispatchSwitch","生产台调度开关"),
    /**
     * 厨房端-生产台接单开关
     */
    KITCHEN_PRODUCE_DISPATCH_SWITCH("kitchenProduceDispatchSwitch","厨房端-生产台接单开关"),
    /**
     * 生产工序变更
     */
    PRODUCE_WORK_CHANGE("produceWorkChange","生产工序变更"),
    /**
     * 关联运营商商品变更
     */
    BUSINESS_PRODUCE_CHANGE("businessProduceChange","关联运营商商品变更"),
    /**
     * 平板添加
     */
    PAD_ADD("padAdd","平板添加"),
    /**
     * 平板删除
     */
    PAD_REMOVE("padRemove","平板删除"),
    /**
     * 库存变更
     */
    INVENTORY_CHANGE("inventoryChange","库存变更"),
    /**
     * 下单库存变更
     */
    ORDER_INVENTORY_CHANGE("inventoryChange","下单库存变更"),
    ;

    private String value;
    private String desc;

    LogInfoTypeEmnu(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
