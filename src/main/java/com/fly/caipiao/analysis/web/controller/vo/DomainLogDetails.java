package com.fly.caipiao.analysis.web.controller.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/7/29 下午3:19
 * @description ${TODO}
 **/

public class DomainLogDetails {
    private List<CDNFileVO> DomainLogDetail = new ArrayList<>();

    public List<CDNFileVO> getDomainLogDetail() {
        return DomainLogDetail;
    }

    public void setDomainLogDetail(List<CDNFileVO> domainLogDetail) {
        DomainLogDetail = domainLogDetail;
    }
}
