package com.simplefactory;

import com.product.phone.Huawei;
import com.product.phone.Phone;
import com.product.phone.Xiaomi;

public class SimpleFactory implements SimpleFactoryStandard{
	public static final String HUAWEI = "huawei";
	public static final String XIAOMI = "xiaomi";
	
	@Override
	public Phone createPhone(String brand) {
		switch (brand) {
		case HUAWEI:
			return new Huawei();			
		case XIAOMI:
			return new Xiaomi();
		}
		return null;
	}
}
