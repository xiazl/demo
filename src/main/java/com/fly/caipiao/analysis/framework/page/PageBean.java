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

    public Integer getCurrent() {
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

}
