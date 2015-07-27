package ey.org.dao;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ey.org.model.User;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@ContextConfiguration(locations={"classpath:/springAnnotation-core.xml"})
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Resource
	private UserDao userDao;

	@Test
	public void add() throws BadHanyuPinyinOutputFormatCombination{
		for(int i=0;i<100000;i++){
			String username = "tstaUser"+i;
			User user = new User();
			user.setId(username);
			user.setUsername(username);
			user.setLoginid("loginid");
			user.setPassword("pwd");
			user.setIseffect("y");
	     	user.setEmail("email");
			user.setGender("gender");
	        user.setMobile("137137");
	        userDao.save(user);
	        System.out.println(i);
		}
		
		
	}
}