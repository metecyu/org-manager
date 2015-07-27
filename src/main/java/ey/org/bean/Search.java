package ey.org.bean;

import java.util.Date;
import java.util.List;

/**
 * Bean类 - 查询条件
 * =======================================
 * @author  作者姓名：黄 汉
 * @version 创建时间：Apr 8, 2011 5:30:19 PM
 * =======================================
 */
public class Search {

	private List<String> strProperty; //字符串属性名称集合
	private List<String> strKeyword; //字符串查询关键字集合
	private List<String> dateProperty; //日期属性名称集合
	private List<Date> dateFrom; //日期值开始
	private List<Date> dateTo; //日期值结束
	private List<String> intProperty; //整数属性名称集合
	private List<Integer> intFrom; //整数值开始
	private List<Integer> intTo; //整数值结束
	
	public List<String> getDateProperty() {
		return dateProperty;
	}
	public void setDateProperty(List<String> dateProperty) {
		this.dateProperty = dateProperty;
	}
	public List<Date> getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(List<Date> dateFrom) {
		this.dateFrom = dateFrom;
	}
	public List<Date> getDateTo() {
		return dateTo;
	}
	public void setDateTo(List<Date> dateTo) {
		this.dateTo = dateTo;
	}
	public List<String> getIntProperty() {
		return intProperty;
	}
	public void setIntProperty(List<String> intProperty) {
		this.intProperty = intProperty;
	}
	public List<Integer> getIntFrom() {
		return intFrom;
	}
	public void setIntFrom(List<Integer> intFrom) {
		this.intFrom = intFrom;
	}
	public List<Integer> getIntTo() {
		return intTo;
	}
	public void setIntTo(List<Integer> intTo) {
		this.intTo = intTo;
	}
	public List<String> getStrProperty() {
		return strProperty;
	}
	public void setStrProperty(List<String> strProperty) {
		this.strProperty = strProperty;
	}
	public List<String> getStrKeyword() {
		return strKeyword;
	}
	public void setStrKeyword(List<String> strKeyword) {
		this.strKeyword = strKeyword;
	}

	
	
	
}
