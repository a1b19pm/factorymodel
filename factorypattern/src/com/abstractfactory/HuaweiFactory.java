package com.abstractfactory;

import com.product.phone.Huawei;
import com.product.phone.Phone;
import com.product.table.Table;

public class HuaweiFactory implements ProductStandard{

	@Override
	public Phone createPhone() {
		return new Huawei();
	}

	@Override
	public Table createTable() {
		return new com.product.table.Huawei();
	}
	
}
