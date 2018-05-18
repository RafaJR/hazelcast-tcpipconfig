package com.hazelcast.clients;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import com.hazelcast.core.Member;
import com.hazelcast.logging.ILogger;
import com.hazelcast.pojos.Customer;
import com.hazelcast.pojos.Product;
import com.hazelcast.pojos.serializables.CustomerDataSerializableFactory;
import com.hazelcast.pojos.serializables.ProductPortableFactoryImpl;
import com.hazelcast.task.SimpleTask;
import com.hazelcast.task.TaskIntegerListGeneration;
//import com.hazelcast.util.function.Predicate;
//import java.util.function.Predicate;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.SqlPredicate;
import com.hazelcasts.utils.Constants;

public class ClientProgramatic {
	
	static final int NRO_INPUTS = 1000;
	private static List<Double> latency = new ArrayList<Double>();

	public static void main(String[] args) throws Exception {

		HazelcastInstance client = HazelcastClient.newHazelcastClient(getClientConfig(Constants.clientProgramaticName));

		ILogger log = client.getLoggingService().getLogger(ClientProgramatic.class);

		// Mapas de datos
		IMap<String, Product> dataMapProduct = client.getMap(Constants.dataMapProduct);
		IMap<String, Customer> dataMapCustomer = client.getMap(Constants.dataMapCustomer);		
		
		// Generador de Ids
		IdGenerator id = client.getIdGenerator(Constants.iDsGenerator);		
		
		IExecutorService executorService = client.getExecutorService(Constants.executorConfigName);
//		executorService.executeOnAllMembers(new SimpleTask());
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
//		<Boolean> Map<Member, Future<Boolean>> executionResult
//		System.out.println("Comienzo de la ejecución: ".concat(String.valueOf(Constants.horaMinutoSegundoMilisegundo.format(new Date()))));
//		Map<Member, Future<Boolean>> executionResult = executorService.submitToAllMembers(new TaskIntegerListGeneration(null, NRO_INPUTS)).entrySet().forEach(entry -> entry.getValue());
//		executorService.submitToAllMembers(new TaskIntegerListGeneration(null, NRO_INPUTS)).entrySet().forEach(entry -> {			
//			try {
//				latency.add(entry.getValue().get());
//				
//			} catch (InterruptedException | ExecutionException e) {
//				e.printStackTrace();
//			}
//			
//		});
		
//		double latenciAverange= latency.stream().mapToDouble(n -> n).summaryStatistics().getAverage();		

//		System.out.println("Fin de la ejecución: "
//			.concat(String.valueOf(sdf.format(new Date())))
//			.concat("\n Latencia media: ".concat(String.valueOf(latenciAverange))));

		for(double i = 1; i <= 1000; i++) {
			
			dataMapCustomer.put(Constants.dataCustomerAttr.concat(String.valueOf(id.newId())),
					new Customer(i, "Cliente ".concat(String.valueOf(i)), getRandomDni(), getRandomProfile()));
			dataMapProduct.put(Constants.dataProductAttr.concat(String.valueOf(id.newId())),
					new Product(i, "Producto ".concat(String.valueOf(i)), Double.valueOf(i*10), getRandomCustomerId(dataMapCustomer)));
			
			
		}

//		dataMapProduct.entrySet().forEach(entry -> log.info("Producto: ".concat(entry.getValue().toString())));
//		dataMapCustomer.entrySet().forEach(entry -> log.info("Cliente: ".concat(entry.getValue().toString())));
		Product product = getProductById(dataMapProduct, 11l);
		log.info("Producto por id: ".concat(product != null ? product.toString() : "Producto no encontrado"));
		Customer customer = getCustomerById(dataMapCustomer, 12l);
		log.info("Cliente por id: ".concat(customer != null ? customer.toString() : "Cliente no encontrado"));
//		List<Customer> listCustomer = getCustomerByProfile(dataMapCustomer, "a");
		List<Customer> listCustomer = getCustomerByProfileSql(dataMapCustomer, "%a%");
		log.info(listCustomer.isEmpty() ?
			"No se ha encontrado ningún cliente" :
			listCustomer.stream().map(Customer::toString)
			.collect(Collectors.joining("\n")));

	}
	
	private static double getRandomCustomerId(IMap<String, Customer> dataMapCustomer) {
		
		return dataMapCustomer.values().stream().findAny().get().getId();
	}
	
