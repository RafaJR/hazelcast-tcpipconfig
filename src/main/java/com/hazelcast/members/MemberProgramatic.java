package com.hazelcast.members;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hazelcast.config.CacheDeserializedValues;
import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.listeners.RandomIntegersIMapListener;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.pojos.serializables.CustomerDataSerializableFactory;
import com.hazelcast.pojos.serializables.ProductPortableFactoryImpl;
import com.hazelcasts.utils.Constants;

public class MemberProgramatic {

	public static void main(String[] args) throws Exception {
		
		// Instancia de hazelcast tcp-ip creada programáticamente
		HazelcastInstance hazelcastTcpIpProg = Hazelcast.newHazelcastInstance(getHazelcastConfig(Constants.programaticInstanceName));		
		
//		IMap<Object, Object> hzProgMap = hazelcastTcpIpProg.getMap(Constants.dataMap);

	}
	
	private static Config getHazelcastConfig(String instanceName) {
		
		Config config = new Config();
		
		// Configuración principal
		config.setInstanceName(instanceName);
		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);
		TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
		
		// Configuración de red
		tcpIpConfig.setEnabled(true);
//		tcpIpConfig.setRequiredMember(Constants.requiredMember);
//		tcpIpConfig.addMember(Constants.members);
		config.setProperty(Constants.partitionsPropertyName, String.valueOf(Constants.partitions));
		
		// Configuración de mapas de datos
		config.addMapConfig(buildMapConfig(Constants.dataMapProduct, Constants.dataProductAttr, Constants.dataProduct, Constants.timeToLiveSeconds));
		config.addMapConfig(buildMapConfig(Constants.dataMapCustomer, Constants.dataCustomerAttr, Constants.customerProduct, Constants.timeToLiveSeconds));
		// Mapa de números enteros para generar Números aleatorios
		config.addMapConfig(buildMapConfig(Constants.dataMapInteger, null, null, Constants.timeToLiveSeconds));
		
		// Configuración de hilos de ejecución de tareas
//		config.setProperty(Constants.threadCountPropertyName, String.valueOf(Constants.threadCount));
//		config.setProperty(Constants.genericOperationThreadCountPropertyName, String.valueOf(Constants.genericOperationThreadCount));
		
//		EntryProcessor ep;
//		ep.
		
		// Configuración de ejecutor de hilos		
		config.addExecutorConfig(getExecutorConfig(Constants.executorConfigName, Constants.executorPoolSize, Constants.queueCapacity, true));
		
		// Configuración se serialización; Debe realizarse tanto en le lado de miembros como en el cliente.
		SerializationConfig serializationConfig = new SerializationConfig();		
		serializationConfig.addDataSerializableFactoryClass(CustomerDataSerializableFactory.FACTORY_ID, CustomerDataSerializableFactory.class);
		serializationConfig.addPortableFactoryClass(ProductPortableFactoryImpl.FACTORY_ID, ProductPortableFactoryImpl.class);
		config.setSerializationConfig(serializationConfig);
		
		return config;
	}
	
	private static ExecutorConfig getExecutorConfig(String name, int executorPoolSize, int queueCapacity, boolean stadistics) {
		
		ExecutorConfig executorConfig = new ExecutorConfig(name);
		// Número de hilos por cada miembro desplegado
		executorConfig.setPoolSize(executorPoolSize);
		// Número de procesos encolados que se permiten como máximo; el máximo permitidlo por el host (0)
		executorConfig.setQueueCapacity(queueCapacity);
		// Recopilación de estadísticas de ejecución habilitada
		executorConfig.setStatisticsEnabled(stadistics);
		
		return executorConfig;
		
	}
	
	private static ExecutorConfig getExecutorConfig(String name) {
		
		return new ExecutorConfig(name);
		
	}
	
	// Metodo que devuelve la Configuración de mapa de datos a con los mismo parámetros del método
	private static MapConfig buildMapConfig(String id, String attr, String objectClass, int timeToLiveSeconds) {
		
		MapConfig mapConfig = new MapConfig(id);
		mapConfig.setTimeToLiveSeconds(timeToLiveSeconds);
		
//		mapProcuctConfig.addMapAttributeConfig(new MapAttributeConfig(attr, objectClass));
//		mapConfig.addEntryListenerConfig(listenerConfig)
		
		return mapConfig;
		
	}
	
	@SuppressWarnings("unused")
	private static MapConfig buildNumericListMapConfig(String id, String attr, String objectClass, int timeToLiveSeconds) {
		
		MapConfig mapConfig = new MapConfig(id);
		
		mapConfig.setTimeToLiveSeconds(timeToLiveSeconds);
		EntryListenerConfig entryListenerConfig = new EntryListenerConfig();
		entryListenerConfig.setLocal(true);
		entryListenerConfig.setImplementation(new RandomIntegersIMapListener());		
		mapConfig.addEntryListenerConfig(entryListenerConfig);
		
		return mapConfig;
		
	}

}