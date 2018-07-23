package com.fly.caipiao.analysis.framework.page;

/**
 * @author baidu
 * @date 2018/6/22 下午5:55
 * @description jquery dataTable 查询条件
 **/
public class ConditionVO {
    // 搜索框查询
    private String sSearch;
    private String keyword;

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
