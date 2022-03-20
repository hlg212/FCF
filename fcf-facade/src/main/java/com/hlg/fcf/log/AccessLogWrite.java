package com.hlg.fcf.log;

import com.hlg.fcf.model.log.IAccessLog;

/**
 * 访问日志储存接口
 * 框架默认采用 esb 写到 mq 中，然后再由日志中心进行入库
 *
 * 如果是集成版本，没有MQ，可以自定义实现，使用@Primary注解覆盖
 *
 * @author huangligui
 * @date 2019年5月5日
 */
public interface AccessLogWrite {

    public void write(IAccessLog log);
}
