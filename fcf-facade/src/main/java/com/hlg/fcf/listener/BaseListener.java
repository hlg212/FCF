
package com.hlg.fcf.listener;


import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hlg.fcf.event.BaseEvent;



public interface BaseListener<E extends BaseEvent>  {
	
	//@Async
	//@EventListener
	void onEvent(E event);
}
