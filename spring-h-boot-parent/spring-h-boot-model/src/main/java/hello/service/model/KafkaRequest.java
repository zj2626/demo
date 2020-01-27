package hello.service.model;

import java.io.Serializable;

public class KafkaRequest implements Serializable {
    private static final long serialVersionUID = -8270973640126113723L;
    /**
     * @param times    生产者需要发送的消息总量
     * @param name     TOPIC
     * @param needSort 是否需要按照顺序消费
     */
    private Integer times;
    private String topic;
    private Boolean needSort;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getNeedSort() {
        return needSort;
    }

    public void setNeedSort(Boolean needSort) {
        this.needSort = needSort;
    }
}