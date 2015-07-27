package ey.orgclient.pub;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import ey.orgclient.pub.model.OrgUser;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class UserHTest {
	private static final Logger log = LogManager
			.getLogger(UserHTest.class);
	UserH userH = new UserH();
	@Test
	public void findById() {
		
		OrgUser user =userH.findById("yzp");
		Assert.assertEquals("俞张平", user.getUsername());
		
	}
	@Test
	public void findByLonginId() {
		
		OrgUser user =userH.findByLonginId("yzp");
		Assert.assertEquals("俞张平", user.getUsername());
	}

}
