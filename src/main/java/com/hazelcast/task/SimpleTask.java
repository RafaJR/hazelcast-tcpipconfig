package com.hazelcast.task;
import java.io.Serializable;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;

import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public final class SimpleTask implements Runnable, Serializable, HazelcastInstanceAware{
	
	private static final long serialVersionUID = -8209987319310483699L;
	private static final String message = "Hola mundo!";
	private transient HazelcastInstance myInstance;
//    private	ILogger log;

	@Override
	public void run() {
		System.out.println(message + " - " + myInstance.getName());
		
	}

	@Override
	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
		myInstance = hazelcastInstance;
		
	}

}