package com.ai.web.entity;

import java.util.List;

import com.ai.core.util.domain.entity.StringAndEqualsObject;

/**
 * 后台向前台返回JSON，用于easyui的datagrid
 * 
 * @author sun yu
 * 
 */
public class DataGridJson extends StringAndEqualsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long total;// 总记录数
	private List<?> heads;
	private List<?> rows;// 每行记录
	private List<?> footer;

	// 完成的所有车辆数目
	private int count;

	// 总共应收金额
	private double sumCharge;

	// 总已收金额
	private double sumCharged;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}



	public Long getTotal() {
		return total;
	}

	public double getSumCharge() {
		return sumCharge;
	}

	public void setSumCharge(double sumCharge) {
		this.sumCharge = sumCharge;
	}

	public double getSumCharged() {
		return sumCharged;
	}

	public void setSumCharged(double sumCharged) {
		this.sumCharged = sumCharged;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}

	public List<?> getHeads() {
		return heads;
	}

	public void setHeads(List<?> heads) {
		this.heads = heads;
	}
}
