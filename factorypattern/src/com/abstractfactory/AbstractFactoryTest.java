package com.abstractfactory;

public class AbstractFactoryTest {
	public static void main(String[] args) {
		ProductStandard product = new HuaweiFactory();
		product.createPhone().call();
		product.createTable().video();
	}
}
