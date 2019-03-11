package com.simplefactory;

public class FactoryTest {
	public static void main(String[] args) {
		SimpleFactory factory = new SimpleFactory();
		factory.createPhone(SimpleFactory.HUAWEI).call();
		factory.createPhone(SimpleFactory.XIAOMI).call();
	}
}
