package ey.orgclient.pub;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import ey.orgclient.pub.model.OrgOrg;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class OrgHTest {
	private static final Logger log = LogManager
			.getLogger(OrgHTest.class);
	OrgH orgH = new OrgH();

	public void findById() {
		
		OrgOrg org =orgH.findById("root");
		Assert.assertEquals("root", org.getId());
	}

	public void findByCode() {
		
		OrgOrg org =orgH.findByCode("001");
		
	}

	public void findMainOrgByUserid() {
		OrgOrg org =orgH.findMainOrgByUserid("zkk");
		
	}
	@Test
	public void findByRoleid() {
		List<OrgOrg> orgList =orgH.findByRoleid("admin");
		log.info(orgList.size());
	}

}
