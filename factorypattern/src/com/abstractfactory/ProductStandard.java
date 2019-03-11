package com.abstractfactory;

import com.product.phone.Phone;
import com.product.table.Table;

public interface ProductStandard {
	Phone createPhone();
	Table createTable();
}
