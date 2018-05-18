package com.hazelcast.pojos.serializables;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableFactory;
import com.hazelcast.pojos.Product;

public class ProductPortableFactoryImpl implements PortableFactory {
	
	public final static int PRODUCT_CLASS_ID = 2;
    public final static int FACTORY_ID = 2;

	@Override
	public Portable create(int classId) {
		
		switch(classId) {
		
			case PRODUCT_CLASS_ID:	return new Product();				
			default: return null;
			
		}
	}

}
