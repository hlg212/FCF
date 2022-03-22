
package  io.github.hlg212.fcf.listener;


import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import  io.github.hlg212.fcf.event.BaseEvent;



public interface BaseListener<E extends BaseEvent>  {
	
	//@Async
	//@EventListener
	void onEvent(E event);
}
