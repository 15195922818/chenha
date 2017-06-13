package com.ai.core.util.domain.entity;

import java.io.Serializable;


/**
 * 模型接口
 * 标识所有需要传输以及持久化的对象
 *
 * @author Peiyu
 */
public interface Model extends Serializable {
    /**
     * 提供默认的javaBean转JSON字符串的方法，供子类使用
     *
     * @return Bean对应的json串
     */
    public String toJsonString();
}
