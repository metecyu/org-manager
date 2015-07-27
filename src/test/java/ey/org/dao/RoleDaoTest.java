package ey.org.dao;

import java.util.List;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ey.org.mgmodel.UserRoleMG;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@ContextConfiguration(locations={"classpath:/springAnnotation-core.xml"})
public class RoleDaoTest extends AbstractTransactionalJUnit4SpringContextTests  {
	private static final Logger log = Logger.getLogger(RoleDaoTest.class);

	@Resource
	private RoleDao roleDao;

	@Test
	public void getUserRoleList() throws BadHanyuPinyinOutputFormatCombination{
		String userid ="122" ;
		List<UserRoleMG> list  = roleDao.getUserRoleList(userid);
		log.info(list.size());
		
	}

}