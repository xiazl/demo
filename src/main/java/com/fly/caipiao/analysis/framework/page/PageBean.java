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
    private int current = 1;
    /**
     * start  number
     */
    private int iDisplayStart = 0;

    /**
     * number of data items per page
     */
    private int iDisplayLength = 50;

    public int getCurrent() {
        return iDisplayStart/iDisplayLength+1;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

}
