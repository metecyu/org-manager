package ey.orgclient.serviceSqlImp.dao;

import java.util.List;

import org.junit.Test;

import ey.orgclient.model.Role;
import ey.orgclient.serviceSqlImp.RoleServiceSqlImp;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class RoleServiceSqlImpTest {

	@Test
	public void findByUserid() {
		RoleServiceSqlImp obj = new RoleServiceSqlImp();		
		List list = obj.findByUserid("122");
		
	}
	
	@Test
	public void findById() {
		RoleServiceSqlImp obj = new RoleServiceSqlImp();		
		Role role = obj.findById("admin");
		
	}
	@Test
	public void findByCode() {
		RoleServiceSqlImp obj = new RoleServiceSqlImp();		
		Role role = obj.findByCode("admin");
		
	}
	
	


}
