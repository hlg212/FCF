package  io.github.hlg212.fcf.model.basic;

import lombok.Data;

import java.util.Date;

@Data
public class UserLock implements IUserLock {

    private String username;
    private String errorMsg;
    private Date createTime;
    private String createUser;
    private Date lockToTime;
}
