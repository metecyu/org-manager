package ey.org.dao;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@ContextConfiguration(locations={"classpath:/springAnnotation-core.xml"})
public class OrgDaoTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Resource
	private OrgDao orgDao;


}