package  io.github.hlg212.fcf.model.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exchange implements IExchange {

    private Boolean autoDelete;
    private int consumers;
    private Boolean durable;
    private String name;

    @Override
    public Boolean getAutoDelete() {
        return autoDelete;
    }

    @JsonProperty("auto_delete")
    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }
    @Override
    public int getConsumers() {
        return consumers;
    }

    public void setConsumers(int consumers) {
        this.consumers = consumers;
    }
    @Override
    public Boolean getDurable() {
        return durable;
    }

    public void setDurable(Boolean durable) {
        this.durable = durable;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
