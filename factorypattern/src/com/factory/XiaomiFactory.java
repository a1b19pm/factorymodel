package com.factory;

import com.product.phone.Phone;
import com.product.phone.Xiaomi;

public class XiaomiFactory implements FactoryStandard{

	@Override
	public Phone createPhone() {
		return new Xiaomi();
	}

}
