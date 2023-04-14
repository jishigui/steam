package com.util;

public class PageUtil {

    /**
     * 总记录数
     */
    private int totalRecord;
    /*
    * 总页数
    * */
    private int totalPage;

    /**
     * 每页显示的记录条数
     */
    private int pageSize;

    /**
     * 当前是第几页
     *
     */
    private int currentPage;

    /**
     * 查询结果
     */
    private Object dbdata;
    public PageUtil(){};

    public PageUtil(int totalRecord, int pageSize, int currentPage, Object dbdata) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.dbdata = dbdata;

        if (totalRecord%pageSize==0){
            totalPage = totalRecord/pageSize;
        }else {
            totalPage = totalRecord/pageSize+1;
        }
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        //totalRecord=10
        //paegSize = 5;
        //totalPage=2

        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {


        return totalPage;
    }

    public void setTotalPage(int totalPage) {

        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Object getDbdata() {
        return dbdata;
    }

    public void setDbdata(Object dbdata) {
        this.dbdata = dbdata;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "totalRecord=" + totalRecord +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", dbdata=" + dbdata +
                '}';
    }
}
