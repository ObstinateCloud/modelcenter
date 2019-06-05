package com.sgcc.web.dto.output;

public class PageDTO {
	private Integer currentPage;
	private Integer pageSize;
	private Long total;
	private Integer totalPage;
	private Object dataList;
	
	public PageDTO(Integer currentPage,Integer pageSize,Long total){
		this.currentPage=currentPage;
		this.pageSize=pageSize;
		this.total=total;
		setPageSize();
	}
	private void setPageSize() {
		this.totalPage=(int) (this.total%this.pageSize>0?(this.total/this.pageSize)+1:this.total/this.pageSize);
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
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getTotalPage() {
		
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Object getDataList() {
		return dataList;
	}
	public void setDataList(Object dataList) {
		this.dataList = dataList;
	}
	
	
}
