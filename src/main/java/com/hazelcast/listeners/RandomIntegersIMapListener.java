package com.hazelcast.listeners;

import java.util.List;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

/** 
 * @author rjimenez
 * Listener para el IMap de números aleatorios
 */
public class RandomIntegersIMapListener implements EntryAddedListener<Long, List<Integer>>,
													EntryRemovedListener<Long, List<Integer>>,
													EntryUpdatedListener<Long, List<Integer>> {
	
	private static final int divisor = 10;

	@Override
	public void entryUpdated(EntryEvent<Long, List<Integer>> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entryRemoved(EntryEvent<Long, List<Integer>> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entryAdded(EntryEvent<Long, List<Integer>> event) {
		
		long key = event.getKey();
		
		if(key % divisor == 0) {
			
			System.out.println("La llave ".concat(String.valueOf(toString())).concat(" es múltiplo de ").concat(String.valueOf(divisor)));
		}
		
	}

}
