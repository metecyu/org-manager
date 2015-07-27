package ey.org.bean;


/**
 * Bean类 - 页面属性bean
 * =======================================
 * @author  作者姓名：yzp
 * @version 创建时间：Apr 8, 2011 5:30:19 PM
 * =======================================
 */
@SuppressWarnings("unchecked")
public class Param {
	String name;
	Object value;
	public  Param(String name,Object value){
		this.name =name ;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	
}