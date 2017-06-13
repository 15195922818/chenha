package com.test;

import java.text.MessageFormat;

public class testMessage {
	public static void main(String[] args) {
		Integer n = 1000;
		String sql = MessageFormat.format("select {0} from {1} where {2} {3} limit {4}",
                "id,occur_time,dst_address", "event_20170516", "( occur_time >= 1494817752000 and occur_time <= 1494947352000 ) and _type='event'", "", n);
		System.out.println(sql);
	}
}
