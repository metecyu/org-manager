package ey.org.bean;

import java.util.List;

/**
 * Bean类 - 分页  
 * =======================================
 * @author  作者姓名：黄 汉
 * @version 创建时间：Apr 8, 2011 5:30:19 PM
 * =======================================
 */
@SuppressWarnings("unchecked")
public class Pager3 {
	
	// 排序方式
	public enum OrderType{
		asc, desc
	}
	// 每页最大记录数限制
	public static final Integer MAX_PAGE_SIZE = 500;
	// 当前页码
	private Integer pageNumber = 1;
	// 每页记录数
	private Integer pageSize = 10;
	// 总记录数
	private Integer totalCount = 0;
	// 总页数
	private Integer pageCount = 0;
	// 查找属性名称
	private String property;
	// 查找关键字
	private String keyword;
	// 排序字段
	private String orderBy = "id";
	// 排序方式
	private OrderType orderType = OrderType.asc;
	// 数据List
	private List list;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

}