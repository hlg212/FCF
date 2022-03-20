/**
 * 
 */
package com.hlg.fcf;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 可以序列化json基类
 * @author changwei
 * @date 2018年10月17日
 */
@JsonSerialize
public interface ISerializable extends Serializable{

}
