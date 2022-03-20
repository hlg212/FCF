package com.hlg.fcf.model.rtp;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.model.basic.IOrg;

public interface ITopic  extends ISerializable {

    public static final String TYPE_CUS = "C";

    public static final String TYPE_USER = "U";

    public static final String TYPE_MONITOR = "M";

    public static final String TOPIC_SPLIT = ".";

    public String getName();

    public String getType();

    public String getId();

    public String getAppCode();

    public String getPartition();

    public Boolean getAutoDelete();

    // 最大关联
   // public int maxSubscribe();

    // 最大被关联
   // public int maxSubscribeTo();

    // 最多订阅者
   // public int maxSubscriber();

}
