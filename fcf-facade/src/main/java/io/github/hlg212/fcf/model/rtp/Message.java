package  io.github.hlg212.fcf.model.rtp;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author  huangligui
 * @create: 2019-01-25 10:46
 **/
@Data
public class Message implements Serializable {
    private String id;
    //
    private String type;
    // 真实消息内容
    private Object context;

    private String sendUserId;
    private String sendUserName;

    // 发送方
    private String origin;

    private long timestamp;
    public Message()
    {

    }
    public Message(String type,String context)
    {
        this.type = type;
        this.context = context;
    }
}
