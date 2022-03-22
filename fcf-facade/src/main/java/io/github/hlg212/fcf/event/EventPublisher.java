package  io.github.hlg212.fcf.event;

public interface EventPublisher<T> {

    public void publish(T event);

}
