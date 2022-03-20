
package com.hlg.fcf.model.basic;

import com.hlg.fcf.model.Model;
import lombok.Data;

@Data
public class App extends Model implements  IApp{

	private String id;
	private String name;
	private String code;
	private String loginPage;
	private String type;
	private String context;
	private String icon;
	private String indexPage;
}
