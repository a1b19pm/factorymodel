package com.factory;

public class FactoryTest {
	
	public static void main(String[] args) {
		FactoryStandard factory = new HuaweiFactory();
		factory.createPhone().call();
		factory = new XiaomiFactory();
		factory.createPhone().call();
	}
}
