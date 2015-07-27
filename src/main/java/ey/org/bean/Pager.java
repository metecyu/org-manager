package ey.org.bean;

import java.util.List;

/**
 * Bean类 - 分页
 * =======================================
 * @author  作者姓名：yzp
 * @version 创建时间：Apr 8, 2011 5:30:19 PM
 * =======================================
 */
@SuppressWarnings("unchecked")
public class Pager {
	
	// 排序方式
		public enum OrderType{
			asc, desc
		}
		// 每页最大记录数限制
		public static final Integer MAX_PAGE_SIZE = 500;
		// 起始记录
		private Integer firstRow = 0;
		// 当前页码
		private Integer currentPage = 1;
		// 每页记录数
		private Integer maxRows = 10;
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
		public Integer getFirstRow() {
			return firstRow;
		}
		public void setFirstRow(Integer firstRow) {
			this.firstRow = firstRow;
		}
		public Integer getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
		public Integer getMaxRows() {
			return maxRows;
		}
		public void setMaxRows(Integer maxRows) {
			if (maxRows < 1) {
				maxRows = 1;
			} else if(maxRows > MAX_PAGE_SIZE) {
				maxRows = MAX_PAGE_SIZE;
			}
			this.maxRows = maxRows;
		}
		public Integer getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(Integer totalCount) {
			this.totalCount = totalCount;
		}
		public Integer getPageCount() {
			pageCount = totalCount / maxRows;
			if (totalCount % maxRows > 0) {
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
		public static Integer getMaxPageSize() {
			return MAX_PAGE_SIZE;
		}
}