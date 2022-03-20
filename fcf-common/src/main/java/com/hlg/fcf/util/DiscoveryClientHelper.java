package com.hlg.fcf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscoveryClientHelper {

    @Autowired
    private DiscoveryClient discoveryClient;

    private static  DiscoveryClientHelper _instance;

    private static DiscoveryClientHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringUtils.getBean(DiscoveryClientHelper.class);
        }
        return _instance;
    }

   public static List<ServiceInstance> getInstances(String serviceId)
   {
        return getInstance().discoveryClient.getInstances(serviceId);
   }

    public static List<ServiceInstance> getCurrInstances()
    {
       return getInstances(AppContextHelper.getAppCode());
    }

    public static List<String> getServices()
    {
        return getInstance().discoveryClient.getServices();
    }
}
