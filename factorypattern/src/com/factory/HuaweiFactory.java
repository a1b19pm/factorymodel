package com.factory;

import com.product.phone.Huawei;
import com.product.phone.Phone;

public class HuaweiFactory implements FactoryStandard{

	@Override
	public Phone createPhone() {
		return new Huawei();
	}

}
