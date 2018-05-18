package com.hazelcast.pojos;

import java.io.IOException;
import java.io.Serializable;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.hazelcast.pojos.serializables.CustomerDataSerializableFactory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
/** 
 * Implementación de DataSerializable o IdentifiedDataSerializable:
 * require del constructor sin argumentos.
 * Puede ser privado, con lo cual se evita el acceso externo
 */
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@ToString
public class Customer /*extends ValueExtractor*/ /*implements Serializable*/ implements IdentifiedDataSerializable {
	
//	private static final long serialVersionUID = -9129084951154740108L;
	
	private double idCustomer;
	private String name;
	private String dni;
	private String profile;
	
	@Override
	public void readData(ObjectDataInput input) throws IOException {
		
		this.idCustomer = input.readDouble();
		this.name = input.readUTF();
		this.dni = input.readUTF();
		this.profile = input.readUTF();
	}

	@Override
	public void writeData(ObjectDataOutput output) throws IOException {

		output.writeDouble(this.idCustomer);
		output.writeUTF(this.name);
		output.writeUTF(this.dni);
		output.writeUTF(this.profile);
		
	}
	
	@Override
	public int getFactoryId() {
		
		return CustomerDataSerializableFactory.FACTORY_ID;
	}
	
	@Override
	public int getId() {
		
		return CustomerDataSerializableFactory.CUSTOMER_TYPE;
	}
	
//	@Override
//	public void extract(Object target, Object argument, ValueCollector collector) {
//		
//	}

}