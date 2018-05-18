package com.hazelcast.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;
import com.hazelcasts.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Se implementa 'Calleable' para que se devuelva algún resultado y así, poder hacer que la ejecución sea síncrona.
public class TaskIntegerListGeneration implements Serializable, /*Runnable*/ Callable<Double>, HazelcastInstanceAware {
	
	private transient HazelcastInstance hazelcastInstance;
	
	private static final long serialVersionUID = -889507328343414983L;
	private int nro_inputs;
	
	@Override
	public Double call() throws Exception {
		
		IMap<Long, List<Integer>> dataMapInteger = hazelcastInstance.getMap(Constants.dataMapInteger);
		
		System.out.println("TaskIntegerListGeneration se está ejecutando.");
		
		for(int i = 0; i < nro_inputs; i++) {
			
			dataMapInteger.put(hazelcastInstance.getIdGenerator(Constants.iDsGeneratorOnMember).newId(), getRandomIntegerList(100));
		}
				
//		dataMapInteger.entrySet().forEach(entry -> entry.getValue()
//			.forEach(value -> System.out.print(String.valueOf(
//			Constants.horaMinutoSegundoMilisegundo.format(new Date()))
//			.concat("Entero: ").concat(String.valueOf(value)).concat(", "))));
		
		return Double.valueOf(dataMapInteger.getLocalMapStats().getTotalPutLatency() / dataMapInteger.getLocalMapStats().getPutOperationCount());
	}
	
	private List<Integer> getRandomIntegerList(int intNumbers) {
		
		List<Integer> listInteger = new ArrayList<Integer>();
		
		while(listInteger.size() < intNumbers) {
			
			listInteger.add(getRandomInteger(-100, 100));
		}
		
		return listInteger;
	}
	
	private int getRandomInteger(int min, int max) {
		
		final int trustMin = min < max ? min : max;
		final int trustMax = (max > min ? max : min) + 1;
		
		return new Random().nextInt((trustMax - trustMin)) + trustMin;
	}

	@Override
	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {

		this.hazelcastInstance = hazelcastInstance;
		
	}

}