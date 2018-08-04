package com.fly.caipiao.analysis.framework.page;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *  分页返回参数封装
 *
 * @author baidu
 */
public class PageHelp {

	/**
	 * mysql 分页返回
	 * @param data
	 * @param <T>
	 * @return
	 */
	public final static <T> PageDataResult<T> getDataResult(List<T> data) {
		PageDataResult<T> dataResult = new PageDataResult<>();
		dataResult.setAaData(data);

		PageInfo<T> page = new PageInfo<T>(data);
		dataResult.setiTotalRecords(page.getTotal());
		dataResult.setiTotalDisplayRecords(page.getTotal());
		return dataResult;
	}

	/**
	 * mongo 分页返回
	 * @param data
	 * @param size
	 * @param <T>
	 * @return
	 */
	public final static <T> PageDataResult<T> getDataResult(List<T> data, Long size) {
		PageDataResult<T> dataResult = new PageDataResult<>();
		dataResult.setAaData(data);

		dataResult.setiTotalRecords(size);
        dataResult.setiTotalDisplayRecords(size);
		return dataResult;
	}

}
