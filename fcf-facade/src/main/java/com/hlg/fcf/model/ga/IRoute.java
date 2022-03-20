
package com.hlg.fcf.model.ga;

import com.hlg.fcf.ISerializable;

public interface IRoute extends ISerializable {

    public String getId();

    public String getName();

    public String getMatchPath();

    public String getNmatchPath() ;

    public String getUri();

    public Long getOrder();
}
