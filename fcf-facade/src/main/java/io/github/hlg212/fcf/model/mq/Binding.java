package  io.github.hlg212.fcf.model.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Binding implements IBinding {
    private String destination;
    private String destinationType;
    private String routingKey;
    private String source;

    @Override
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getDestinationType() {
        return destinationType;
    }
    @JsonProperty("destination_type")
    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }
    @JsonProperty("routing_key")
    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
