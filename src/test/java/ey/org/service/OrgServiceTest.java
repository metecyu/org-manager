package ey.org.service;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@ContextConfiguration(locations={"classpath:/springAnnotation-core.xml"})
public class OrgServiceTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Resource
	private OrgService orgService;

	
	
	public void addOrg() throws BadHanyuPinyinOutputFormatCombination{
		orgService.addOrg("root", "101团", "101团 - 全称", "101");
		orgService.addOrg("root", "102团", "102团 - 全称", "102");
	}
	

	public void getPath() throws BadHanyuPinyinOutputFormatCombination{
		String path = orgService.getFullPath("8");
		System.out.println(path);
		String path2 = orgService.getFullPath("root");
		System.out.println(path2);
	}
	
	@Test
	public void updateOrgWholeName() throws BadHanyuPinyinOutputFormatCombination{
		orgService.updateSonOrgWholeName("空军34师", "空军34师","空军35师");
		orgService.updateSonOrgWholeName("空军34师/师机关", "师机关","师机关2");
	
	}
	
	

}