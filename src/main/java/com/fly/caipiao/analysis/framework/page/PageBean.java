package com.fly.caipiao.analysis.framework.page;

/**
 * jquery dataTable分页对象bean
 *
 * @author baidu
 */

public class PageBean {

    /**
     * current page number
     */
    private Integer current = 1;
    /**
     * start  number
     */
    private Long iDisplayStart = 0L;

    /**
     * number of data items per page
     */
    private Integer iDisplayLength = 50;

    private Integer iSortCol_0; // 排序字段

    private String sSortDir_0; // 排序方式

    public Integer getCurrent() {
        if(iDisplayLength == 0){
            return 1;
        }
        return (int)(iDisplayStart/iDisplayLength+1);
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Long getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Long iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public Integer getiSortCol_0() {
        return iSortCol_0;
    }

    public void setiSortCol_0(Integer iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    public String getsSortDir_0() {
        return sSortDir_0;
    }

    public void setsSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }
}
