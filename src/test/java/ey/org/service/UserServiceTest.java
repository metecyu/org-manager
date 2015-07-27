package ey.org.service;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@ContextConfiguration(locations={"classpath:/springAnnotation-core.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Resource
	private UserService userService;

	
	@Test
	public void addUser() throws BadHanyuPinyinOutputFormatCombination{
		userService.addUser("root", "蔡志高", "czg", "111","12@123.com","1","123123");
	}
	


}