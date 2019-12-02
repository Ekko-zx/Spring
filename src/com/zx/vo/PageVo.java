package com.zx.vo;

public class PageVo {
    private int pagenum;//当前页数
    private int pagesize;//每页行数
    private int pageallhan;//总行数
    private int pageallnum;//总页数


    public int getPageallnum() {
        return pageallnum;
    }

    public void setPageallnum(int pageallnum) {
        this.pageallnum = pageallnum;
    }

    public int getPageallhan() {
        return pageallhan;
    }

    public void setPageallhan(int pageallhan) {
        this.pageallhan = pageallhan;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "pagenum=" + pagenum +
                ", pagesize=" + pagesize +
                ", pageallhan=" + pageallhan +
                ", pageallnum=" + pageallnum +
                '}';
    }
}
