
package com.hlg.fcf.event;

import com.hlg.fcf.annotation.RemoteEventAnnotation;
import lombok.Data;

import java.util.Date;

/**
 * token 被移除事件
 * 用户登出之后会触发该事件
 * @author: huangligui
 * @create: 2019-04-2 09:16
 **/

@RemoteEventAnnotation(topic = Constants.Topic.FRAME)
@Data
public class TokenRemoteEvent extends  RemoteEvent {

    private String token;

    private Date date;

    private String appCode;
}