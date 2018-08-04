package com.fly.caipiao.analysis.web.controller.vo;

/**
 * @author baidu
 * @date 2018/7/29 下午3:19
 * @description ${TODO}
 **/

public class DomainLogModel{
    private String DomainName;
    private DomainLogDetails DomainLogDetails = new DomainLogDetails();

    public String getDomainName() {
        return DomainName;
    }

    public void setDomainName(String domainName) {
        DomainName = domainName;
    }

    public DomainLogDetails getDomainLogDetails() {
        return DomainLogDetails;
    }

    public void setDomainLogDetails(DomainLogDetails domainLogDetails) {
        DomainLogDetails = domainLogDetails;
    }
}