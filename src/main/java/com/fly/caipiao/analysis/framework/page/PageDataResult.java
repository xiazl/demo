package com.fly.caipiao.analysis.framework.page;

import java.util.List;

/**
 * 分页查询返回bean
 *
 * @author baidu
 */
public class PageDataResult<T> {
	/**
	 * query parameters
	 */
//	private Map filter;
	/**
	 * page parameter
	 */
	private Long iTotalRecords;

	private Long iTotalDisplayRecords;
	/**
	 * An array that contains the actual objects
	 */
	private List<T> data = null;

	public Long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
