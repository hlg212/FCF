
package io.hlg212.fcf.model.ga;

import io.hlg212.fcf.ISerializable;

public interface IRoute extends ISerializable {

    public String getId();

    public String getName();

    public String getMatchPath();

    public String getNmatchPath() ;

    public String getUri();

    public Long getOrder();
}
