package ey.orgclient.serviceSqlImp.dao;

import java.util.List;

import org.junit.Test;

import ey.orgclient.model.Func;
import ey.orgclient.serviceSqlImp.FuncServiceSqlImp;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class FuncServiceSqlImpTest {

	@Test
	public void findByRoleId() {
		FuncServiceSqlImp obj = new FuncServiceSqlImp();		
		List list = obj.findByRoleId("admin", "1");
		
	}
	
	
	public void findById() {
		FuncServiceSqlImp obj = new FuncServiceSqlImp();		
		Func func = obj.findById("root");
		
	}
	
	public void findByCode() {
		FuncServiceSqlImp obj = new FuncServiceSqlImp();		
		Func func = obj.findByCode("root");
		
	}


}
