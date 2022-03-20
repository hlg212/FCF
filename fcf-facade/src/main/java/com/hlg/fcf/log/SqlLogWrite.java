package com.hlg.fcf.log;

/**
 * SQL日志储存接口
 *
 * 内部接口，无需关心
 *
 * @author huangligui
 * @date 2019年5月5日
 */
public interface SqlLogWrite {

    public void write(String log);
}
