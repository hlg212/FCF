package io.github.hlg212.fcf.core.handler;

public interface FillHandler {

    public Object inserFill(String property,Object bean,Class propertyType);

    public Object updateFill(String property,Object bean,Class propertyType);
}
