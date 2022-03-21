package io.hlg212.fcf.model.mq;

public class QueueDeleteParam {

    private String mode;
    private String vhost;
    private String name;

    public QueueDeleteParam(String vhost, String name) {
        this.mode = "delete";
        this.vhost = vhost;
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
