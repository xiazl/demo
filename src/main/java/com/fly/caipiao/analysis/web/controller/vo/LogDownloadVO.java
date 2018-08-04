package com.fly.caipiao.analysis.web.controller.vo;

/**
 * @author baidu
 * @date 2018/7/29 下午1:49
 * @description ${TODO}
 **/
public class LogDownloadVO {
    private String PageNumber;
    private String TotalCount;
    private String PageSize;
    private String RequestId;
    private DomainLogModel DomainLogModel;

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public DomainLogModel getDomainLogModel() {
        return DomainLogModel;
    }

    public void setDomainLogModel(DomainLogModel domainLogModel) {
        DomainLogModel = domainLogModel;
    }


}
