package com.ai.web.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @ClassName: BaseEntity
 * @Description: 实体类的基础父类，定义基本的toString，hashcode，equals等，目前仅实现toString和序列化
 * @author linyl linyuliang.85@gmail.com
 */
public abstract class BaseEntity implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : 默认序列化id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
