package com.hudong.b16live.utils;
import java.util.List;
/**
 * PageBean
 *
 * @Title: PageBean.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/7 10:18
 * @Author 90
 */
/**
 * 分页bean
 */

public class PageBean<T> {
    // 当前页
    private Integer currentPage = 1;
    // 每页显示的总条数
    private Integer pageSize = 10;
    // 总条数
    private Integer totalNum;
    // 是否有下一页
    private Integer isMore;
    // 总页数
    private Integer totalPage;
    // 开始索引
    private Integer startIndex;
    // 分页结果
    private List<T> items;
    private int PNCountPerPage = 10;// 每页显示的页码数
    private int FirstPageNum = 0; // 显示的第一个页码

    public int getFirstPageNum() {
        return FirstPageNum;
    }

    public void setFirstPageNum(int firstPageNum) {
        FirstPageNum = firstPageNum;
    }

    public int getLastPageNum() {
        return LastPageNum;
    }

    public void setLastPageNum(int lastPageNum) {
        LastPageNum = lastPageNum;
    }

    private int LastPageNum = 0; // 显示的最后一个页码
    private int FirstRecordNum = 0; // 当前页第一个记录
    private int LastRecordNum = 0; // 当前页最后一个记录
    private int CountPerPage = 10; // 每页记录数，和pageSize一致

    public PageBean() {
        super();
    }

    public PageBean(Integer currentPage, Integer pageSize, Integer totalNum) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum+this.pageSize-1)/this.pageSize;
        this.startIndex = (this.currentPage-1)*this.pageSize;
        this.isMore = this.currentPage >= this.totalPage?0:1;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getIsMore() {
        return isMore;
    }

    public void setIsMore(Integer isMore) {
        this.isMore = isMore;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    //跳转到目标页
    public void goToPage(int _PageNum) {
        if (_PageNum <= totalPage) {  //totalPage   替换pageCount
            if (totalPage <= PNCountPerPage) {// 总页数小于等于每页显示页码数 PNCountPerPage
                // 修改显示的第一个页码
                this.FirstPageNum = 1;
                // 修改显示的最后一个页码
                this.LastPageNum = totalPage;
                // 修改当前页第一个记录
                this.FirstRecordNum = (_PageNum - 1) * this.CountPerPage;
                // 修改当前页最后一个记录
                if (_PageNum == this.totalPage) {// 最后一页
                    this.LastRecordNum = this.totalNum - 1;
                }else{//不是最后一页
                    this.LastRecordNum = _PageNum * this.CountPerPage - 1;
                }
                // 修改当前页码
                this.currentPage = _PageNum;
            }else{    // 总页数大于每页显示页码数

                // 修改显示的第一个页码
                int fc = _PageNum - (this.PNCountPerPage / 2);
                this.setFirstPageNum(fc);
                // 修改显示的最后一个页码
                int lc = _PageNum + (this.PNCountPerPage / 2);

                if (lc > this.totalPage)
                    lc = this.totalPage;
                this.setLastPageNum(lc);
                // 修改当前页第一个记录
                this.FirstRecordNum = (_PageNum - 1) * this.CountPerPage;
                // 修改当前页最后一个记录
                if (_PageNum == this.totalPage) {// 最后一页
                    this.LastRecordNum = this.totalNum - 1;
                } else {// 不是最后一页
                    this.LastRecordNum = _PageNum * this.CountPerPage - 1;
                }
                // 修改当前页码
                this.currentPage = _PageNum;
            }
        }else{//不做任何处理

        }

    }
    public String getPageCode() {
        if(this.totalNum==0) return "";//totalNum  替换  RecordCount
        String pagecode="";
        pagecode="";
        pagecode+="<div class=\"page-area clearfix\"> <div class=\"rt page-lt\">";
        //currentPage   替换getCurrentPageNum
        if(this.getCurrentPage()!=1){
           pagecode+="<a  href=\"javascript:goToPage("+(this.getCurrentPage()-1)+")\" title=\"上一页\"><b>&lt;</b></a>";
        }else{
            pagecode+="<a   title=\"上一页\"><b>&lt;</b></a>";
        }

        if(this.getFirstPageNum()>1){
           pagecode+="<a href=\"javascript:goToPage(1)\" class=\"current\"><b>1</b></a>";
        }
        for(int i=this.getFirstPageNum();i<=this.getLastPageNum();i++){
            if(this.getCurrentPage()==i){
                pagecode+="<a href=\"javascript:void()\" class=\"current\"><b>"+i+"</b></a>";
            }else{
                pagecode+="<a href=\"javascript:goToPage("+i+")\" ><b>"+i+"</b></a>";
            }
        }
        if(this.getLastPageNum()<this.totalPage){
            pagecode+="..."+"<a  href=\"javascript:goToPage("+this.totalPage+")\">"+this.totalPage+"</a>";
        }
        if(this.getCurrentPage()!=this.getLastPageNum()){//currentPage  替换CurrentPageNum
            pagecode+="<a href=\"javascript:goToPage("+(this.getCurrentPage()+1)+")\"  title=\"下一页\"><b>&gt;</b></a>";
        }else{
            pagecode+="<a  title=\"下一页\"><b>&gt;</b></a>";
        }

        //最后
        pagecode+="</div></div>";
        /*
        if(this.getFirstPageNum()>1){
            pagecode=pagecode+"<a href=\"javascript:goToPage(1)\">1</a><span class=\"md\">...</span>";
        }
        for(int i=this.getFirstPageNum();i<=this.getLastPageNum();i++)
        {
            if(this.getCurrentPageNum()==i)
            {
                pagecode=pagecode+"<a href=\"#\" class=\"on\" >"+i+"</a> ";
            }
            else
            {
                pagecode=pagecode+"<a href=\"javascript:goToPage("+i+")\">"+i+"</a> ";
            }
        }

        if(this.getLastPageNum()<this.PageCount){
            pagecode=pagecode+"...<a  href=\"javascript:goToPage("+this.PageCount+")\">"+this.PageCount+"</a> ";
        }

        if(this.getCurrentPageNum()!=this.getLastPageNum())
        {
            pagecode=pagecode+"<a class=\"next\" href=\"javascript:goToPage("+(this.CurrentPageNum+1)+")\">下一页 &gt;</a>";
        }else{
            pagecode=pagecode+"<a href=\"###\" class=\"next\" >下一页 &gt;</a>";
        }
        pagecode = pagecode +" <span class=\"all\">共<b>"+this.getPageCount()+"</b>页<b>"+this.getRecordCount()+"</b>条</span>";
        pagecode=pagecode+" <input type=\"text\" class=\"input\" name=\"queryPageNo\" id=\"queryPageNo\" placeholder=\"输入页数\"  onkeyup=\"(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)\" onblur=\"this.v();\" /> <a href=\"javascript:goToPage($('#queryPageNo').val())\" class=\"trun\">跳转</a>";
        pagecode=pagecode+"</div>";*/
        return pagecode;
    }
}
