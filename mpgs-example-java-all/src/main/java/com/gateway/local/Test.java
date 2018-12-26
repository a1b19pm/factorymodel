package com.gateway.local;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import com.gateway.app.Config;
import com.gateway.client.ApiProtocol;
import com.gateway.client.ApiService;

public class Test {
	private static final String UTF8_ENCODING = "UTF-8";
	private static String testSend() throws Exception{
//		Map<String, String> sourceOfFundsMap = new HashMap<String, String>();
//		sourceOfFundsMap.put("type", "CARD");
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("sourceOfFunds", sourceOfFundsMap);
//		Gson data = new Gson();
//		String jsonData = data.toJson(dataMap);
		String jsonData = "";
		Config config = new Config();
		config.setApiUsername("TEST936695006");
		config.setApiPassword("Hkbea0534");
		config.setAuthenticationType(Config.AuthenticationType.PASSWORD);
//		String requestUrl = "https://ap-gateway.mastercard.com/api/rest/version/50/merchant/TEST936695006/token/tk0001";
		String requestUrl = "https://ap-gateway.mastercard.com/api/rest/version/50/merchant/TEST936695006/session";
		HttpPut httpPut = new HttpPut(requestUrl);
        httpPut.setEntity(new StringEntity(jsonData, UTF8_ENCODING));
        return ApiService.executeHTTPMethod(httpPut, config, ApiProtocol.REST);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(Test.testSend());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}