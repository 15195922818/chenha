package com.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ai.core.util.utils.RSAUtil;

public class testRSA{
	
	static String priv = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANnM20QaVLlr/0P4\n" +
            "lSKa2VQBV1yy/lmFHdwr0SS00SN4plsnHG2RokOs3z6vWnuSVdC/aHHbk6tq/S2/\n" +
            "9cwJ3IJt+mpax0ci3/IOsI5tTg8gtJp0/fw9zhm+F57H+wIjF7hWsj+0Xa47yDhP\n" +
            "tQnR/j9U9KcOZU1gUp/WDXEMhc+ZAgMBAAECgYBTJWSTlCXB/5ucnDp60nijHDv4\n" +
            "tJBrJmlm0wdtpuWsT/PaGo08uvsiMM2tBY5aXFwMa/X4eWXfa+GpZH32XdFi+UHb\n" +
            "RVdlw6eKt0qYDMtNubDu59o4xIuCxZ1/b47n+6L77H7lM4IKGTTFdh5zOlqRcwqX\n" +
            "Oyle48xNIsk8Dgur8QJBAO8Er6QMcXcsE9kSkFSDaBwK5Dxdol4rW+3aHnsjyKM1\n" +
            "qRksiBKdB5h+KQMHaDAc7Xw3dJSq03a2sZCb8Ekur00CQQDpRkBlUL7xxQ3p7R1n\n" +
            "SiP+oSGPNcvsoLZVF7ko+SjLhR1QoLPp1gpRUf4+WAPKxSxB58qHZHvtMttR/pjD\n" +
            "1ZN9AkEAzKBbanvIrDkNKPsum1wAxnlAmrmi5zIBuujQzaV+ANRQ1597Q0wyk4f2\n" +
            "voQZ2474di7X3Os+rNSNwfruxQh1xQJATkEL3zD8LRy+syehAoJjmsqw+FrCowmy\n" +
            "wM7phW44GjX4hJC0ahAse+U2lfFurDoCw8UfMtYpoIO8AUCYPLHL/QJAS2Fb8uNk\n" +
            "Q+rKx2bZAZWdYVhuMEFP7Fhc/FUjVYc0SbDmGHCwJ/nsLdoDR3oDuMKHkotJSS3M\n" +
            "SlZStB00k+vxXQ==";

	static  String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZzNtEGlS5a/9D+JUimtlUAVdc\n" +
            "sv5ZhR3cK9EktNEjeKZbJxxtkaJDrN8+r1p7klXQv2hx25Orav0tv/XMCdyCbfpq\n" +
            "WsdHIt/yDrCObU4PILSadP38Pc4Zvheex/sCIxe4VrI/tF2uO8g4T7UJ0f4/VPSn\n" +
            "DmVNYFKf1g1xDIXPmQIDAQAB";
	
	
	public static void main(String args[]){
		/*String str = RSAUtil.encryptRSA("asdfghjkl", publicKey);
		System.out.println(str);
		try {
			String str1 = RSAUtil.decryptRSA(str, priv);
			System.out.println(str1);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		test();
	}
	
	public static void test(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("c");
		list.add("b");
		Collections.sort(list);
		for(String str:list){
			System.out.println(str);
		}
		
		
	}
}

