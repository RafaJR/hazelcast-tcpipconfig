package com.hazelcast.pojos;

import java.io.IOException;
import java.io.Serializable;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.pojos.serializables.ProductPortableFactoryImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product /*extends ValueExtractor*/ implements Portable { 
	
	/** 
	 * Serializable: Se serializa todo lo que no sea ni estático ni transient en la clase.
	 * 
	 */
	
	private static final long serialVersionUID = -2566192309597454238L;
	
	private double idProduct;
		private final String ID_PRODUCT = "idProduct";
	private String name;
		private final String NAME = "name";
	private double prize;
		private final String PRIZE = "prize";
	private double customer;
		private final String CUSTOMER = "customer";
	
	@Override
	public int getFactoryId() {		
		return ProductPortableFactoryImpl.FACTORY_ID;
	}
	
	@Override
	public int getClassId() {
		return ProductPortableFactoryImpl.PRODUCT_CLASS_ID;
	}
	
	@Override
	public void writePortable(PortableWriter writer) throws IOException {
		
		writer.writeDouble(ID_PRODUCT, idProduct);
		writer.writeUTF(NAME, name);
		writer.writeDouble(PRIZE, prize);
		writer.writeDouble(CUSTOMER, customer);
	}
	
	@Override
	public void readPortable(PortableReader reader) throws IOException {
		
		this.idProduct = reader.readDouble(ID_PRODUCT);
		this.name = reader.readUTF(NAME);
		this.prize = reader.readDouble(PRIZE);
		this.customer = reader.readDouble(CUSTOMER);
		
	}
	
//	@Override
//	public void extract(Object target, Object argument, ValueCollector collector) {
	
//	}

}