package ey.orgclient.serviceSqlImp.dao;

import org.junit.Assert;
import org.junit.Test;

import ey.orgclient.model.User;
import ey.orgclient.service.ServiceFactory;
import ey.orgclient.serviceSqlImp.UserServiceSqlImp;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class UserServiceSqlImpTest {

	public void findById() {
		UserServiceSqlImp obj = new UserServiceSqlImp();
		
		User user = obj.findById("yzp");
		System.out.println(user.getUsername());
		User noUser = obj.findById("nouser");
		Assert.assertEquals(null, noUser);
	}
	@Test
	public void findByLonginId() {
		UserServiceSqlImp obj = new UserServiceSqlImp();
		
		User user = obj.findByLonginId("yzp");
		System.out.println(user.getUsername());
	}
}
