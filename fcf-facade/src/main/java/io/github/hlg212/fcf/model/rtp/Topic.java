package  io.github.hlg212.fcf.model.rtp;

import com.fasterxml.jackson.annotation.JsonFormat;
import  io.github.hlg212.fcf.annotation.Field;
import  io.github.hlg212.fcf.model.Model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: frame-parent
 * @description: 话题
 * @author  huangligui
 * @create: 2019-01-23 16:35
 **/
@Data
public class Topic extends Model implements  ITopic{

    @Field(description="id")
    private String id;

    //名称
    @Field(description="名称,可以写中文")
    private String name;

    // E: 模块 U: 用户  C: 自定义
    @Field(description="类型,U: 用户  C: 自定义")
    private String type;

    @Field(description="所属应用")
    private String appCode;

    // 分区
    @Field(description="分区")
    private String partition;

    // 分块
    private String block;

    // 过期
    @Field(description="过期时间")
    private Date expires;
    //创建时间
    @Field(description="创建时间")
    private Date createTimestamp;

    @Field(description="自动删除")
    private Boolean autoDelete;

}
