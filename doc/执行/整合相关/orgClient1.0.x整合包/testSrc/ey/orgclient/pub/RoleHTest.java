package ey.orgclient.pub;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import ey.orgclient.pub.model.OrgFunc;
import ey.orgclient.pub.model.OrgRole;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class RoleHTest {
	private static final Logger log = LogManager
			.getLogger(RoleHTest.class);
	
	RoleH roleH = new RoleH();
	@Test
	public void findById() {
		OrgRole orgRole =roleH.findById("admin");
		log.info(orgRole.getRolename() );
	}

	@Test
	public void findByCode() {
		OrgRole orgRole =roleH.findByCode("ttt");
		log.info(orgRole.getRoleWholename() );
	}
	
	@Test
	public void findByUserid() {
		List<OrgRole> list =roleH.findByUserid("122");
		log.info("功能项数量:"+list.size());
	
	}
	@Test
	public void findByRolegroup() {
		List<OrgRole> list =roleH.findByRolegroup("yy1");
		log.info("角色数量:"+list.size());
	
	}
	
}
