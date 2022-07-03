package  io.github.hlg212.fcf.service;

import  io.github.hlg212.fcf.Curd;
import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.Qco;

public interface CurdService<T extends ISerializable> extends QueryService<T>,Curd<T, Qco> {

	
}
