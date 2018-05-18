package com.hazelcasts.utils;

import java.text.SimpleDateFormat;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;

public class Constants {
	
	// Configuración estática
	public static final Config hazelcastTcpIpConfig = new ClasspathXmlConfig("hazelcast.xml");
	
	// Maps
	public static final String dataMapProduct = "dataMapProduct";
	public static final String dataMapCustomer = "dataMapCustomer";
	public static final String dataMapInteger = "dataMapInteger";
	// Variables de Configuración de Maps
	public static final int timeToLiveSeconds = 0; // Tiempo de vida límite en el map, pasado sin consumir se borra
	
	// Configuración de red
	public static final String requiredMember = "192.168.1.72";
	public static final String members = "192.168.1.1-200";
	
	// Configuración de propiedades de Hazelcast
	public static final String partitionsPropertyName = "hazelcast.map.partition.count";
	public static final int partitions = 271; //Se recomienda numero primo 
	public static final String threadCountPropertyName = "hazelcast.io.thread.count";
	public static final int threadCount = 100;
	public static final String genericOperationThreadCountPropertyName = "hazelcast.operation.generic.thread.count";
	public static final int genericOperationThreadCount = 10;
	
	// Configuración de ejecución de hilos
	public static final String executorConfigName = "executorConfig1";
	public static final int executorPoolSize = 10;
	public static final int queueCapacity = 0;
	
	// Clases de datos
	public static final String dataProduct = "com.hazelcast.pojos.Product";
		public static final String dataProductAttr = "attrProduct";
	public static final String customerProduct = "com.hazelcast.pojos.Customer";
		public static final String dataCustomerAttr = "attrCustomer";
	public static final String dataInteger = "java.lang.Integer";
		
	// Nombres de instancias
	public static final String staticInstanceName = "staticMemberInstance";
	public static final String programaticInstanceName = "programaticMemberInstance";
	public static final String clientProgramaticName = "clientProgramaticName";
	
	// Generador de ids en cliente
	public static final String iDsGenerator = "iDsGenerator";
	public static final String iDsGeneratorOnMember = "iDsGenerator";
	
	// Formatos de fecha
	public static final SimpleDateFormat horaMinutoSegundoMilisegundo = new SimpleDateFormat("hh:mm:ss.SSS");
	
	// Otros datos
	public static final int intNumbers = 100;

}