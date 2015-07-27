package ey.orgclient.pub;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import ey.orgclient.pub.model.OrgFunc;

/**
 * @Title: test.java
 * @Package ey.orgclient.serviceImpSql.dao
 * @Description: TODO(添加描述)
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-9-11 上午11:47:13
 * @version V1.0
 */
public class FuncHTest {
	private static final Logger log = LogManager
			.getLogger(FuncHTest.class);
	
	FuncH funcH = new FuncH();

	public void findById() {
		OrgFunc orgFunc =funcH.findById("root");
		log.info(orgFunc.getFuncname() );
	}

	
	public void findByCode() {
		OrgFunc orgFunc =funcH.findByCode("root");
		log.info(orgFunc.getFuncname() );
	}
	
	@Test
	public void findByRoleId() {
		List<OrgFunc> list =funcH.findByRoleId("admin", "2");
		log.info("功能项数量:"+list.size());
	
	}
}
