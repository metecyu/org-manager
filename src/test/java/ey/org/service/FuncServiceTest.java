package ey.org.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
@ContextConfiguration(locations={"classpath:/springAnnotation-core.xml"})
public class FuncServiceTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Resource
	private FuncService funcService;

	
	@Test
	public void addFunc() {
		// delete from ORG_FUNC t where t."ID" <> 'root';
		/*Func menu = 
		funcService.addFunc("菜单", "3", "10","root", "");
			Func menu2 = 
			funcService.addFunc("首页", "3", "1001",menu.getId(), "train/showIndex.do");
				funcService.addFunc("待办的飞行训练", "3", "100101",menu2.getId(), "train/showUnfinishedList.do");
			menu2 = 	
			funcService.addFunc("飞行训练", "3", "1002",menu.getId(), "");
				// funcService.addFunc("待办的飞行训练", "1", "100201",menu2.getId(), "");
				funcService.addFunc("待办的飞行训练（计时员）", "3", "100201",menu2.getId(), "train/showUnfinishedList.do");
				funcService.addFunc("待办的飞行训练（领航室）", "3", "100202",menu2.getId(), "train/showStep2List.do");
				funcService.addFunc("待办的飞行训练（航空军医）", "3", "100203",menu2.getId(), "train/showStep3List.do");
				funcService.addFunc("待办的飞行训练（团指挥员）", "3", "100204",menu2.getId(), "train/showStep4List.do");
				funcService.addFunc("待办的飞行训练（团首长）", "3", "100205",menu2.getId(), "train/showStep5List.do");
				funcService.addFunc("待办的飞行训练（飞行大队）", "3", "100206",menu2.getId(), "train/showStep6List.do");
				funcService.addFunc("待办的飞行训练（机组）", "3", "100207",menu2.getId(), "train/showStep7List.do");
				funcService.addFunc("待办的飞行训练（作训室）", "3", "100208",menu2.getId(), "train/showStep8List.do");
				funcService.addFunc("待办的飞行训练（场站）", "3", "100209",menu2.getId(), "train/showStep9List.do");				
				funcService.addFunc("待办的飞行训练（航行科）", "3", "100210",menu2.getId(), "train/showStep10List.do");
				funcService.addFunc("待办的飞行训练（军训科）", "3", "100211",menu2.getId(), "train/showStep11List.do");
				funcService.addFunc("待办的飞行训练（调度室）", "3", "100212",menu2.getId(), "train/showStep12List.do");
				funcService.addFunc("待办的飞行训练（机务讲评）", "3", "100213",menu2.getId(), "train/showStep13List.do");

				funcService.addFunc("经办的飞行训练", "3", "100221",menu2.getId(), "train/showMyTrainList.do");
				funcService.addFunc("未完成的飞行训练", "3", "100222",menu2.getId(), "train/showFerryTrainList.do");
				funcService.addFunc("已完成的飞行训练", "3", "100223",menu2.getId(), "train/showFerryTrainListxx.do");
			menu2 =
			funcService.addFunc("飞行任务", "3", "1003",menu.getId(), "");
				funcService.addFunc("未完成的飞行任务（作战科）", "3", "100301",menu2.getId(), "mission/showUnfinishedList.do");
				funcService.addFunc("未完成的飞行任务（师领导）", "3", "100302",menu2.getId(), "mission/showStep2List.do");
				funcService.addFunc("未完成的飞行任务（作战室）", "3", "100303",menu2.getId(), "mission/showStep3List.do");
				funcService.addFunc("未完成的飞行任务（领航室）", "3", "100304",menu2.getId(), "mission/showStep4List.do");
				funcService.addFunc("未完成的飞行任务（航空军医）", "3", "100305",menu2.getId(), "mission/showStep5List.do");
				funcService.addFunc("未完成的飞行任务（团指挥员）", "3", "100306",menu2.getId(), "mission/showStep6List.do");
				funcService.addFunc("未完成的飞行任务（团首长）", "3", "100307",menu2.getId(), "mission/showStep7List.do");
				funcService.addFunc("未完成的飞行任务（航行科）", "3", "100308",menu2.getId(), "mission/showStep8List.do");
				funcService.addFunc("未完成的飞行任务（飞行大队）", "3", "100309",menu2.getId(), "mission/showStep9List.do");
				funcService.addFunc("未完成的飞行任务（机组）", "3", "100310",menu2.getId(), "mission/showStep10List.do");
				funcService.addFunc("未完成的飞行任务（场站）", "3", "100313",menu2.getId(), "mission/showStep13List.do");
				funcService.addFunc("未完成的飞行任务（调度室）", "3", "100311",menu2.getId(), "mission/showStep11List.do");
				funcService.addFunc("未完成的飞行任务（机务讲评）", "3", "100312",menu2.getId(), "mission/showStep12List.do");
				
				funcService.addFunc("已完成的飞行任务", "3", "100321",menu2.getId(), "mission/showUnfinishedListxx.do");
			menu2 =
			funcService.addFunc("统计汇总", "3", "1004",menu.getId(), "");
				funcService.addFunc("飞行日记", "3", "100401",menu2.getId(), "diary/showAllDiaryList.do");
				funcService.addFunc("飞行时间表", "3", "100402",menu2.getId(), "statistic/openStatisticForm.do");
			menu2 =
			funcService.addFunc("飞行安全基础信息", "3", "1005",menu.getId(), "");
				funcService.addFunc("飞机信息维护", "3", "100501",menu2.getId(), "airplane/getAllAirplane.do");
				funcService.addFunc("航线信息维护", "3", "100502",menu2.getId(), "waypoint/showAllWaypointList.do");
				funcService.addFunc("飞行人员信息维护", "3", "100503",menu2.getId(), "person/showAllWorkerList.do");
				
			menu2 =
			funcService.addFunc("系统管理", "3", "1006",menu.getId(), "");
				funcService.addFunc("编码管理", "3", "100601",menu2.getId(), "codes/getAllCCate.do");
		menu = 
		funcService.addFunc("访问控制", "2", "","root", "");*/
		
		//-------------
		
		/*Func menu2 = funcService.addFunc("编码管理", "1", "2001","408948b6487827800148782785e4002c", "");
			funcService.addFunc("多级编码管理", "1", "200101",menu2.getId(), "");*/
				
	}
	

	

}