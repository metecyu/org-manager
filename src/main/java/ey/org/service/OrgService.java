package ey.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Service;

import ey.org.dao.OrgDao;
import ey.org.model.Org;
import ey.org.webmodel.OrgWB;

/**
 * 组织
 * @ClassName: OrgService
 * @author yzp
 * @date 2014-8-28 上午11:02:40
 *
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Service
public class OrgService {
	@Resource
	private OrgDao orgDao;
	
	
	/**
	 * 新增组织
	 * @Title: addOrg
	 * @author yzp
	 * @date 2014-9-1 下午2:28:46
	 * @param pid
	 * @param shortname
	 * @param orgname
	 * @param code
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination Org    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	 
	public Org addOrg(String pid,String shortname,String orgname ,String code) throws BadHanyuPinyinOutputFormatCombination {
		Org org = new Org();
		org.setId(orgDao.genarateDeptId(shortname));
		org.setPid(pid);
		org.setShortname(shortname);
		org.setOrgname(orgname);
		org.setCode(code);
		org.setIseffect("y");
		org.setSort(orgDao.findMaxSerial(pid)+1);
		orgDao.save(org);
		// 更新全路径
		org.setOrgwholename(getFullPath(org.getId()));
		orgDao.update(org);
		return org;
	}
	/**
	 * 更新组织
	 * @Title: updateOrg
	 * @author yzp
	 * @date 2014-9-1 下午2:28:46
	 * @param orgid
	 * @param shortname
	 * @param orgname
	 * @param code
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination Org    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	 
	public Org updateOrg(String orgid,String shortname,String orgname ,String code) throws BadHanyuPinyinOutputFormatCombination {
		
		Org org = orgDao.findById(orgid);
		String oldShortName = org.getShortname();
		String oldOrgwholename = org.getOrgwholename();
		// org.setId(orgDao.genarateDeptId(shortname));
		org.setPid(org.getPid());
		org.setShortname(shortname);
		org.setOrgname(orgname);
		org.setCode(code);
		org.setOrgwholename(getFullPath(org.getId()));
		// org.setIseffect("y");
		// org.setSort(orgDao.findMaxSerial(org.getPid())+1);
		orgDao.save(org);
		// 更新全路径
		/**/
		
		this.updateSonOrgWholeName(oldOrgwholename,oldShortName, shortname);
		
		return org;
	}
	
	/**
	 * 删除组织（逻辑删除）
	 * @Title: deleteOrg
	 * @author yzp
	 * @date 2014-9-4 上午10:58:24
	 * @param orgid
	 * @return Org    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public Org deleteOrg(String orgid)  {
		
		Org org = orgDao.findById( orgid);
		org.setIseffect("n");
		orgDao.save(org);
		return org;
	}
	/**
	 * 编码是否重名
	 * @Title: isUniCode
	 * @author yzp
	 * @date 2014-9-1 下午2:26:33
	 * @param code
	 * @return boolean    
	 * @throws 
	 * @Description: 
	 */
	public boolean isUniCode(String code,String orgid) {
		boolean isUni = orgDao.isUniCode(code,orgid);
		return isUni;
	}
	
	/**
	 * 是否允许删除
	 * @Title: isAllowDel
	 * @author yzp
	 * @date 2014-9-1 下午2:26:33
	 * @param orgid
	 * @return int 1:合法，-1:有子部门     
	 * @throws 
	 * @Description: 
	 */
	public int isAllowDel(String orgid) {
		int type = 1;
		List orgList = orgDao.getOrgList(orgid);
		if(orgList.size()>0){
			type =-1;
		}
		return type;
	}
	

	
	/**
	 * 获得所有的org记录
	 * @Title: getAllOrgList
	 * @author yzp
	 * @date 2014-8-29 上午10:09:37
	 * @return List<Org>    
	 * @throws 
	 * @Description: 
	 */
	public List<Org> getAllOrgList(){
		return orgDao.getAllOrgList();
	}
	/**
	 * 获取组织
	 * @Title: findById
	 * @author yzp
	 * @date 2014-9-4 上午10:59:32
	 * @param orgid
	 * @return Org    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public Org findById(String orgid){
		return  orgDao.findById(orgid);
	}
	/**
	 * 获取子组织列表（仅限一级组织）
	 * @Title: getOrgList
	 * @author yzp
	 * @date 2014-9-4 上午10:59:49
	 * @param pid
	 * @return List<Org>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	
	public List<Org> getOrgList(String pid){
		return  orgDao.getOrgList(pid);
	}
	
	/**
	 * 
	 * @Title: getPageOrgList 分页
	 * @author (wmc)
	 * @date 2014-12-1 下午2:39:25
	 * @param pid
	 * @param currentPage
	 * @return List<Org>
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public List<Org> getPageOrgList(String pid,int currentPage){
		return orgDao.getPageOrgList(pid, currentPage);
	}
	
	/**
	 * 获得记录总数，用于计算分页数
	 * @Title: getPageCount
	 * @author (wmc)
	 * @date 2014-12-1 下午2:40:11
	 * @param pid
	 * @return int
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public int getPageCount(String pid){
		return orgDao.getPageCount(pid);
	}
	
	/**
	 * 获取组织的全路径   如：公司/部门/项目组
	 * @Title: getFullPath
	 * @author yzp
	 * @date 2014-9-4 上午10:58:48
	 * @param orgid
	 * @return String    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public String getFullPath(String orgid){
		Org org = orgDao.findById(orgid);
		String fullPathStr = ""+org.getShortname();
        if(!"root".equals(org.getId())){        	
        	 String  str = getFullPath(org.getPid());
        	 fullPathStr = str +"/"+fullPathStr;
        	 return fullPathStr;
        }else{
               return fullPathStr;      //当num=0时，循环结束
        }
	}    
	/**
	 * 更新所有下属子部门（包括孙部门）的全路径
	 * @Title: updateSonOrgWholeName
	 * @author yzp
	 * @date 2014-9-2 上午10:59:07
	 * @param orgwholename 原全路径
	 * @param oldShortname 原名称（修改前）
	 * @param newShortname 现名称    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public void updateSonOrgWholeName(String orgwholename,String oldShortname,String newShortname){
		// Org org = orgDao.findById(orgid);
		// 如果名称发生变化
		if(!newShortname.equals(oldShortname)){
			String oldPath = orgwholename;
			String newPath ="";			
			int pos =orgwholename.lastIndexOf("/");
			if(pos!=-1){
				newPath = orgwholename.substring(0,pos+1)+newShortname;
			}else{
				newPath = newShortname;
			}
			orgDao.updateOrgWholeName( oldPath, newPath);
		}
	}
	
	
   /**
	 * 获取角色数据权限列表（组织）
	 * @Title: getRoleOrgList
	 * @author yzp
	 * @date 2014-9-9 下午1:17:26
	 * @param roleid
	 * @return List<Func>    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public List<Org> getRoleOrgList(String roleid){
		List<Org> list = orgDao.getRoleOrgList(roleid);
		return list;
	}
	
	public List<OrgWB> turnToWB(List<Org> orgList,List<Org> roleOrgList){
		Map roleOrgMap = new HashMap();
		for(Org org : roleOrgList){
			roleOrgMap.put(org.getId(),"");
		}
		List<OrgWB> outList = new ArrayList();
		for(Org org : orgList){
			OrgWB wb = new OrgWB();
			wb.setId(org.getId());
			wb.setOrgname(org.getOrgname());
			wb.setShortname(org.getShortname());
			wb.setIseffect(org.getIseffect());
			wb.setType(org.getType());
			wb.setSort(org.getSort());
			wb.setCode(org.getCode());
			wb.setOrgwholename(org.getOrgwholename());
			wb.setPid(org.getPid());
			
			if(roleOrgMap.containsKey(org.getId())){
				wb.setChecked(true);	
			}
			outList.add(wb);
		} 
		return outList;
	}
	
	/**
	 * 通过编码获取组织id
	 * @Title: getOrgId
	 * @author (wmc)
	 * @date 2015-1-5 下午4:09:28
	 * @param code
	 * @return String
	 * @throws
	 * @Description: TODO(xxxxxxxxxxxx)
	 */
	public String getOrgId(String code){
		return orgDao.getOrgId(code);
	}
	
}
