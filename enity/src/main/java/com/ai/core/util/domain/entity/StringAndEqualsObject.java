package com.ai.core.util.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ai.core.util.utils.JSONUtil;

/**
 * @author linyl linyuliang.85@gmail.com
 * @ClassName: StringAndEqualsObject
 * @Description: 使用apache的commons包，重载了java对象的toString和Equals方法
 */
public abstract class StringAndEqualsObject implements Model {

	private static final long serialVersionUID = 1L;

	/*
	 * (非 Javadoc) <p>Title: StringAndEqualsObject.equals</p>
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/*
	 * (非 Javadoc) <p>Title: StringAndEqualsObject.hashCode</p>
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/*
	 * (非 Javadoc) <p>Title: StringAndEqualsObject.toString</p>
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	@Override
	public String toJsonString() {
		return JSONUtil.toJson(this);
	}

	;
}
