package com.std.framework.model;

import java.util.List;

@SuppressWarnings("unused")
public class ModelPaginator {

    private int pageNo;
    private int pageSize;
    private int pageCounts;
    private int dataCounts;
    private List dataList;

    public ModelPaginator(int pageNo, int pageSize, int counts, List dataList) {
        this.pageNo = pageNo;
        this.dataCounts = counts;
        this.dataList = dataList;
        this.pageCounts = counts / pageSize + (counts % pageSize > 0 ? 1 : 0);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCounts() {
        return pageCounts;
    }

    public void setPageCounts(int pageCounts) {
        this.pageCounts = pageCounts;
    }

    public int getDataCounts() {
        return dataCounts;
    }

    public void setDataCounts(int dataCounts) {
        this.dataCounts = dataCounts;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }
}