	private static List<Customer> getCustomerByProfileSql(IMap<String, Customer> dataMapCusomer, String profile) {
		
		SqlPredicate sqlPredicate = new SqlPredicate(String.format("profile like %s and idCustomer between %s and %s", profile, 1, 100));
		
		return dataMapCusomer.values(sqlPredicate).stream()
				.sorted((Customer c1, Customer c2) -> c1.getName().compareTo(c2.getName()))
				.collect(Collectors.toList());
	}
	
	private static List<Customer> getCustomerByProfile(IMap<String, Customer> dataMapCusomer, String profile) {		
		
		try {
			return dataMapCusomer.values( // Getting "Customer" values from IMap as Collection
				customer -> ((Customer)customer.getValue()).getProfile().contains(profile) && ((Customer)customer.getValue()).getIdCustomer() != 0) // Filtering the Collection by Customer.profile
				.stream().sorted((Customer c1, Customer c2) -> c1.getName().compareTo(c2.getName())) // Sorting by Customer.name
				.collect(Collectors.toList()); // Converting to "List<Customer>"
			
		}catch(NoSuchElementException e) {
			return null;
		}

	}	
	
	private static Product getProductById(IMap<String, Product> dataMapProduct, double id) {
		
		try {
//			return dataMapProduct.values(equal(id)).stream().findFirst().get();
//			return dataMapProduct.values((Predicate<String, Product>)p -> ((Product)p.getValue()).getIdProduct() == id).stream().findFirst().get();
			return dataMapProduct.values(p -> ((Product)p.getValue()).getIdProduct() == id).stream().findFirst().get();
		}catch(NoSuchElementException e) {
			return null;
		}
		
	}
	
	private static Customer getCustomerById(IMap<String, Customer> dataMapCustomer, double id) {
		
		try {
			return dataMapCustomer.values(c -> ((Customer)c.getValue()).getIdCustomer() == id).stream().findFirst().get();
			
		}catch(NoSuchElementException e) {
			return null;
		}
		
	}
		
	private static Predicate equal(double id) {
		
		return new Predicate<String, Product>() {
			
			private static final long serialVersionUID = 2680364721192560797L;

			@Override
			public boolean apply(Entry<String, Product> mapEntry) {

				return mapEntry.getValue().getIdProduct() == id;

			}			
		};

	}

	private static String getRandomDni() {		
		
		StringBuffer randomDni = new StringBuffer();
		
		for(int i = 0; i < 8; i++) {
			
			randomDni.append(getRandomInteger(0,9));
		}
		
		final char[] dniCharacters = {
				
				'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O',
				'P', 'Q', 'R', 'S', 'T',
				'U', 'W', 'X', 'Y', 'Z'
			};
		
		return randomDni.append(dniCharacters[getRandomInteger(0, 24)]).toString();

	}
	
	private static String getRandomProfile() {
		
		String[] strArray = {"Asiduo", "Casual", "Premium", "Moroso"};
		
		return strArray[getRandomInteger(0, 3)];
	}
	
	private static int getRandomInteger(int min, int max) {
		
		final int trustMin = min < max ? min : max;
		final int trustMax = (max > min ? max : min) + 1;
		
		return new Random().nextInt((trustMax - trustMin)) + trustMin;
	}

	private static ClientConfig getClientConfig(String instanceName) {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setInstanceName(instanceName);
		ClientNetworkConfig networkConfig = new ClientNetworkConfig();
//		networkConfig.addAddress(Constants.requiredMember);
		clientConfig.setNetworkConfig(networkConfig);
		// clientConfig.setProperty("hazelcast.logging.type", "log4j");
		// Configuración de serialización; debe realizarse tanto en el lado de miembro como en el de cliente
		SerializationConfig serializationConfig = new SerializationConfig();		
		serializationConfig.addDataSerializableFactoryClass(CustomerDataSerializableFactory.FACTORY_ID, CustomerDataSerializableFactory.class);
		serializationConfig.addPortableFactoryClass(ProductPortableFactoryImpl.FACTORY_ID, ProductPortableFactoryImpl.class);
		clientConfig.setSerializationConfig(serializationConfig);

		return clientConfig;

	}
	
	@SuppressWarnings("unused")
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

}
