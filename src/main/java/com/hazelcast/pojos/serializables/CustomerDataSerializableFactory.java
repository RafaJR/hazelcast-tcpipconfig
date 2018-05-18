package com.hazelcast.pojos.serializables;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.hazelcast.pojos.Customer;

public class CustomerDataSerializableFactory implements DataSerializableFactory {
	
	public static final int FACTORY_ID = 1;
	public static final int CUSTOMER_TYPE = 1;

	@Override
	public IdentifiedDataSerializable create(int typeId) {

		return typeId == CUSTOMER_TYPE ? new Customer() : null;
	}

}
