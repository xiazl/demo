package com.fly.caipiao.analysis.framework.page;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 *  分页返回参数封装
 *
 * @author baidu
 */
public class PageHelp {
	public final static <T> PageDataResult<T> getDataResult(List<T> data) {
		return getDataResult(data, null);
	}
	public final static <T> PageDataResult<T> getDataResult(List<T> data, Map filter) {
		PageDataResult<T> dataResult = new PageDataResult<>();
		dataResult.setData(data);

		PageInfo<T> page = new PageInfo<T>(data);
		dataResult.setiTotalRecords(page.getTotal());
        dataResult.setiTotalDisplayRecords(page.getTotal());
		return dataResult;
	}

}
