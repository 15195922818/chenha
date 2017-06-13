package com.ai.kafkatest;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by GW on 2017/2/7.
 */
//@Service
//@Lazy(false)
public class SimpleMessageReciver extends AbstractMessageReciver {

    @Override
    protected void processMsg(String key, String value) {

        System.out.println(key + "#" + value);
    }
}
