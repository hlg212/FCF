
package com.hlg.fcf.model.mc;

import com.hlg.fcf.model.Model;
import lombok.Data;

import java.util.Date;

@Data
public class OnlineUser extends Model implements  IOnlineUser{

	private String id;

	private String userId;

	private Date loginDate;

	private String clientId;

	private String token;

	private String username;

	private String appCode;

	private String ip;

}
