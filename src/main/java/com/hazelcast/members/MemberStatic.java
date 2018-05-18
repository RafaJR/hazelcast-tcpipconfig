package com.hazelcast.members;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcasts.utils.Constants;

public class MemberStatic {

	public static void main(String[] args) throws Exception {
		
		// Instancia de hazelcast tcp-ip creada con configuración xml
		HazelcastInstance hazelcastTcpIp = Hazelcast.newHazelcastInstance(Constants.hazelcastTcpIpConfig);
		hazelcastTcpIp.getConfig().setInstanceName(Constants.staticInstanceName);
		
//		IMap<Object, Object> hzProgMap = hazelcastTcpIpProg.getMap(Constants.dataMap);

	}

}
